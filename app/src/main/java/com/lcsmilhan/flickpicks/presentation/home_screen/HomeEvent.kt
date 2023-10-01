package com.lcsmilhan.flickpicks.presentation.home_screen

sealed class HomeEvent {
    data class SelectedGenre(val genreId: Int): HomeEvent()
    data class OnMovieClick(val movieId: Int): HomeEvent()
    data class OnSearchClick(val keyword: String): HomeEvent()
    object CleanFilter: HomeEvent()
}
