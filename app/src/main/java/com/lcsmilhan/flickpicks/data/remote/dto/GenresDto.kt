package com.lcsmilhan.flickpicks.data.remote.dto

import com.lcsmilhan.flickpicks.domain.model.Genre

data class GenresDto(
    val id: Int,
    val name: String
)

fun GenresDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}
