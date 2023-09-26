package com.lcsmilhan.flickpicks.data.repository.remote

import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.remote.ApiService
import com.lcsmilhan.flickpicks.data.remote.dto.GenresDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDetailsDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDto
import com.lcsmilhan.flickpicks.data.remote.dto.VideoDto
import com.lcsmilhan.flickpicks.domain.repository.remote.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<Resource<List<MovieDto>>> = flow {
        try {
            emit(Resource.Loading())
            val popularMovies = api.getPopularMovies()
            emit(Resource.Success(listOf(popularMovies)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getTopRatedMovies(): Flow<Resource<List<MovieDto>>> = flow {
        try {
            emit(Resource.Loading())
            val topRatedMovies = api.getTopRatedMovies()
            emit(Resource.Success(listOf(topRatedMovies)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getUpcomingMovies(): Flow<Resource<List<MovieDto>>> = flow {
        try {
            emit(Resource.Loading())
            val upcomingMovies = api.getUpcomingMovies()
            emit(Resource.Success(listOf(upcomingMovies)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getSearchMovies(keyword: String): Flow<Resource<List<MovieDto>>> = flow {
        try {
            emit(Resource.Loading())
            val searchResults = api.getSearchMovie(keyword)
            emit(Resource.Success(listOf(searchResults)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getMovieGenres(): Flow<Resource<List<GenresDto>>> = flow {
        try {
            emit(Resource.Loading())
            val genres = api.getMovieGenres()
            emit(Resource.Success(listOf(genres)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getMovieTrailers(id: Int): Flow<Resource<List<VideoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val movieTrailers = api.getMovieTrailers(id)
            emit(Resource.Success(listOf(movieTrailers)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Resource<List<MovieDetailsDto>>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetails = api.getMovieDetails(id)
            emit(Resource.Success(listOf(movieDetails)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}