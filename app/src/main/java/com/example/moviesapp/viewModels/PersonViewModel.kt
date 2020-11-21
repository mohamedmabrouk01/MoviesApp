package com.example.moviesapp.viewModels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.callBack.PersonDetailsCallBack
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.data.models.ImagesResponse
import com.example.moviesapp.data.models.People
import com.example.moviesapp.data.models.PersonImages
import com.example.moviesapp.utils.FileUtils
import com.example.moviesapp.utils.network.RequestListener
import com.example.moviesapp.viewModels.base.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class PersonViewModel<v : PersonDetailsCallBack> @ViewModelInject constructor(
    val repository: MovieRepository,
    @ApplicationContext val context: Context,
    application: Application
) : BaseViewModel<v>(application) {
    val loader:ObservableBoolean = ObservableBoolean()
    lateinit var image:PersonImages
      var personId:Long=0
    fun loadPersonDetails(id: Long){
       personId=id
        loader.set(true)
        viewModelScope.launch {
            repository.getPersonDetails(personId, object : RequestListener<People> {
                override suspend fun onResponse(data: Flow<People>) {
                    data.collect {
                        view.loadPerson(it)
                        loader.set(false)
                    }
                }

                override fun onError(msg: String) {
                    loader.set(false)
                }

                override fun onSessionExpired(msg: String) {
                    loader.set(false)
                }

                override fun onNetWorkError(msg: String) {
                    loader.set(false)
                }

            })
        }
    }

    fun loadImages(){
      viewModelScope.launch {
          repository.getPersonImages(personId, object : RequestListener<ImagesResponse> {
              override suspend fun onResponse(data: Flow<ImagesResponse>) {
                  data.collect {
                      view.loadImages(it.profiles)
                  }
              }

              override fun onError(msg: String) {

              }

              override fun onSessionExpired(msg: String) {

              }

              override fun onNetWorkError(msg: String) {

              }

          })
      }
    }

    fun downloadImage(){
        viewModelScope.launch(Dispatchers.IO){
            repository.downloadImage("${BuildConfig.BASE_IMG_URL}${image.file_path}")
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {

                        val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
                        bitmap?.let {
                            Toast.makeText(context,if (FileUtils.saveImage(it)) "Saved" else "Fail" , Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }

                })
        }
    }




}