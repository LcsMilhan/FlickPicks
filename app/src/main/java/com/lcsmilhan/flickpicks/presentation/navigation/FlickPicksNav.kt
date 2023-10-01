package com.lcsmilhan.flickpicks.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lcsmilhan.flickpicks.presentation.MovieDetails
import com.lcsmilhan.flickpicks.presentation.MovieScreen

@Composable
fun FlickPicksNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            MovieScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetails.route + "/{movie_id}",
            arguments = listOf(navArgument(name = "movie_id") {
                type = NavType.IntType
            })
        ) {navBackStackEntry ->
            navBackStackEntry.arguments?.getInt("movie_id")?.let {
                MovieDetails()
            }
        }
    }

}