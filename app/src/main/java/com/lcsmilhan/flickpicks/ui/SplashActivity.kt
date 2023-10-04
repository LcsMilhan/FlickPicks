package com.lcsmilhan.flickpicks.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.*
import com.lcsmilhan.flickpicks.ui.theme.FlickPicksTheme
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickPicksTheme {
                SplashScreen()
            }
        }
    }
    @Composable
    private fun SplashScreen() {
        LaunchedEffect(true) {
            delay(2000)
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mainIntent)
            finish()
        }
    }
}

