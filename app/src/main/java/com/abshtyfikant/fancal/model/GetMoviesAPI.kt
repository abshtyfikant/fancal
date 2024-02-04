package com.abshtyfikant.fancal.model

import com.abshtyfikant.fancal.data.UpcomingMovie
import com.abshtyfikant.fancal.data.dateLater
import com.abshtyfikant.fancal.data.dateTomorrow
import com.abshtyfikant.fancal.data.eventsList
import com.abshtyfikant.fancal.data.Movie
import com.abshtyfikant.fancal.data.moviesList
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun getUpcoming() {

    //TWITCH CLIENT ID
    //b6rq5qxglawqzdy7atb49nf56pjaie
    val BASE_URL = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&primary_release_date.gte=" +
            dateTomorrow + "&primary_release_date.lte=" + dateLater + "&sort_by=popularity.desc&with_original_language=en&with_release_type=1"
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
    val moviesData = gson.fromJson(responseBody, UpcomingMovie::class.java)

    for (movie in moviesData.results){
        moviesList.add(Movie(movie.id, movie.title, movie.release_date, movie.popularity, false))
    }
    if(eventsList.isNotEmpty()) {
        for (e in eventsList) {
            for(m in moviesList){
                if (e.id == m.id)
                    m.isFollowing = true
            }
        }
    }
}

fun refreshList(){
    moviesList.clear()
    getUpcoming()
}