package com.lcsmilhan.flickpicks.domain.remote.model

import com.google.gson.annotations.SerializedName

data class Details(
    val id: Int,
    val overview: String,
    val title: String,
    val video: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
)
