package com.lcsmilhan.flickpicks.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.model.Movie

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)) {
            Text(text = movie.title)
            GlideImage(model = Constants.getPosterPath(movie.thumbImage), contentDescription = "")
        }
    }
}

@Composable
fun TestApiComposable(
    movieList: List<Movie>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Filmes da API:",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Exibe a lista de filmes
        LazyColumn {
            items(movieList) { movie ->
                MovieItem(movie = movie)
                Divider() // Adiciona uma linha divisória entre os filmes
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    
    val state = viewModel.moviesState.collectAsState()
    
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            TestApiComposable(state.value.movies)
        }
    }
    
}