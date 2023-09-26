package com.lcsmilhan.flickpicks.data.remote.dto

import com.lcsmilhan.flickpicks.domain.model.Video

data class VideoDto(
    val id: String,
    val key: String,
    val name: String,
)

fun VideoDto.toVideo(): Video {
    return Video(
        id = id,
        key = key,
        name = name
    )
}