package com.lcsmilhan.flickpicks.presentation.explorer_screen

sealed class ExplorerEvent {
    data class SelectedGenre(val genreId: Int): ExplorerEvent()
    data class OnSearchClick(val keyword: String): ExplorerEvent()
    object CleanFilter: ExplorerEvent()
}
