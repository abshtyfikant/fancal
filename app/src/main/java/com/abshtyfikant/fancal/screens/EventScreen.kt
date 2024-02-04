package com.abshtyfikant.fancal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.model.deleteFromFollowing
import com.abshtyfikant.fancal.ui.theme.FancalTheme

//@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen() {
    FancalTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FavouriteSection()
        }
    }
}

@Composable
fun FavouriteSection() {
    val showDialog =  remember { mutableStateOf(false) }
    val movieIcon = Icons.Rounded.Movie
    val tvIcon = Icons.Rounded.LiveTv
    val customEventIcon = Icons.Rounded.Event

    if(showDialog.value)
        CustomEvent(value = "", setShowDialog = {
            showDialog.value = it }) {}
    Scaffold(
        floatingActionButton = { AddFollowingFAB { showDialog.value = true } },
        floatingActionButtonPosition = FabPosition.End
    ) { padding -> LazyColumn(
            modifier = Modifier.fillMaxSize().padding(10.dp).padding(top = 80.dp)
        ) {
            items(items = eventsList, key = { it.id }) { task ->
                ListItem(headlineContent = { Text(text = task.title) },
                    leadingContent = {
                        Box(contentAlignment = Alignment.Center) {
                            DaysCircle(eventsList.indexOf(task) == 0,
                                eventsList.indexOf(task) == eventsList.size-1)
                            Icon(when (task.type) {
                                    "movie" -> {movieIcon}
                                    "tv" -> {tvIcon}
                                    "custom" -> {customEventIcon}
                                    else -> {Icons.Rounded.Settings}
                                },
                                contentDescription = task.type,
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)) } },
                    trailingContent = {
                        IconButton(onClick = { deleteFromFollowing(task.id) }) {
                            Icon(Icons.Rounded.DeleteOutline, contentDescription = "Usuń")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AddFollowingFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = Modifier.padding(bottom = 80.dp)
            .padding(horizontal = 15.dp),
        //modifier = Modifier.size(.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
        //shape = CircleShape
    ) {
        Icon(Icons.Filled.Add, "Utwórz własne wydarzenie.")
    }
}

@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreenPreview() {
    FavouritesScreen()
}