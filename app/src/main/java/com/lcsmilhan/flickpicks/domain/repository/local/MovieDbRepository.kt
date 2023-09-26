package com.lcsmilhan.flickpicks.domain.repository.local

import com.lcsmilhan.flickpicks.data.local.MyListMovie
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getAllFavorites(): Flow<List<MyListMovie>>
    suspend fun addToFavorite(movie: MyListMovie)
    suspend fun removeFromFavorite(movie: MyListMovie)

}