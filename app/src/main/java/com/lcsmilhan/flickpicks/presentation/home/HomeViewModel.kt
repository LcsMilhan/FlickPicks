package com.lcsmilhan.flickpicks.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.domain.remote.model.Genre
import com.lcsmilhan.flickpicks.domain.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _popularState = MutableStateFlow(HomeState())
    val popularState = _popularState.asStateFlow()

    private val _topRatedState = MutableStateFlow(HomeState())
    val topRatedState = _topRatedState.asStateFlow()

    private val _upcomingState = MutableStateFlow(HomeState())
    val upcomingState = _upcomingState.asStateFlow()

    private val _genreState = MutableStateFlow(HomeState())
    val genreState = _genreState.asStateFlow()

    private val _searchResults = MutableStateFlow(HomeState())
    val searchResults = _searchResults.asStateFlow()

    private var allGenres: List<Genre>? = null
    private var selectedGenre: Int? = null
    private var searchQuery: String? = null
    private var currentMovieId: Int? = null

    init {
        viewModelScope.launch {
            loadPopularMovies()
            loadTopRatedMovies()
            loadUpcomingMovies()
            loadAllGenres()
            Log.d("viewmodel", "${_popularState.value.movies}")
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.OnMovieClick -> {
                currentMovieId = event.movieId
            }
            is HomeEvent.OnSearchClick -> {
                viewModelScope.launch {
                    searchQuery = event.keyword
                    searchMovies(searchQuery!!)
                }
            }
            is HomeEvent.SelectedGenre -> {
                viewModelScope.launch {
                    selectedGenre = event.genreId
                    loadMoviesByGenre(selectedGenre!!)
                }
            }
            HomeEvent.CleanFilter -> {
                selectedGenre = null
            }
        }
    }

    private suspend fun searchMovies(keyword: String) {
        repository.getSearchMovies(keyword).collect { result ->
            when(result) {
                is Resource.Success -> {
                    _searchResults.value = HomeState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _searchResults.value = HomeState(
                        error = "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _searchResults.value = HomeState(isLoading = true)
                }
            }
        }
    }

    private suspend fun loadPopularMovies() {
        repository.getPopularMovies().collect { result ->
            when(result) {
                is Resource.Success -> {
                    _popularState.value = HomeState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _popularState.value = HomeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _popularState.value = HomeState(
                        isLoading =  true
                    )
                }
            }
        }
    }

    private suspend fun loadTopRatedMovies() {
        repository.getTopRatedMovies().collect { result ->
            when(result) {
                is Resource.Success -> {
                    _topRatedState.value = HomeState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _topRatedState.value = HomeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _topRatedState.value = HomeState(
                        isLoading =  true
                    )
                }
            }
        }
    }

    private suspend fun loadUpcomingMovies() {
        repository.getUpcomingMovies().collect { result ->
            when(result) {
                is Resource.Success -> {
                    _upcomingState.value = HomeState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _upcomingState.value = HomeState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _upcomingState.value = HomeState(
                        isLoading =  true
                    )
                }
            }
        }
    }

    private suspend fun loadAllGenres() {
        repository.getMovieGenres().collect { result ->
            when(result) {
                is Resource.Success -> {
                    allGenres = result.data ?: emptyList()
                    _genreState.value = HomeState(
                        genres = result.data ?: emptyList()
                    )
//                    Log.d("viewmodel", "$allGenres")
                }
                is Resource.Error -> {
                    _genreState.value = HomeState(
                        error = "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _genreState.value = HomeState(isLoading = true)
                }
            }
        }
    }

    private suspend fun loadMoviesByGenre(genreId: Int) {
        repository.getMoviesByGenre(genreId).collect { result ->
            when(result) {
                is Resource.Success -> {
                    _genreState.value = _genreState.value.copy(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _genreState.value = _genreState.value.copy(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _genreState.value = _genreState.value.copy(isLoading = true)
                }
            }
        }
    }

}