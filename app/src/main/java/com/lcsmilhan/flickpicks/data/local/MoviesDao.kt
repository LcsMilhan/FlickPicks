package com.lcsmilhan.flickpicks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movie: MyListMovie)

    @Delete
    suspend fun removeFromFavorite(movie: MyListMovie)

    @Query("SELECT * FROM favorite_table")
    fun getAllFavorites(): Flow<List<MyListMovie>>

}