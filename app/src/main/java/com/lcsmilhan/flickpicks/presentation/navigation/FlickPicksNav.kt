package com.lcsmilhan.flickpicks.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lcsmilhan.flickpicks.presentation.screens.ExplorerScreen
import com.lcsmilhan.flickpicks.presentation.screens.FavoritesScreen
import com.lcsmilhan.flickpicks.presentation.screens.MovieDetailsScreen
import com.lcsmilhan.flickpicks.presentation.screens.WatchListScreen

@Composable
fun FlickPicksNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ExplorerScreen.route,
    ) {
        composable(Screen.ExplorerScreen.route) {
            ExplorerScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.MovieDetailsScreen.route + "/{movie_id}",
            arguments = listOf(navArgument(name = "movie_id") {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getInt("movie_id")
            movieId?.let {
                MovieDetailsScreen(navController = navController, movieId = it)
            }
        }
        composable(Screen.FavoritesScreen.route) {
            FavoritesScreen(navController = navController)
        }
        composable(Screen.WatchListScreen.route) {
            WatchListScreen(navController = navController)
        }
    }

}