package com.lcsmilhan.flickpicks.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lcsmilhan.flickpicks.presentation.navigation.Screen
import com.lcsmilhan.flickpicks.presentation.screens.component.BottomBarItem
import com.lcsmilhan.flickpicks.presentation.watch_list_screen.WatchListViewModel
import com.lcsmilhan.flickpicks.presentation.watch_list_screen.component.WatchListItem

@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    navController: NavController
) {
    val watchListState by viewModel.watchListState.collectAsStateWithLifecycle()
    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomBarItem(
                onExploreClick = {
                    navController.navigate(Screen.ExplorerScreen.route)
                },
                onMyWatchListClick = { navController.navigate(Screen.WatchListScreen.route) },
                onMyFavoritesClick = { navController.navigate(Screen.FavoritesScreen.route) }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Watch List",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp)
            )
            when {
                watchListState.isLoading -> {
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
                watchListState.error.isNotEmpty() -> {
                    Text(
                        text = watchListState.error,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                watchListState.myWatchList.isNotEmpty() -> {
                    watchListState.myWatchList.forEach { movie ->
                        WatchListItem(
                            onItemClick = {
                                navController.navigate(
                                    "${Screen.MovieDetailsScreen.route}/${it.id}"
                                )
                            },
                            movie = listOf(movie)
                        )
                    }
                }
            }
        }
    }
}