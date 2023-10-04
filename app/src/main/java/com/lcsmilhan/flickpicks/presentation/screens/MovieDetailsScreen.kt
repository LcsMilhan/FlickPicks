package com.lcsmilhan.flickpicks.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lcsmilhan.flickpicks.R
import com.lcsmilhan.flickpicks.presentation.movie_details_screen.MovieDetailsEvent
import com.lcsmilhan.flickpicks.presentation.movie_details_screen.MovieDetailsViewModel
import com.lcsmilhan.flickpicks.presentation.movie_details_screen.component.MovieDetailsItem
import com.lcsmilhan.flickpicks.presentation.screens.component.BottomBarItem

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    movieId: Int,
    navController: NavHostController,
) {
    val detailsState by viewModel.movieDetails.collectAsStateWithLifecycle()
    val trailerState by viewModel.movieTrailers.collectAsState()

    val isMovieInWatchList = viewModel.myWatchList.collectAsStateWithLifecycle()
        .let { watchListState -> watchListState.value.any { it.id == movieId } }

    val isMovieInFavorites = viewModel.myFavorites.collectAsStateWithLifecycle()
        .let { favoriteState -> favoriteState.value.any { it.id == movieId } }

    val favoriteIcon: Painter = if (isMovieInFavorites) {
        painterResource(R.drawable.favorite)
    } else {
        painterResource(R.drawable.favorite_off)
    }

    val watchListIcon: Painter = if (isMovieInWatchList) {
        painterResource(R.drawable.watch_list)
    } else {
        painterResource(R.drawable.watch_list_off)
    }

    Scaffold(
        bottomBar = {
            BottomBarItem(navController = navController)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            detailsState.movieDetails?.let { details ->
                trailerState.movieTrailers.firstOrNull()?.let { trailer ->
                    MovieDetailsItem(
                        backImage = details.backdropPath,
                        releaseDate = details.releaseDate,
                        voteAverage = details.voteAverage,
                        overview = details.overview,
                        videoId = trailer.key,
                        onFavoriteClick = {
                            viewModel.onEvent(
                                if (isMovieInFavorites) {
                                    MovieDetailsEvent.OnRemoveFromFavorites(movieId)
                                } else {
                                    MovieDetailsEvent.OnAddToFavorites(movieId)
                                }
                            )
                        },
                        onWatchListClick = {
                            viewModel.onEvent(
                                if (isMovieInWatchList) {
                                    MovieDetailsEvent.OnRemoveFromWatchList(movieId)
                                } else {
                                    MovieDetailsEvent.OnAddToWatchList(movieId)
                                }
                            )
                        },
                        favoriteIcon = favoriteIcon,
                        watchListIcon = watchListIcon,
                        title = details.title
                    )
                }
            }
        }
    }
}