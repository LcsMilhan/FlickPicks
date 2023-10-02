package com.lcsmilhan.flickpicks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import com.lcsmilhan.flickpicks.domain.remote.model.Video
import com.lcsmilhan.flickpicks.presentation.movie_details.MovieDetailsEvent
import com.lcsmilhan.flickpicks.presentation.movie_details.MovieDetailsViewModel
import com.lcsmilhan.flickpicks.presentation.navigation.Screen

@Composable
fun MovieDetails(
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel(),
    navController: NavController,
    movieId: Int
) {
    val trailerState by movieDetailsViewModel.movieTrailers.collectAsStateWithLifecycle()
    val detailsState by movieDetailsViewModel.movieDetails.collectAsStateWithLifecycle()
    val favoriteState by movieDetailsViewModel.myFavorites.collectAsStateWithLifecycle()
    val watchListState by movieDetailsViewModel.myWatchList.collectAsStateWithLifecycle()

    var isMovieInWatchList by remember { mutableStateOf(false) }
    var isMovieInFavorites by remember { mutableStateOf(false) }

    LaunchedEffect(watchListState) {
        isMovieInWatchList = watchListState.any { it.id == movieId }
    }

    LaunchedEffect(favoriteState) {
        isMovieInFavorites = favoriteState.any { it.id == movieId }
    }

    val favoriteIcon: ImageVector = if (isMovieInFavorites) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    val watchListIcon: ImageVector = if (isMovieInWatchList) {
        Icons.Default.Add
    } else {
        Icons.Default.Refresh
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            // Seção de Detalhes do Filme
            MovieOverview(overview = detailsState.movieDetails?.overview ?: "Overview não disponível")
            Box(modifier = Modifier.fillMaxWidth()) {
                detailsState.movieDetails?.let { details ->
                    MovieDetailsItem(details = details)
                }
            }
        }
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        if (isMovieInFavorites) {
                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnRemoveFromFavorites(movieId))
                        } else {
                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnAddToFavorites(movieId))
                        }
                    }
                ) {
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = if (isMovieInFavorites) "Remover dos Favoritos" else "Adicionar aos Favoritos",
                        tint = Color.Red
                    )
                }
            }
            Row(Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate(Screen.FavoritesScreen.route) }
                ) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "Favoritos", tint = Color.Red)
                }
            }


            Row(Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = {
                        if (isMovieInWatchList) {
                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnRemoveFromWatchList(movieId))
                        } else {
                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnAddToWatchList(movieId))
                        }
                    }
                ) {
                    Icon(
                        imageVector = watchListIcon,
                        contentDescription = if (isMovieInWatchList) "Remover dos Favoritos" else "Adicionar aos Favoritos",
                        tint = Color.Green
                    )
                }
            }
            Row(Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate(Screen.WatchListScreen.route) }
                ) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "WatchList", tint = Color.Green)
                }
            }
        }
    }
}


@Composable
fun MovieOverview(overview: String) {
    Text(text = overview, textAlign = TextAlign.Start)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsItem(details: Details) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GlideImage(model = Constants.getBackDropPath(details.backdropPath), contentDescription = "")
    }
}

@Composable
fun MovieDetailsContent(video: Video) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Título: ${video.name}", fontWeight = FontWeight.Bold)
    }
}