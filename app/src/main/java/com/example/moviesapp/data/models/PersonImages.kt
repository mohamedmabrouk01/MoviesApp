package com.example.moviesapp.data.models

import android.os.Parcelable
import com.example.moviesapp.BuildConfig
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonImages(val file_path:String , val aspect_ratio:Float , val height:Int , val width:Int,
val vote_average:Float , val vote_count:Int) : Parcelable {
    val image:String
    get() = BuildConfig.BASE_IMG_URL+file_path
}