package com.lcsmilhan.flickpicks.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.model.Genre
import com.lcsmilhan.flickpicks.domain.model.MovieDetails

data class MovieDetailsDto(
    val genres: List<Genre>,
    val id: Int,
    val overview: String?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double
)

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        genres = genres,
        id = id,
        overview = overview,
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        backgroundImage = backdropPath,
        thumbImage = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
