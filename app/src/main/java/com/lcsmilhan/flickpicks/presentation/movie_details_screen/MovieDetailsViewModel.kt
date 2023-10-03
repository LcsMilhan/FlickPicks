package com.lcsmilhan.flickpicks.presentation.movie_details_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.local.mapper.toFavorites
import com.lcsmilhan.flickpicks.data.local.mapper.toWatchList
import com.lcsmilhan.flickpicks.domain.local.model.Favorites
import com.lcsmilhan.flickpicks.domain.local.model.WatchList
import com.lcsmilhan.flickpicks.domain.local.repository.MyFavoritesRepository
import com.lcsmilhan.flickpicks.domain.local.repository.MyWatchListRepository
import com.lcsmilhan.flickpicks.domain.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val myFavoritesRepository: MyFavoritesRepository,
    private val myWatchListRepository: MyWatchListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieTrailers = MutableStateFlow(MovieDetailsState())
    val movieTrailers = _movieTrailers.asStateFlow()

    private val _movieDetails = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()

    private val _myFavorites = MutableStateFlow<List<Favorites>>(emptyList())
    val myFavorites = _myFavorites.asStateFlow()

    private val _myWatchList = MutableStateFlow<List<WatchList>>(emptyList())
    val myWatchList = _myWatchList.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("movie_id")?.let {
                loadMovieDetails(it)
                loadMovieTrailers(it)
                loadFavorites()
                loadWatchList()
            }
        }
    }

    fun onEvent(event: MovieDetailsEvent) {
        when(event) {
            is MovieDetailsEvent.OnAddToFavorites -> {
                viewModelScope.launch {
                    _movieDetails.value.movieDetails?.toFavorites()?.let {
                        myFavoritesRepository.insertMovie(it)

                        val updatedMyFavoritesList = _myFavorites.value.toMutableList()
                        updatedMyFavoritesList.add(it)
                        _myFavorites.value = updatedMyFavoritesList.toList()
                    }
                }
            }
            is MovieDetailsEvent.OnRemoveFromFavorites -> {
                viewModelScope.launch {
                    _movieDetails.value.movieDetails?.toFavorites()?.let {
                        myFavoritesRepository.deleteMovie(it)

                        val updatedMyFavoritesList = _myFavorites.value.toMutableList()
                        updatedMyFavoritesList.remove(it)
                        _myFavorites.value = updatedMyFavoritesList.toList()
                    }
                }
            }
            is MovieDetailsEvent.OnAddToWatchList -> {
                viewModelScope.launch {
                    _movieDetails.value.movieDetails?.toWatchList()?.let {
                        myWatchListRepository.insertWatchList(it)

                        val updatedMyWatchList = _myWatchList.value.toMutableList()
                        updatedMyWatchList.add(it)
                        _myWatchList.value = updatedMyWatchList.toList()
                    }
                }
            }
            is MovieDetailsEvent.OnRemoveFromWatchList -> {
                viewModelScope.launch {
                    _movieDetails.value.movieDetails?.toWatchList()?.let {
                        myWatchListRepository.deleteWatchList(it)

                        val updatedMyWatchList = _myWatchList.value.toMutableList()
                        updatedMyWatchList.remove(it)
                        _myWatchList.value = updatedMyWatchList.toList()
                    }
                }
            }
        }
    }

    private fun loadWatchList() {
        viewModelScope.launch {
            myWatchListRepository.getMyWatchList().collect { watchList ->
                _myWatchList.value = watchList
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            myFavoritesRepository.getMyMovies().collect { favorites ->
                _myFavorites.value = favorites
            }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collect { result ->
                val movieDetailsState = when (result) {
                    is Resource.Success -> {
                        MovieDetailsState(movieDetails = result.data)
                    }
                    is Resource.Error -> {
                        MovieDetailsState(error = result.message ?: "An unexpected error occurred")
                    }
                    is Resource.Loading -> {
                        MovieDetailsState(isLoading = true)
                    }
                }
                _movieDetails.value = movieDetailsState
            }
        }
    }

    private fun loadMovieTrailers(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieTrailers(movieId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _movieTrailers.value = MovieDetailsState(
                            movieTrailers = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _movieTrailers.value = MovieDetailsState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _movieTrailers.value = MovieDetailsState(isLoading = true)
                    }
                }
            }
        }
    }
}