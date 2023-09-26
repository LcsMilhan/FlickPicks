package com.lcsmilhan.flickpicks.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val video: Boolean,
    val backgroundImage: String?,
    val thumbImage: String?,
    val genreIds: List<Int>,
    val releaseDate: String,
    val voteAverage: Double,
)