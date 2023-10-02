package com.lcsmilhan.flickpicks.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object MovieDetailsScreen: Screen("movie_details_screen")
    object FavoritesScreen: Screen("favorites_screen")
    object WatchListScreen: Screen("watch_list_screen")
}