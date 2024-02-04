package com.abshtyfikant.fancal.data

//Movie data class
data class Movie(
    val id: Int,
    val title: String,
    val date: String,
    val avgRating: Double,
    var isFollowing: Boolean
)

var moviesList = mutableListOf<Movie>()