package com.example.moviesapp.data.api

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.models.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("person/popular?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getAllPeople(@Query("page")  page:Int) : Deferred<MovieResponse>
}