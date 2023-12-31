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
import com.lcsmilhan.flickpicks.presentation.navigation.Screen
import com.lcsmilhan.flickpicks.presentation.screens.component.BottomBarItem
import com.lcsmilhan.flickpicks.presentation.watch_list_screen.WatchListViewModel
import com.lcsmilhan.flickpicks.presentation.watch_list_screen.component.WatchListItem

@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val watchListState by viewModel.watchListState.collectAsStateWithLifecycle()

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
                text = "Watch List",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 15.dp, top = 20.dp)
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
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(watchListState.myWatchList) { movie ->
                            WatchListItem(
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
