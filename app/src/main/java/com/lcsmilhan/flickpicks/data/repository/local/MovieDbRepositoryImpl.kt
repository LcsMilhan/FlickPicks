package com.lcsmilhan.flickpicks.data.repository.local

import com.lcsmilhan.flickpicks.data.local.MoviesDao
import com.lcsmilhan.flickpicks.data.local.MyListMovie
import com.lcsmilhan.flickpicks.domain.repository.local.MovieDbRepository
import kotlinx.coroutines.flow.Flow

class MovieDbRepositoryImpl(
    private val dao: MoviesDao
) : MovieDbRepository {

    override fun getAllFavorites(): Flow<List<MyListMovie>> {
        return dao.getAllFavorites()
    }

    override suspend fun addToFavorite(movie: MyListMovie) {
        val myListMovie = MyListMovie(
            id = movie.id,
            imagePath = movie.imagePath,
            title = movie.title,
            releaseData = movie.releaseData
        )
        dao.addToFavorite(myListMovie)
    }

    override suspend fun removeFromFavorite(movie: MyListMovie) {
        dao.removeFromFavorite(movie)
    }

}