package com.lcsmilhan.flickpicks.presentation.screens.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lcsmilhan.flickpicks.R

@Composable
fun BottomBarItem(
    onExploreClick: () -> Unit,
    onMyWatchListClick: () -> Unit,
    onMyFavoritesClick: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }

    BottomNavigation(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp,30.dp)),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        BottomNavigationItem(
            selected = selectedItem == 0,
            onClick = {
                selectedItem = 0
                onExploreClick()
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.explore),
                    contentDescription = null,
                    tint = if (selectedItem == 0) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            label = {
                Text(
                    text = "Explore",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedItem == 0) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.primary
        )

        BottomNavigationItem(
            selected = selectedItem == 1,
            onClick = {
                selectedItem = 1
                onMyWatchListClick()
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.watch_list),
                    contentDescription = null,
                    tint = if (selectedItem == 1) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            label = {
                Text(
                    text = "Watch List",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedItem == 1) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.primary
        )

        BottomNavigationItem(
            selected = selectedItem == 2,
            onClick = {
                selectedItem = 2
                onMyFavoritesClick()
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.favorite),
                    contentDescription = null,
                    tint = if (selectedItem == 2) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            label = {
                Text(
                    text = "Favorites",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (selectedItem == 2) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            },
            selectedContentColor = MaterialTheme.colorScheme.primary,
            unselectedContentColor = MaterialTheme.colorScheme.primary
        )
    }
}