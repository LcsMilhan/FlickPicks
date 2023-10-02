package com.lcsmilhan.flickpicks.presentation.movie_details

sealed class MovieDetailsEvent {
    data class OnTrailerClick(val movieId: Int) : MovieDetailsEvent()
    data class OnAddToFavorites(val movieId: Int) : MovieDetailsEvent()
    data class OnRemoveFromFavorites(val movieId: Int) : MovieDetailsEvent()
    data class OnAddToWatchList(val movieId: Int) : MovieDetailsEvent()
    data class OnRemoveFromWatchList(val movieId: Int) : MovieDetailsEvent()
}
