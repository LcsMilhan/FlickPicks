package com.lcsmilhan.flickpicks.data.local.repository

import com.lcsmilhan.flickpicks.data.local.MyWatchListDao
import com.lcsmilhan.flickpicks.domain.local.model.WatchList
import com.lcsmilhan.flickpicks.domain.local.repository.MyWatchListRepository
import kotlinx.coroutines.flow.Flow

class MyWatchListRepositoryImpl(
    private val dao: MyWatchListDao
) : MyWatchListRepository {

    override fun getMyWatchList(): Flow<List<WatchList>> {
        return dao.getWatchList()
    }

    override suspend fun insertWatchList(watchList: WatchList) {
        return dao.insertMovie(watchList)
    }

    override suspend fun deleteWatchList(watchList: WatchList) {
        return dao.deleteMovie(watchList)
    }
}