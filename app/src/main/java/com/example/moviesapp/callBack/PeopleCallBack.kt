package com.example.moviesapp.callBack

import androidx.paging.PagingData
import com.example.moviesapp.data.models.People

interface PeopleCallBack : BaseCallBack {
    fun loadPeople(data:PagingData<People>)
}