package com.lcsmilhan.flickpicks.presentation.home

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.model.Movie

data class HomeState(
    @SerializedName("results") val movies: List<Movie> = emptyList(),
    @SerializedName("genres") val genres: List<Genre> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)