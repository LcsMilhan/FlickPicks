package com.lcsmilhan.flickpicks.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object MovieDetails: Screen("movie_details_screen")
}