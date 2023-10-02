package com.lcsmilhan.flickpicks.presentation.favorites

import com.lcsmilhan.flickpicks.domain.local.model.Favorites

data class FavoritesState(
    val myFavorites: List<Favorites> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
