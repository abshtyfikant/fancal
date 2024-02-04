package com.abshtyfikant.fancal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abshtyfikant.fancal.data.moviesList
import com.abshtyfikant.fancal.data.tvList
import com.abshtyfikant.fancal.model.addToFollowing
import com.abshtyfikant.fancal.model.deleteFromFollowing
import com.abshtyfikant.fancal.model.refreshList
import com.abshtyfikant.fancal.model.refreshListTV
import com.abshtyfikant.fancal.services.*
import com.abshtyfikant.fancal.ui.theme.FancalTheme
import java.math.BigDecimal
import java.math.RoundingMode

//@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowingScreen() {
    refreshList(); refreshListTV()
    FancalTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FollowingSection()
        }
    }
}

@Composable
fun FollowingSection(){
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Filmy", "TV")

    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 64.dp)) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Rounded.Movie, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Rounded.LiveTv, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> MoviesTab()
            1 -> TvTab()
        }
    }
}

@Composable
fun MoviesTab(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
            //.padding(top = 80.dp)
            //.padding(bottom = 80.dp)
    ) {
        items(items = moviesList, key = { it.id }) { task ->
            ListItem(
                headlineContent = { Text(text = task.title, lineHeight = 16.sp) },
                supportingContent = { Text(text = task.date) },
                leadingContent = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically){
                        val checkedState = remember { mutableStateOf(task.isFollowing) }
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                if(checkedState.value) {
                                    addToFollowing(task.id, task.title, task.date, "movie")
                                    task.isFollowing = true
                                }
                                else {
                                    deleteFromFollowing(task.id)
                                    task.isFollowing = false
                                }},
                            colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.size(15.dp))

                        Box(contentAlignment = Alignment.Center) {
                            DiscoverCircle()
                            val ratingDec = task.avgRating.toBigDecimal().setScale(1, RoundingMode.UP)
                            val ratingString: String = if (ratingDec > BigDecimal(0)) {ratingDec.toString()}
                            else
                                "-"
                            Text(text = ratingString,
                                color = MaterialTheme.colorScheme.background)
                            //Cover()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun TvTab(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
            //.padding(top = 80.dp)
    ) {
        items(items = tvList, key = { it.id }) { task ->
            ListItem(
                headlineContent = { Text(text = task.title, lineHeight = 16.sp) },
                supportingContent = { Text(text = task.date) },
                leadingContent = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically){
                        val checkedState = remember { mutableStateOf(task.isFollowing) }
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                if(checkedState.value) {
                                    addToFollowing(task.id, task.title, task.date, "tv")
                                    task.isFollowing = true
                                }
                                else {
                                    deleteFromFollowing(task.id)
                                    task.isFollowing = false
                                }},
                            colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.size(15.dp))

                        Box(contentAlignment = Alignment.Center) {
                            DiscoverCircle()
                            val ratingDec = task.avgRating.toBigDecimal().setScale(1, RoundingMode.UP)
                            val ratingString: String = if (ratingDec > BigDecimal(0))
                                ratingDec.toString()
                            else
                                "-"
                            Text(text = ratingString,
                                color = MaterialTheme.colorScheme.background)
                            //Cover()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DiscoverCircle(){
    Canvas(modifier = Modifier.size(60.dp), onDraw = {
        drawCircle(color = Color.LightGray)
    })
}
