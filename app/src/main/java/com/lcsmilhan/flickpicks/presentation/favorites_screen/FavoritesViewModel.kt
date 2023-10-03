package com.lcsmilhan.flickpicks.presentation.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcsmilhan.flickpicks.domain.local.repository.MyFavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val myFavoritesRepository: MyFavoritesRepository
) : ViewModel() {

    private val _favoritesState = MutableStateFlow(FavoritesState())
    val favoritesState = _favoritesState.asStateFlow()

    init {
        loadFavoriteMovies()
    }

    private fun loadFavoriteMovies() {
        viewModelScope.launch {
            _favoritesState.value = _favoritesState.value.copy(isLoading = true)
            try {
                myFavoritesRepository.getMyMovies().collect { favoriteMovies ->
                    _favoritesState.value = _favoritesState.value.copy(
                        myFavorites = favoriteMovies,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                _favoritesState.value = _favoritesState.value.copy(
                    error = e.localizedMessage ?: "Failed to load favorites",
                    isLoading = false
                )
            }
        }
    }

}