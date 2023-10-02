package com.lcsmilhan.flickpicks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import com.lcsmilhan.flickpicks.domain.local.model.WatchList


@Database(
    entities = [Favorites::class, WatchList::class],
    version = 4,
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favoritesDao: MyFavoritesDao
    abstract val watchListDao: MyWatchListDao
}