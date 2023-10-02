package com.lcsmilhan.flickpicks.domain.local.repository

import com.lcsmilhan.flickpicks.domain.local.model.WatchList
import kotlinx.coroutines.flow.Flow

interface MyWatchListRepository {
    fun getMyWatchList(): Flow<List<WatchList>>
    suspend fun insertWatchList(watchList: WatchList)
    suspend fun deleteWatchList(watchList: WatchList)

}