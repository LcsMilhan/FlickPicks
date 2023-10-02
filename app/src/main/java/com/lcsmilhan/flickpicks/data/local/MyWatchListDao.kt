package com.lcsmilhan.flickpicks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lcsmilhan.flickpicks.domain.local.model.WatchList
import kotlinx.coroutines.flow.Flow

@Dao
interface MyWatchListDao {

    @Query("SELECT * FROM watch_list_table")
    fun getWatchList(): Flow<List<WatchList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(myWatchList: WatchList)

    @Delete
    suspend fun deleteMovie(myWatchList: WatchList)

}