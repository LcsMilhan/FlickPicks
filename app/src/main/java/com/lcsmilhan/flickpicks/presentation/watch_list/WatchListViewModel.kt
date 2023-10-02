package com.lcsmilhan.flickpicks.presentation.watch_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.domain.local.repository.MyWatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val myWatchListRepository: MyWatchListRepository
) : ViewModel() {

    private val _watchListState = MutableStateFlow(WatchListState())
    val watchListState = _watchListState.asStateFlow()

    init {
        loadWatchListMovies()
    }

    private fun loadWatchListMovies() {
        viewModelScope.launch {
            _watchListState.value = _watchListState.value.copy(isLoading = true)
            try {
                myWatchListRepository.getMyWatchList().collect { watchListMovies ->
                    _watchListState.value = _watchListState.value.copy(
                        myWatchList = watchListMovies,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _watchListState.value = _watchListState.value.copy(
                    error = e.localizedMessage ?: "Failed to load watch list",
                    isLoading = false
                )
            }
        }
    }

}