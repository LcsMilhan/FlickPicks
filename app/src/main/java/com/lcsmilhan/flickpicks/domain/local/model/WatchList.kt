package com.lcsmilhan.flickpicks.domain.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("watch_list_table")
data class WatchList(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val posterPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
)
