package com.abshtyfikant.fancal.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abshtyfikant.fancal.ui.theme.FancalTheme

//@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen() {
    FancalTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(30.dp)
                    .padding(top = 80.dp)
                    .padding(bottom = 80.dp)
            ) {
                val rowIterator = arrayOf("1 dzień", "3 dni", "7 dni")
                for (numberOfDays in rowIterator) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val checkedState = remember {
                            mutableStateOf(notificationCheckedStates[rowIterator.indexOf(numberOfDays)]) }
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                notificationCheckedStates[rowIterator.indexOf(numberOfDays)] =
                                    checkedState.value
                            },
                            colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            text = "Powiadomienie $numberOfDays przed premierą.",
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(15.dp))
                }
            }
        }
    }
}

//Array of booleans representing number of days before a premiere: 1, 3, 7
var notificationCheckedStates = arrayOf(false, false, false)


@Preview(widthDp = 360, heightDp = 800)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreenPreview(){
    NotificationsScreen()
}