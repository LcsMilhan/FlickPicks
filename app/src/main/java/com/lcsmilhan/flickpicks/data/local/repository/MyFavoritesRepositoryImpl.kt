package com.lcsmilhan.flickpicks.data.local.repository

import com.lcsmilhan.flickpicks.data.local.MyFavoritesDao
import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import com.lcsmilhan.flickpicks.domain.local.repository.MyFavoritesRepository
import kotlinx.coroutines.flow.Flow

class MyFavoritesRepositoryImpl(
    private val dao: MyFavoritesDao
) : MyFavoritesRepository {

    override fun getMyMovies(): Flow<List<Favorites>> {
        return dao.getFavorites()
    }

    override suspend fun insertMovie(movie: Favorites) {
        return dao.insertMovie(movie)
    }

    override suspend fun deleteMovie(movie: Favorites) {
        return dao.deleteMovie(movie)
    }

}