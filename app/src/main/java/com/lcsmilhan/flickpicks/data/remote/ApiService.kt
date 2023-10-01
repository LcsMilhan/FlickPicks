package com.lcsmilhan.flickpicks.data.remote

import com.lcsmilhan.flickpicks.common.Constants.TMDB_API_KEY
import com.lcsmilhan.flickpicks.data.remote.model.GenreResponse
import com.lcsmilhan.flickpicks.data.remote.model.MovieResponse
import com.lcsmilhan.flickpicks.data.remote.model.VideoResponse
import com.lcsmilhan.flickpicks.domain.remote.model.Details
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<Details>

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<GenreResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): Response<VideoResponse>

}