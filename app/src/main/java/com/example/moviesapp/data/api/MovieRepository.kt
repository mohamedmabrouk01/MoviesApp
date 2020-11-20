package com.example.moviesapp.data.api

import android.content.Context
import com.example.moviesapp.data.models.ImagesResponse
import com.example.moviesapp.data.models.MovieResponse
import com.example.moviesapp.data.models.People
import com.example.moviesapp.utils.network.RequestCoroutines
import com.example.moviesapp.utils.network.RequestListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MovieRepository @Inject constructor(val api: MovieApi,@ApplicationContext val context: Context) : RequestCoroutines{

    suspend fun getAllPeople(page:Int,listener: RequestListener<MovieResponse>) =  api.getAllPeople(page).handelEx(context,listener)

    suspend fun getPersonDetails(id:Long,listener: RequestListener<People>) = api.peopleDetails(id).handelEx(context,listener)

    suspend fun getPersonImages(id:Long , listener: RequestListener<ImagesResponse>) = api.personImages(id).handelEx(context,listener)

    fun downloadImage(url:String) = api.downloadImage(url)

}