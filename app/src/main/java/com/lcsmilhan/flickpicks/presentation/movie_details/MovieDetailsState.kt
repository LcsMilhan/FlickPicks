package com.lcsmilhan.flickpicks.presentation.movie_details

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import com.lcsmilhan.flickpicks.domain.remote.model.Video

data class MovieDetailsState(
    val movieDetails: Details? = null,
    @SerializedName("results") val movieTrailers: List<Video> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isFavorite: Boolean = false
)
