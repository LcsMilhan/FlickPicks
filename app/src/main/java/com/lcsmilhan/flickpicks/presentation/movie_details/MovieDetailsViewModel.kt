package com.lcsmilhan.flickpicks.presentation.movie_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.domain.remote.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieTrailers = MutableStateFlow(MovieDetailsState())
    val movieTrailers = _movieTrailers.asStateFlow()

    private val _movieDetails = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()


    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("movie_id")?.let {
                loadMovieDetails(it)
                Log.w("viewmodel", "${_movieDetails.value.movieDetails}")
            }
        }
    }

    fun onEvent(event: MovieDetailsEvent) {
        when(event) {
            is MovieDetailsEvent.OnAddToFavorites -> {

            }
            is MovieDetailsEvent.OnAddToWatchlist -> {

            }
            is MovieDetailsEvent.OnRemoveFromFavorites -> {

            }
            is MovieDetailsEvent.OnRemoveFromWatchlist -> {

            }
            is MovieDetailsEvent.OnTrailerClick -> {
                loadMovieTrailers(event.movieId)
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


    fun loadMovieTrailers(movieId: Int) {
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