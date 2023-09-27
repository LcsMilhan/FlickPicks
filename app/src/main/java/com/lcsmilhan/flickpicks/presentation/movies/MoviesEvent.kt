package com.lcsmilhan.flickpicks.presentation.movies

import com.lcsmilhan.flickpicks.domain.model.Genre

sealed class MoviesEvent {
    data class FilterByGenre(val movieGenre: List<Genre>): MoviesEvent()
    data class Search(val keyword: String): MoviesEvent()
    object ToggleFilterSection: MoviesEvent()
}
