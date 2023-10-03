package com.lcsmilhan.flickpicks.presentation.home.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.domain.remote.model.Movie
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SearchBarItem(
    onValueChange: () -> Unit,
    movie: List<Movie>
) {
    val focusRequester = remember { FocusRequester() }
    var isSearchFocused by remember { mutableStateOf(false) }
    var searchKeyword by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)) {
            OutlinedTextField(
                value = searchKeyword,
                onValueChange = {
                    searchKeyword = it
                    onValueChange()
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
                    focusedLabelColor = MaterialTheme.colorScheme.secondary.copy(0.6f),
                    unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.6f),
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.secondary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.secondary.copy(0.6f),
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
                    SearchItems(
                        image = Constants.getPosterPath(it.posterPath),
                        title = it.title,
                        voteAverage = it.voteAverage,
                        releaseDate = it.releaseDate
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SearchItems(
    image: String,
    title: String,
    voteAverage: Double,
    releaseDate: String
) {

    var visible by remember { mutableStateOf(false) }

    val fadeInDuration = 500
    val fadeOutDuration = 300

    val fadeInAnimation = rememberInfiniteTransition(label = "")
    val fadeInAlpha by fadeInAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = fadeInDuration),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val fadeOutAnimation = rememberInfiniteTransition(label = "")
    val fadeOutAlpha by fadeOutAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = fadeOutDuration),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .graphicsLayer(
                alpha = if (visible) fadeInAlpha else fadeOutAlpha,
                translationY = if (visible) 0f else 20f
            )
    ) {
        GlideImage(
            model = image,
            contentDescription = "Thumb Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp))
                .shadow(elevation = 5.dp)
        )

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, top = 5.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            verticalAlignment = CenterVertically,
        ) {
            Text(
                text = dateFormat(releaseDate),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.width(50.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star icon",
                tint = Color(android.graphics.Color.parseColor("#ac8f4a")),
                modifier = Modifier.size(22.dp)
            )
            val decimalFormat = DecimalFormat("#.#")
            val formattedVoteAverage = decimalFormat.format(voteAverage)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = formattedVoteAverage,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}


fun dateFormat(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy")
    val date = LocalDate.parse(dateString, inputFormatter)
    return outputFormatter.format(date)
}


@Preview
@Composable
fun SearchBarPreview() {

    val movie = Movie(
        id = 565770,
        title = "Blue Beetle",
        video = false,
        overview = "dasdasdas",
        backdropPath = "asdas",
        genreIds = listOf(1,2,3),
        posterPath = "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
        releaseDate = "2023-08-16",
        voteAverage = 7.116
    )

    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        SearchBarItem(onValueChange = { /*TODO*/ }, movie = listOf(movie))
    }
}