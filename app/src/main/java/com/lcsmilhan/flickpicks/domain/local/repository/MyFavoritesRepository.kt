package com.lcsmilhan.flickpicks.domain.local.repository

import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import kotlinx.coroutines.flow.Flow

interface MyFavoritesRepository {
    fun getMyMovies(): Flow<List<Favorites>>
    suspend fun insertMovie(movie: Favorites)
    suspend fun deleteMovie(movie: Favorites)

}