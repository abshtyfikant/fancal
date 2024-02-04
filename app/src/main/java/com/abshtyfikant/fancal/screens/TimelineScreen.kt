package com.abshtyfikant.fancal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abshtyfikant.fancal.Graph
import com.abshtyfikant.fancal.data.dateCurrent
import com.abshtyfikant.fancal.services.NotificationService
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.ui.theme.FancalTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

//@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen() {
    FancalTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            color = MaterialTheme.colorScheme.background
        ) {

            EventCard()
        }
    }
}

@Composable
fun EventCard() {
    eventsList.sortBy {it.date}

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
    ) {
        items(items = eventsList, key = { it.id }) { task ->
            ListItem(
                headlineContent = { Text(text = task.title, lineHeight = 18.sp) },
                supportingContent = { Text(text = task.date) },
                leadingContent = {
                    Cover(task.title[0])
                },
                trailingContent = {
                    val daysPeriod = ChronoUnit.DAYS.between(dateCurrent, LocalDate.parse(task.date))
                    val daysText = daysPeriod.toString()
                    fireNotification(notificationCheckedStates, task.title, daysPeriod.toInt())
                    Box(modifier = Modifier
                        .size(60.dp),
                        contentAlignment = Alignment.Center
                    ){
                        DaysCircle(eventsList.indexOf(task) == 0, eventsList.indexOf(task) == eventsList.size-1)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                //modifier = Modifier.align(Alignment.Center)
                                text = daysText,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.background,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = if(daysPeriod > 1) {"dni"} else {"dzień"},
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.background,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Composable
fun Cover(firstLetter: Char) {
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(80.dp), onDraw = {
            drawRoundRect(
                color = DarkGray,
                cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
            )
        })
        Text(text = firstLetter.toString(),
            fontSize = MaterialTheme.typography.titleLarge.fontSize)
    }
}

@Composable
fun DaysCircle(start: Boolean, end:Boolean) {
    var startDraw: Offset
    var endDraw: Offset
    Canvas(modifier = Modifier.size(60.dp), onDraw = {
        if (start) {
            startDraw = Offset(size.width / 2, size.height / 2)
            endDraw = Offset(size.width / 2, 3 * size.height)
        }
        else if (end) {
            startDraw = Offset(size.width / 2, -3 * size.height)
            endDraw = Offset(size.width / 2, size.height / 2)
        }
        else{
            startDraw = Offset(size.width / 2, -3 * size.height)
            endDraw = Offset(size.width / 2, 3 * size.height)
        }

        val canvasCenter = Offset(size.width / 2, size.height / 2)
        drawLine(
            color = LightGray,
            start = startDraw,
            end = endDraw,
            strokeWidth = 5.dp.toPx()
        )
        drawCircle(color = LightGray)
    })
}

fun fireNotification(array: Array<Boolean>, title: String, numberOfDays: Int){
    if(array.size < 3)
        return

    if (array[0] && numberOfDays == 1) {
        NotificationService(Graph.appContext).showNotification(title, numberOfDays)
    }
    if (array[1] && numberOfDays == 3) {
        NotificationService(Graph.appContext).showNotification(title, numberOfDays)
    }
    if (array[2] && numberOfDays == 7) {
        NotificationService(Graph.appContext).showNotification(title, numberOfDays)
    }
}
