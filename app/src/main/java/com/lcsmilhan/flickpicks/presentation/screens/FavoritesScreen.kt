package com.lcsmilhan.flickpicks.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.lcsmilhan.flickpicks.presentation.favorites_screen.FavoritesViewModel
import com.lcsmilhan.flickpicks.presentation.favorites_screen.component.FavoritesItem
import com.lcsmilhan.flickpicks.presentation.navigation.Screen
import com.lcsmilhan.flickpicks.presentation.screens.component.BottomBarItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val favoriteState by viewModel.favoritesState.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            BottomBarItem(navController = navController)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Favorites List",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 15.dp, top = 20.dp)
            )
            when {
                favoriteState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            Modifier
                                .align(Alignment.Center)
                                .size(40.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                favoriteState.error.isNotEmpty() -> {
                    Text(
                        text = favoriteState.error,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                favoriteState.myFavorites.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(favoriteState.myFavorites) { movie ->
                            FavoritesItem(
                                onItemClick = {
                                    navController.navigate(
                                        "${Screen.MovieDetailsScreen.route}/${it.id}"
                                    )
                                },
                                movie = movie
                            )
                        }
                    }
                }
            }
        }
    }
}