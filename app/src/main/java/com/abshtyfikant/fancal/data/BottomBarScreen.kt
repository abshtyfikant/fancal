package com.abshtyfikant.fancal.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.FormatListBulleted
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Timeline: BottomBarScreen(
        route = "timeline",
        title = "Oś czasu",
        icon = Icons.Rounded.CalendarMonth
    )
    object Following: BottomBarScreen(
        route = "following",
        title = "Odkrywaj",
        icon = Icons.Rounded.FormatListBulleted
    )
    object Favourites: BottomBarScreen(
        route = "favourites",
        title = "Wydarzenia",
        icon = Icons.Rounded.StarBorder
    )
    object Notifications: BottomBarScreen(
        route = "notifications",
        title = "Ustawienia",
        icon = Icons.Rounded.NotificationsNone
    )
}