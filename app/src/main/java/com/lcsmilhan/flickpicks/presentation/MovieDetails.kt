package com.lcsmilhan.flickpicks.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import com.lcsmilhan.flickpicks.domain.remote.model.Video
import com.lcsmilhan.flickpicks.presentation.movie_details.MovieDetailsViewModel

@Composable
fun MovieDetails(
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel(),
) {
    val trailerState by movieDetailsViewModel.movieTrailers.collectAsStateWithLifecycle()
    val detailsState by movieDetailsViewModel.movieDetails.collectAsStateWithLifecycle()


//    val isFavorite = detailsState.movieDetails?.let {
//        favoritesState.contains(MyMovies(posterPath = it.posterPath, isFavoriteList = true))
//    } ?: false

    Surface(
        Modifier.fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            MovieOverview(
                overview = detailsState.movieDetails?.overview ?: "Overview não disponível"
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                detailsState.movieDetails?.let { details ->
                    MovieDetailsItem(details = details)
                }
            }
        }
//            Button(
//                onClick = {
//                    if (isFavorite) {
//                        detailsState.movieDetails?.let {
//                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnRemoveFromFavorites(it.posterPath))
//                        }
//                    } else {
//                        detailsState.movieDetails?.let {
//                            movieDetailsViewModel.onEvent(MovieDetailsEvent.OnAddToFavorites(it.posterPath))
//                        }
//                    }
//                }
//            ) {
//                if (isFavorite) {
//                    Icon(imageVector = Icons.Default.Star, contentDescription = "")
//                } else {
//                    Icon(imageVector = Icons.Default.Clear, contentDescription = "")
//                }
//            }
//        }
//
//        LazyColumn {
//            items(favoritesState) { myMovie ->
//                Text(text = myMovie.posterPath)
//            }
//        }
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