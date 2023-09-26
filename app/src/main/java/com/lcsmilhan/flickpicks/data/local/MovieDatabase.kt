package com.lcsmilhan.flickpicks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [MyListMovie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao

}