package com.lcsmilhan.flickpicks.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.model.Movie
import com.lcsmilhan.flickpicks.presentation.explorer_screen.ExplorerEvent
import com.lcsmilhan.flickpicks.presentation.explorer_screen.ExplorerViewModel
import com.lcsmilhan.flickpicks.presentation.explorer_screen.component.GenreItem
import com.lcsmilhan.flickpicks.presentation.explorer_screen.component.MovieItem
import com.lcsmilhan.flickpicks.presentation.explorer_screen.component.SearchBarItem
import com.lcsmilhan.flickpicks.presentation.navigation.Screen
import com.lcsmilhan.flickpicks.presentation.screens.component.BottomBarItem

@Composable
fun ExplorerScreen(
    viewModel: ExplorerViewModel = hiltViewModel(),
    navController: NavController
) {
    val popularState by viewModel.popularState.collectAsStateWithLifecycle()
    val topRatedState by viewModel.topRatedState.collectAsStateWithLifecycle()
    val upcomingState by viewModel.upcomingState.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val genreState by viewModel.genreState.collectAsStateWithLifecycle()

    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
    val verticalScrollState = rememberScrollState()

    val filteredPopularMovies = popularState.movies.filterMoviesByGenre(selectedGenre)
    val filteredTopRatedMovies = topRatedState.movies.filterMoviesByGenre(selectedGenre)
    val filteredUpcomingMovies = upcomingState.movies.filterMoviesByGenre(selectedGenre)

    Scaffold(
        topBar = {
            SearchBarItem(
                onMovieClick = {
                    navController.navigate("${Screen.MovieDetailsScreen.route}/${it}")
                },
                onValueChange = {
                    viewModel.onEvent(ExplorerEvent.OnSearchClick(it))
                },
                movie = searchResults.movies,
            )
        },
        bottomBar = {
            BottomBarItem(
                onExploreClick = { navController.navigate(Screen.ExplorerScreen.route) },
                onMyWatchListClick = { navController.navigate(Screen.WatchListScreen.route) },
                onMyFavoritesClick = { navController.navigate(Screen.FavoritesScreen.route) }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            GenreItem(
                genres = genreState.genres,
                onCleanClick = {
                    selectedGenre = null
                    viewModel.onEvent(ExplorerEvent.CleanFilter)
                },
                selectedGenre = selectedGenre,
                onGenreClick = {
                    selectedGenre = it
                    viewModel.onEvent(ExplorerEvent.SelectedGenre(it.id))
                }
            )
            if (filteredPopularMovies.isEmpty() && filteredTopRatedMovies.isEmpty() && filteredUpcomingMovies.isEmpty() &&
                !popularState.isLoading && !topRatedState.isLoading && !upcomingState.isLoading &&
                popularState.error.isEmpty() && topRatedState.error.isEmpty() && upcomingState.error.isEmpty()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No movies found with this genre.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(verticalScrollState)
                ) {
                    Text(
                        text = "Popular Movies",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp, vertical = 5.dp)
                    )
                    LazyRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(5.dp))
                    ) {
                        items(filteredPopularMovies) { movie ->
                            MovieItem(
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigate(
                                        "${Screen.MovieDetailsScreen.route}/${movie.id}"
                                    )
                                },
                                image = movie.posterPath,
                                voteAverage = movie.voteAverage,
                                releaseDate = movie.releaseDate
                            )
                        }
                    }
                    Text(
                        text = "Top Rated Movies",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp, vertical = 5.dp)
                    )
                    LazyRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(5.dp))
                    ) {
                        items(filteredTopRatedMovies) { movie ->
                            MovieItem(
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigate(
                                        "${Screen.MovieDetailsScreen.route}/${movie.id}"
                                    )
                                },
                                image = movie.posterPath,
                                voteAverage = movie.voteAverage,
                                releaseDate = movie.releaseDate
                            )
                        }
                    }
                    Text(
                        text = "Upcoming Movies",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp, vertical = 5.dp)
                    )
                    LazyRow(
                        Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(5.dp))
                    ) {
                        items(filteredUpcomingMovies) { movie ->
                            MovieItem(
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    navController.navigate(
                                        "${Screen.MovieDetailsScreen.route}/${movie.id}"
                                    )
                                },
                                image = movie.posterPath,
                                voteAverage = movie.voteAverage,
                                releaseDate = movie.releaseDate
                            )
                        }
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