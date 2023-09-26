package com.lcsmilhan.flickpicks.common

import com.lcsmilhan.flickpicks.BuildConfig

object Constants {
    const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY
    const val YT_API_KEY = BuildConfig.YT_API_KEY

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val DATABASE_NAME = "movies_db"

    private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/original"



    fun getPosterPath(posterPath: String?): String {
        return BASE_URL_IMAGE + posterPath
    }

    fun getBackDropPath(backDropPath: String?): String {
        return BASE_URL_IMAGE + backDropPath
    }

}