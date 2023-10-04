package com.lcsmilhan.flickpicks.presentation.movie_details_screen.component

import android.graphics.Color.parseColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import com.lcsmilhan.flickpicks.presentation.explorer_screen.component.dateFormat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.DecimalFormat

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailsItem(
    modifier: Modifier = Modifier,
    backImage: String,
    releaseDate: String,
    voteAverage: Double,
    title: String,
    overview: String,
    onFavoriteClick: () -> Unit,
    onWatchListClick: () -> Unit,
    videoId: String,
    favoriteIcon: Painter,
    watchListIcon: Painter
) {

    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(10.dp)
    ) {
        GlideImage(
            model = Constants.getBackDropPath(backImage),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(CenterHorizontally)
        )
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = dateFormat(releaseDate),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star icon",
                modifier = Modifier.size(22.dp),
                tint = Color(parseColor("#ac8f4a")),
            )
            Spacer(modifier = Modifier.width(5.dp))
            val decimalFormat = DecimalFormat("#.#")
            val formattedVoteAverage = decimalFormat.format(voteAverage)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = formattedVoteAverage,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onFavoriteClick
            ) {
                Icon(
                    painter = favoriteIcon,
                    contentDescription = "Favorite icon",
                    tint = Color.Red
                )
            }
            IconButton(
                onClick = onWatchListClick
            ) {
                Icon(
                    painter = watchListIcon,
                    contentDescription = "Watch list icon",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            textAlign = TextAlign.Start
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(15.dp))
        Divider(
            Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
        Column(
            Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(
                text = "Trailer",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 5.dp),
                textAlign = TextAlign.Start
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                YoutubePlayer(
                    youtubeVideoId = videoId,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
            }

        }
    }
}

@Composable
fun YoutubePlayer(
    modifier: Modifier = Modifier,
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner,
) {

    AndroidView(
        modifier = modifier
            .fillMaxWidth(),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(youtubeVideoId, 0f)
                        }
                    }
                )
            }
        }
    )
}