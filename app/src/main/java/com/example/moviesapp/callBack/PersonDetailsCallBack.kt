package com.example.moviesapp.callBack

import com.example.moviesapp.data.models.People
import com.example.moviesapp.data.models.PersonImages

interface PersonDetailsCallBack : BaseCallBack {
    fun loadPerson(data:People){}
    fun loadImages(images: ArrayList<PersonImages>){}
}