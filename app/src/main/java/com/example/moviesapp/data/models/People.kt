package com.example.moviesapp.data.models

import com.example.moviesapp.BuildConfig

data class People(val id:Long, val name:String , val gender:Int , val known_for_department:String , val profile_path:String? ){
    val image:String?
        get() {
          return BuildConfig.BASE_IMG_URL+ profile_path
        }
}