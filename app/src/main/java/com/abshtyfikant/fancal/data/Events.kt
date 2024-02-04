package com.abshtyfikant.fancal.data

var eventsList = mutableListOf<Event>()

data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val type: String
)
