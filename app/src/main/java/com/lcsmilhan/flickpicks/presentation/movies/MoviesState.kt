package com.lcsmilhan.flickpicks.presentation.movies

import com.lcsmilhan.flickpicks.data.util.MovieFilter
import com.lcsmilhan.flickpicks.data.util.OrderType
import com.lcsmilhan.flickpicks.domain.model.Genre
import com.lcsmilhan.flickpicks.domain.model.Movie

data class MoviesState(
    val movies: List<Movie> = emptyList(),
    val movieFilter: MovieFilter = MovieFilter.Genre(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val searchResults: List<Movie> = emptyList(),
    val currentQuery: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val selectedGenre: Genre? = null
)
