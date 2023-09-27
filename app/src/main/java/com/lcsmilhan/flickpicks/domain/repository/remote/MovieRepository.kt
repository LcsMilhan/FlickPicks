package com.lcsmilhan.flickpicks.domain.repository.remote

import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.remote.dto.GenresDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDetailsDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDto
import com.lcsmilhan.flickpicks.data.remote.dto.VideoDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getPopularMovies(): Flow<Resource<List<MovieDto>>>
    suspend fun getSearchMovies(keyword: String): Flow<Resource<List<MovieDto>>>
    suspend fun getMovieGenres(): List<GenresDto>
    suspend fun getMovieTrailers(id: Int): Flow<Resource<List<VideoDto>>>
    suspend fun getMovieDetails(id: Int): Flow<Resource<List<MovieDetailsDto>>>

}