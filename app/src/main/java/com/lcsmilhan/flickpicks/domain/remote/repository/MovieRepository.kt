package com.lcsmilhan.flickpicks.domain.remote.repository

import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.model.Movie
import com.lcsmilhan.flickpicks.domain.remote.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>>
    suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    suspend fun getSearchMovies(query: String): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(movieId: Int): Flow<Resource<Details>>
    suspend fun getMovieGenres(): Flow<Resource<List<Genre>>>
    suspend fun getMoviesByGenre(genreId: Int): Flow<Resource<List<Movie>>>
    suspend fun getMovieTrailers(movieId: Int): Flow<Resource<List<Video>>>
}