package com.lcsmilhan.flickpicks.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.model.Movie

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    val video: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
)

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        video = video,
        backgroundImage = backdropPath,
        thumbImage = posterPath,
        genreIds = genreIds,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}