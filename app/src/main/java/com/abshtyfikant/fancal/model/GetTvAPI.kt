package com.abshtyfikant.fancal.model

import com.abshtyfikant.fancal.data.UpcomingTV
import com.abshtyfikant.fancal.data.dateLater
import com.abshtyfikant.fancal.data.dateTomorrow
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.data.TvShow
import com.abshtyfikant.fancal.data.tvList
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun getUpcomingTV() {
    val BASE_URL = "https://api.themoviedb.org/3/discover/tv?first_air_date.gte=" +
            dateTomorrow + "&first_air_date.lte=" + dateLater +
            "&include_adult=false&include_null_first_air_dates=false&language=en-US&page=1&sort_by=popularity.desc&with_original_language=en"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(BASE_URL)
        .get()
        .addHeader("accept", "application/json")
        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZGY1NWY1NjZhOGFkZDgzYThjYTI3ZmMyNDZkY2UyZSIsInN1YiI6IjY1ODE1NTFhOGRiYzMzMDg3NDlhNDE1MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.pEOw1xWWDuzM0tMPH-nNz-FNlixEUsi7DVgl0hZJ8tk")
        .build()

    val response = client.newCall(request).execute()
    val responseBody = response.body!!.string()

    val gson = Gson()
    val tvData = gson.fromJson(responseBody, UpcomingTV::class.java)

    for (show in tvData.results){
        tvList.add(TvShow(show.id, show.name, show.first_air_date, show.popularity, false))
    }
    if(eventsList.isNotEmpty()) {
        for (e in eventsList) {
            for(t in tvList){
                if (e.id == t.id)
                    t.isFollowing = true
            }
        }
    }
}

fun refreshListTV(){
    tvList.clear()
    getUpcomingTV()
}