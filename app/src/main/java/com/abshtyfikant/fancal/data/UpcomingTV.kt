package com.abshtyfikant.fancal.data

data class UpcomingTV(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)