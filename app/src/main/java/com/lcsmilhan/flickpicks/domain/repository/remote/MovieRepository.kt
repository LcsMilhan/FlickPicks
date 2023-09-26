package com.lcsmilhan.flickpicks.domain.repository.remote

import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.remote.dto.GenresDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDetailsDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDto
import com.lcsmilhan.flickpicks.data.remote.dto.VideoDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<Resource<List<MovieDto>>>
    suspend fun getTopRatedMovies(): Flow<Resource<List<MovieDto>>>
    suspend fun getUpcomingMovies(): Flow<Resource<List<MovieDto>>>
    suspend fun getSearchMovies(query: String): Flow<Resource<List<MovieDto>>>
    suspend fun getMovieGenres(): Flow<Resource<List<GenresDto>>>
    suspend fun getMovieTrailers(id: Int): Flow<Resource<List<VideoDto>>>
    suspend fun getMovieDetails(id: Int): Flow<Resource<List<MovieDetailsDto>>>

}