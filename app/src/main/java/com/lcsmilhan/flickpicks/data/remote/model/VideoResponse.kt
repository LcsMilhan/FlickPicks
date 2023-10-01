package com.lcsmilhan.flickpicks.data.remote.model

import com.google.gson.annotations.SerializedName
import com.lcsmilhan.flickpicks.domain.remote.model.Video

data class VideoResponse(
    val id: Int,
    @SerializedName("results") val videos: List<Video>
)
