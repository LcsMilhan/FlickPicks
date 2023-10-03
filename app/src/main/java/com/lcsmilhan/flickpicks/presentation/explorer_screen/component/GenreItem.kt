package com.lcsmilhan.flickpicks.presentation.explorer_screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lcsmilhan.flickpicks.domain.remote.model.Genre

@Composable
fun GenreItem(
    genres: List<Genre>,
    onCleanClick: () -> Unit,
    selectedGenre: Genre?,
    onGenreClick: (Genre) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        item {
            Button(
                onClick = {
                    onCleanClick()
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGenre == null) MaterialTheme.colorScheme.tertiaryContainer
                    else MaterialTheme.colorScheme.primaryContainer,
                    contentColor = if (selectedGenre == null) MaterialTheme.colorScheme.onTertiaryContainer
                    else MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp,
                    pressedElevation = 10.dp
                )
            ) {
                Text(
                    text = "All",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        items(genres) { genre ->
            Button(
                onClick = {
                    onGenreClick(genre)
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGenre == genre) MaterialTheme.colorScheme.tertiaryContainer
                    else MaterialTheme.colorScheme.primaryContainer,
                    contentColor = if (selectedGenre == genre) MaterialTheme.colorScheme.onTertiaryContainer
                    else MaterialTheme.colorScheme.onPrimaryContainer
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp,
                    pressedElevation = 10.dp
                )
            ) {
                Text(
                    text = genre.name,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }
}
