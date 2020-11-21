package com.example.moviesapp.utils.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

interface RequestListener<t> {
    suspend fun onResponse(data: Flow<t>)
    fun onEmpty(msg: String){}
    fun onError(msg: String)
    fun onSessionExpired(msg: String){}
    fun onNetWorkError(msg: String){}
}
