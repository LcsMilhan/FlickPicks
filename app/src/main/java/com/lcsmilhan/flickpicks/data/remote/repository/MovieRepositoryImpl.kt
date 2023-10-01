package com.lcsmilhan.flickpicks.data.remote.repository

import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.remote.ApiService
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.model.Movie
import com.lcsmilhan.flickpicks.domain.remote.model.Video
import com.lcsmilhan.flickpicks.domain.remote.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MovieRepository {

    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getPopularMovies()
            if (movies.isSuccessful) {
                val popularMovies = movies.body()?.movies
                if (popularMovies != null) {
                    emit(Resource.Success(popularMovies))
                }
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getTopRatedMovies()
            if (movies.isSuccessful) {
                val topRatedMovies = movies.body()?.movies
                if (topRatedMovies != null) {
                    emit(Resource.Success(topRatedMovies))
                }
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getUpcomingMovies()
            if (movies.isSuccessful) {
                val upcomingMovies = movies.body()?.movies
                emit(Resource.Success(upcomingMovies))
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSearchMovies(query: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.getSearchMovie(query)
            if (movies.isSuccessful) {
                val searchMovies = movies.body()?.movies
                emit(Resource.Success(searchMovies))
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieDetails(movieId: Int): Flow<Resource<Details>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getMovieDetails(movieId)
            if (response.isSuccessful) {
                val movieDetails = response.body()
                if (movieDetails != null) {
                    emit(Resource.Success(movieDetails))
                } else {
                    emit(Resource.Error("No data available"))
                }
            } else {
                emit(Resource.Error("Failed to fetch data"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("Bad Request: ${e.message}"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.message}"))
        }
    }.flowOn(Dispatchers.IO)


    override suspend fun getMovieGenres(): Flow<Resource<List<Genre>>> = flow {
        try {
            emit(Resource.Loading())
            val genres = api.getMovieGenres()
            if (genres.isSuccessful) {
                val moviesGenres = genres.body()?.genres
                emit(Resource.Success(moviesGenres))
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMoviesByGenre(genreId: Int): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = api.moviesByGenre(genreId.toString())
            if (movies.isSuccessful) {
                val moviesByGenre = movies.body()?.movies
                emit(Resource.Success(moviesByGenre))
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieTrailers(movieId: Int): Flow<Resource<List<Video>>> = flow {
        try {
            emit(Resource.Loading())
            val trailers = api.getMovieTrailers(movieId)
            if (trailers.isSuccessful) {
                val movieTrailers = trailers.body()?.videos
                emit(Resource.Success(movieTrailers))
            }
        } catch (e: HttpException) {
            val errorCode = e.code()
            val errorMessage = e.message()
            when(errorCode) {
                400 -> emit(Resource.Error("Bad Request: $errorMessage"))
                404 -> emit(Resource.Error("Not Found: $errorMessage"))
                500 -> emit(Resource.Error("Internal Server Error: $errorMessage"))
                else -> emit(Resource.Error("An unexpected error occurred: $errorMessage"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

}