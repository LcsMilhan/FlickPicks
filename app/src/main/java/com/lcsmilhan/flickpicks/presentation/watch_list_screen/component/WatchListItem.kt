package com.lcsmilhan.flickpicks.presentation.watch_list_screen.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.local.model.WatchList

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WatchListItem(
    onItemClick: (WatchList) -> Unit,
    movie: List<WatchList>
) {

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(movie) { movie ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onItemClick(movie) },
                elevation = CardDefaults.cardElevation(
                    pressedElevation = 10.dp,
                    defaultElevation = 5.dp,
                    focusedElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            model = Constants.getPosterPath(movie.posterPath),
                            contentDescription = "Thumb image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Column(
                            Modifier
                                .fillMaxHeight()
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            movie.releaseDate?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star, 
                                    contentDescription = "Star icon",
                                    tint = Color(parseColor("#ac8f4a")),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = movie.voteAverage.toString(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}