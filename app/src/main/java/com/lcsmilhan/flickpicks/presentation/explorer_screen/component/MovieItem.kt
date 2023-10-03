package com.lcsmilhan.flickpicks.presentation.explorer_screen.component

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lcsmilhan.flickpicks.common.Constants
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    image: String,
    voteAverage: Double,
    releaseDate: String
) {


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        GlideImage(
            model = Constants.getPosterPath(image),
            contentDescription = "Thumb Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .shadow(elevation = 5.dp)

        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .align(CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
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
                tint = Color(Color.parseColor("#ac8f4a")),
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

fun dateFormat(dateString: String?): String {
    if (dateString.isNullOrBlank()) {
        return "Date Unavailable"
    }
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy")
    val date = try {
        LocalDate.parse(dateString, inputFormatter)
    } catch (e: DateTimeParseException) {
        return "Invalid date"
    }
    return outputFormatter.format(date)
}
