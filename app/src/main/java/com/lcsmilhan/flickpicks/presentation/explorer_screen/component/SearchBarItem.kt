package com.lcsmilhan.flickpicks.presentation.explorer_screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.remote.model.Movie

@Composable
fun SearchBarItem(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    movie: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isSearchFocused by remember { mutableStateOf(false) }
    var searchKeyword by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchKeyword,
                onValueChange = {
                    searchKeyword = it
                    onValueChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { isSearchFocused = it.isFocused },
                singleLine = true,
                label = {
                    if (!isSearchFocused && searchKeyword.isEmpty()) {
                        Text(text = "Search by title")
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.6f),
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary.copy(0.6f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.6f)
                ),
                shape = CircleShape
            )
        }
        LazyRow(
            Modifier
                .fillMaxWidth()
                .padding(PaddingValues(6.dp))
        ) {
            items(movie) {
                movie.forEach {
                    MovieItem(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onMovieClick(it.id) },
                        image = Constants.getPosterPath(it.posterPath),
                        voteAverage = it.voteAverage,
                        releaseDate = it.releaseDate,
                    )
                }
            }
        }
    }
}
