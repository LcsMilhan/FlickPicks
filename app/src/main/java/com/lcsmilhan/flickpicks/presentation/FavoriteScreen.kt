package com.lcsmilhan.flickpicks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lcsmilhan.flickpicks.presentation.favorites.FavoritesViewModel

@Composable
fun FavoriteScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesState by viewModel.favoritesState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            favoritesState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            favoritesState.error.isNotEmpty() -> {
                Text(text = favoritesState.error, color = Color.Red)
            }
            favoritesState.myFavorites.isNotEmpty() -> {
                favoritesState.myFavorites.forEach { myMovie ->
                    Text(text = myMovie.title, color = Color.Magenta)
                }
            }
            else -> {
                Text(text = "Nenhum filme favorito encontrado", color = Color.Red)
            }
        }
    }
}