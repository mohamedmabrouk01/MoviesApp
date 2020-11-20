package com.example.moviesapp.data.api

import com.example.moviesapp.BuildConfig
import com.example.moviesapp.data.models.ImagesResponse
import com.example.moviesapp.data.models.MovieResponse
import com.example.moviesapp.data.models.People
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface MovieApi {

    @GET("person/popular?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun getAllPeople(@Query("page")  page:Int) : Deferred<MovieResponse>

    @GET("person/{id}?api_key=${BuildConfig.API_KEY}&language=en-US")
    fun peopleDetails(@Path("id") id:Long) : Deferred<People>

    @GET("person/{id}/images?api_key=${BuildConfig.API_KEY}")
    fun personImages(@Path("id") id:Long) : Deferred<ImagesResponse>

    @GET
    fun downloadImage(@Url url:String) : Call<ResponseBody>

}