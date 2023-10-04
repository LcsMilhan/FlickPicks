package com.lcsmilhan.flickpicks.presentation.screens.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lcsmilhan.flickpicks.R
import com.lcsmilhan.flickpicks.presentation.navigation.Screen

@Composable
fun BottomBarItem(
    navController: NavHostController
) {

    val screens = listOf(
        BottomBarItems.Explore,
        BottomBarItems.WatchList,
        BottomBarItems.Favorites
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp,30.dp)),
        backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val selectedItem = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    val painter = painterResource(id = screen.iconRes)
    BottomNavigationItem(
        selected = selectedItem,
        label = {
            Text(
                text = screen.title,
                style = MaterialTheme.typography.labelLarge,
                color = if (selectedItem) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.primary.copy(0.5f)
            )
        },
        onClick = {
            navController.navigate(screen.route)
        },
        icon = {
            Icon(
                painter = painter,
                contentDescription = "Nav icon",
                tint = if (selectedItem) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.primary.copy(0.5f)
            )
        },
        unselectedContentColor = MaterialTheme.colorScheme.primary,
        selectedContentColor = MaterialTheme.colorScheme.primary

    )
}

sealed class BottomBarItems(
    val route: String,
    val title: String,
    @DrawableRes val iconRes: Int
) {
    object Explore : BottomBarItems(
        route = Screen.ExplorerScreen.route,
        title = "Explore",
        iconRes = R.drawable.explore
    )
    object WatchList : BottomBarItems(
        route = Screen.WatchListScreen.route,
        title = "Watch List",
        iconRes = R.drawable.watch_list
    )
    object Favorites : BottomBarItems(
        route = Screen.FavoritesScreen.route,
        title = "Favorites",
        iconRes = R.drawable.favorite
    )
}