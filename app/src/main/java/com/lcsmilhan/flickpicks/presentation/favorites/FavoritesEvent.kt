package com.lcsmilhan.flickpicks.presentation.favorites

sealed class FavoritesEvent {
    data class OnMovieClick(val movieId: Int): FavoritesEvent()
}
