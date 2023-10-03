package com.lcsmilhan.flickpicks.presentation.watch_list_screen

import com.lcsmilhan.flickpicks.domain.local.model.WatchList

data class WatchListState(
    val myWatchList: List<WatchList> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
