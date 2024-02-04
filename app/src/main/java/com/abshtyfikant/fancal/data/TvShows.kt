package com.abshtyfikant.fancal.data

//TV data class
data class TvShow(
    val id: Int,
    val title: String,
    val date: String,
    val avgRating: Double,
    var isFollowing: Boolean
)

var tvList = mutableListOf<TvShow>()