package com.lcsmilhan.flickpicks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class MyListMovie(
    @PrimaryKey val id: Int,
    val imagePath: String?,
    val title: String,
    val releaseData: String
)