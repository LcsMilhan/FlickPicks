package com.lcsmilhan.flickpicks.domain.model

data class MovieDetails(
    val genres: List<Genre>,
    val id: Int,
    val overview: String?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
)

