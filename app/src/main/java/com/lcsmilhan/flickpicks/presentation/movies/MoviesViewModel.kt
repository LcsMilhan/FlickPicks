package com.lcsmilhan.flickpicks.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.common.Resource
import com.lcsmilhan.flickpicks.data.remote.dto.toGenre
import com.lcsmilhan.flickpicks.data.remote.dto.toMovie
import com.lcsmilhan.flickpicks.data.util.MovieFilter
import com.lcsmilhan.flickpicks.data.util.OrderType
import com.lcsmilhan.flickpicks.domain.model.Movie
import com.lcsmilhan.flickpicks.domain.repository.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow(MoviesState())
    val moviesState = _moviesState.asStateFlow()

    init {
        loadMovies()
    }

    fun onEvent(event: MoviesEvent) {
        when(event) {
            is MoviesEvent.FilterByGenre -> {
                viewModelScope.launch {
                    try {
                        val allGenres = movieRepository.getMovieGenres().map { it.toGenre() }
                        val selectedGenre = event.movieGenre.firstOrNull()
                        val filteredGenres = if (selectedGenre != null) {
                            allGenres.filter { it == selectedGenre }
                        } else {
                            allGenres
                        }
                        _moviesState.value = _moviesState.value.copy(
                            movieFilter = MovieFilter.Genre(OrderType.Descending),
                            isOrderSectionVisible = false,
                        )
                    } catch (e: Exception) {
                        _moviesState.value = _moviesState.value.copy(
                        )
                    }
                }
            }
            is MoviesEvent.Search -> {
                viewModelScope.launch {
                    try {
                        val searchResultsFlow = movieRepository.getSearchMovies(event.keyword)
                        val searchResultsList = mutableListOf<Movie>()
                        searchResultsFlow.collect { resource ->
                            if (resource is Resource.Success) {
                                resource.data?.let { movieDto ->
                                    val movies = movieDto.map { it.toMovie() }
                                    searchResultsList.addAll(movies)
                                }
                            } else if (resource is Resource.Error) {
                                _moviesState.value = _moviesState.value.copy(
                                )
                            }
                        }
                        _moviesState.value = _moviesState.value.copy(
                            searchResults = searchResultsList,
                        )
                    } catch (e: Exception) {
                        _moviesState.value = _moviesState.value.copy(
                        )
                    }
                }
            }
            MoviesEvent.ToggleFilterSection -> {
                _moviesState.value = _moviesState.value.copy(
                    isOrderSectionVisible = !_moviesState.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun loadMovies() {
        viewModelScope.launch{
            movieRepository.getPopularMovies().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val moviesDto = result.data ?: emptyList()
                        val movies = moviesDto.map { movieDto ->
                            movieDto.toMovie()
                        }
                        _moviesState.value = MoviesState(movies)
                    }
                    is Resource.Error -> {
                        _moviesState.value = MoviesState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _moviesState.value = MoviesState(isLoading = true)
                    }
                }
            }
        }
    }
}