package com.abshtyfikant.fancal.screens

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.abshtyfikant.fancal.data.dateTomorrow
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.model.addToFollowing
import java.util.Calendar
import java.util.Date

@Composable
fun CustomEvent(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String) -> Unit) {

    val txtFieldError = remember { mutableStateOf("") }
    val titleField = remember { mutableStateOf(value) }
    //val dateField = remember { mutableStateOf(value) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        var date by remember {
            mutableStateOf(dateTomorrow)
        }
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Utwórz własne wydarzenie",
                        )
                        Icon(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = "",
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    //Title Field
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = if (txtFieldError.value.isEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onError
                                )
                                //shape = RoundedCornerShape(20)
                            ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.StarBorder,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        },
                        placeholder = { Text(text = "Tytuł wydarzenia...") },
                        value = titleField.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = {
                            titleField.value = it.take(30)
                        })
                    Spacer(modifier = Modifier.height(20.dp))

                    //Date picker
                    Box() {
                        var showDatePicker by remember {
                            mutableStateOf(false)
                        }
                        Box(contentAlignment = Alignment.Center) {
                            OutlinedButton(onClick = { showDatePicker = true },
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)) {
                                Text(text = date,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize)
                            }
                        }
                        if (showDatePicker) {
                            DateDialog(
                                onDateSelected = { date = it },
                                onDismiss = { showDatePicker = false }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    //Ready button
                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (titleField.value.isEmpty()) {
                                    txtFieldError.value = "Pole nie może być puste!"
                                    return@Button
                                }
                                var newCustomId = 0
                                for (e in eventsList){
                                    if (e.type == "custom"){
                                        newCustomId++
                                    }
                                }

                                setValue(titleField.value)
                                addToFollowing(newCustomId, titleField.value, date, "custom")
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Gotowe")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = getTomorrowInMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= getTomorrowInMillis()
            }

            // users cannot select the years from 2024
            override fun isSelectableYear(year: Int): Boolean {
                return year <= 2026
            }
        }
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: dateTomorrow

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Anuluj")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

fun getTomorrowInMillis(): Long {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    return calendar.timeInMillis
}

@SuppressLint("SimpleDateFormat")
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(millis))
}