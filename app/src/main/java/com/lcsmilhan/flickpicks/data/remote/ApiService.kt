package com.lcsmilhan.flickpicks.data.remote

import com.lcsmilhan.flickpicks.common.Constants.TMDB_API_KEY
import com.lcsmilhan.flickpicks.data.remote.dto.GenresDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDetailsDto
import com.lcsmilhan.flickpicks.data.remote.dto.MovieDto
import com.lcsmilhan.flickpicks.data.remote.dto.VideoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): MovieDto

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("query") keyword: String,
        @Query("api_key") apiKey: String = TMDB_API_KEY
    ): MovieDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): MovieDetailsDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): VideoDto

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = TMDB_API_KEY,
    ): GenresDto

}