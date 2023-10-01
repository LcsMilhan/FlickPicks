package com.lcsmilhan.flickpicks.data.remote.model

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.remote.model.Movie

data class MovieResponse(
    val id: Int,
    @SerializedName("results") val movies: List<Movie>,
)
