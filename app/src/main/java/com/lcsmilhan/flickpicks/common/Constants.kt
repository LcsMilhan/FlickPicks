package com.lcsmilhan.flickpicks.common

import com.lcsmilhan.flickpicks.BuildConfig

object Constants {
    const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val IMAGE_URL = "https://image.tmdb.org/t/p/w400"

    const val DB_NAME = "my_movies_db"

    fun getPosterPath(posterPath: String?): String {
        return IMAGE_URL + posterPath
    }

    fun getBackDropPath(backDropPath: String?): String {
        return IMAGE_URL + backDropPath
    }

}