package com.lcsmilhan.flickpicks.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.lcsmilhan.flickpicks.presentation.navigation.FlickPicksNav
import com.lcsmilhan.flickpicks.ui.theme.FlickPicksTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalGlideComposeApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickPicksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FlickPickApp()
                }
            }
        }
    }
}

@Composable
fun FlickPickApp() {
    FlickPicksNav()
}

//val video = Video(
//    id = "",
//    key = "cg5z7wgOUig",
//    name = "test"
//)
//val movies = Movie(
//    genreIds = listOf(28,878,12),
//    overview = "Recent college grad Jaime Reyes",
//    video = false,
//    title = "",
//    thumbImage = "/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
//    backgroundImage = "/H6j5smdpRqP9a8UnhWp6zfl0SC.jpg",
//    id = 565770,
//    voteAverage = 7.1,
//    releaseDate = "2023-08-23"
//)
//Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
////                        GlideImage(model = Constants.getPosterPath(movies.thumbImage), contentDescription = "")
//    YoutubePlayer(youtubeVideoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
//}
//
//@Composable
//fun YoutubePlayer(
//    youtubeVideoId: String,
//    lifecycleOwner: LifecycleOwner
//) {
//
//    AndroidView(
//        modifier = Modifier.fillMaxWidth(),
//        factory = { context ->
//            YouTubePlayerView(context).apply {
//                lifecycleOwner.lifecycle.addObserver(this)
//
//                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//                    override fun onReady(youTubePlayer: YouTubePlayer) {
//                        youTubePlayer.cueVideo(youtubeVideoId, 0f)
//                    }
//                })
//            }
//        }
//    )
//
//}
