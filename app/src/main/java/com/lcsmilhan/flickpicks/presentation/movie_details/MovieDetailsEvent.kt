package com.lcsmilhan.flickpicks.presentation.movie_details

sealed class MovieDetailsEvent {
    data class OnTrailerClick(val movieId: Int) : MovieDetailsEvent()
    data class OnAddToFavorites(val posterPath: String) : MovieDetailsEvent()
    data class OnAddToWatchlist(val posterPath: String) : MovieDetailsEvent()
    data class OnRemoveFromFavorites(val posterPath: String) : MovieDetailsEvent()
    data class OnRemoveFromWatchlist(val posterPath: String) : MovieDetailsEvent()
}
