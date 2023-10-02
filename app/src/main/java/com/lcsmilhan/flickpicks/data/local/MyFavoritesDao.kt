package com.lcsmilhan.flickpicks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import kotlinx.coroutines.flow.Flow


@Dao
interface MyFavoritesDao {

    @Query("SELECT * FROM favorites_table")
    fun getFavorites(): Flow<List<Favorites>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(myMovies: Favorites)

    @Delete
    suspend fun deleteMovie(myMovies: Favorites)

}