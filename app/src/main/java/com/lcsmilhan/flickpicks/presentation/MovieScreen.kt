package com.lcsmilhan.flickpicks.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.model.Movie
import com.lcsmilhan.flickpicks.presentation.home.HomeEvent
import com.lcsmilhan.flickpicks.presentation.home.HomeViewModel
import com.lcsmilhan.flickpicks.presentation.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MovieScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val popularState by homeViewModel.popularState.collectAsStateWithLifecycle()
    val topRatedState by homeViewModel.topRatedState.collectAsStateWithLifecycle()
    val upcomingState by homeViewModel.upcomingState.collectAsStateWithLifecycle()
    val searchResults by homeViewModel.searchResults.collectAsStateWithLifecycle()
    val genreState by homeViewModel.genreState.collectAsStateWithLifecycle()

    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
    var searchKeyword by rememberSaveable { mutableStateOf("") }
    val verticalScrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    var isSearchFocused by remember { mutableStateOf(false) }

    val filteredPopularMovies = popularState.movies.filterMoviesByGenre(selectedGenre)
    val filteredTopRatedMovies = topRatedState.movies.filterMoviesByGenre(selectedGenre)
    val filteredUpcomingMovies = upcomingState.movies.filterMoviesByGenre(selectedGenre)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isSearchFocused = it.isFocused
                },
            value = searchKeyword,
            onValueChange = {
                searchKeyword = it
                homeViewModel.onEvent(HomeEvent.OnSearchClick(it))
            },
            singleLine = true,
            label = {
                if (!isSearchFocused && searchKeyword.isEmpty()) {
                    Text(text = "Search by title")
                }
            }
        )
        LazyRow(
            Modifier
                .fillMaxWidth()
                .padding(PaddingValues(5.dp))
        ) {
            items(searchResults.movies) { movie ->
                MovieItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                route = Screen.MovieDetailsScreen.route +
                                        "?movieId=${movie.id}"
                            )
                        },
                    movie = movie
                )
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Button(
                    onClick = {
                        homeViewModel.onEvent(HomeEvent.CleanFilter)
                        selectedGenre = null
                    }
                ) {
                    Text(text = "Clean")
                }
            }
            items(genreState.genres) {
                Button(
                    onClick = {
                        homeViewModel.onEvent(HomeEvent.SelectedGenre(it.id))
                        selectedGenre = it
                    }
                ) {
                    Text(text = it.name)
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        if (filteredPopularMovies.isEmpty() && filteredTopRatedMovies.isEmpty() && filteredUpcomingMovies.isEmpty() &&
            !popularState.isLoading && !topRatedState.isLoading && !upcomingState.isLoading &&
            popularState.error.isEmpty() && topRatedState.error.isEmpty() && upcomingState.error.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Não há filmes com este gênero.")
            }
        } else {
            Column(
                Modifier.verticalScroll(verticalScrollState)
            ) {
                Text(text = "Popular Movies", style = MaterialTheme.typography.titleLarge)
                LazyRow(
                    Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    items(filteredPopularMovies) { popularMovies ->
                        MovieItem(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("${Screen.MovieDetailsScreen.route}/${popularMovies.id}")
                                },
                            movie = popularMovies
                        )
                    }
                }
                Text(text = "Top Rated Movies", style = MaterialTheme.typography.titleLarge)
                LazyRow(
                    Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    items(filteredTopRatedMovies) { topRatedMovies ->
                        MovieItem(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        route = Screen.MovieDetailsScreen.route +
                                                "?movieId=${topRatedMovies.id}"
                                    )
                                },
                            movie = topRatedMovies
                        )
                    }
                }
                Text(text = "Upcoming Movies", style = MaterialTheme.typography.titleLarge)
                LazyRow(
                    Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp)
                ) {
                    items(filteredUpcomingMovies) { upcomingMovies ->
                        MovieItem(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        route = Screen.MovieDetailsScreen.route +
                                                "?movieId=${upcomingMovies.id}"
                                    )
                                },
                            movie = upcomingMovies
                        )
                    }
                }
            }
        }
    }
}


private fun List<Movie>.filterMoviesByGenre(genre: Genre?): List<Movie> {
    return if (genre != null) {
        this.filter { it.genreIds.contains(genre.id) }
    } else {
        this
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
         Box(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = Constants.getPosterPath(movie.posterPath),
                contentDescription = "",
            )
            Box(
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .size(width = 40.dp, height = 20.dp)
                    .background(Color.Red.copy(0.6f))
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}
