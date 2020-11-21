package com.example.moviesapp.viewModels

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapp.callBack.PeopleCallBack
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.data.models.MovieResponse
import com.example.moviesapp.data.models.People
import com.example.moviesapp.data.models.PeopleDataSource
import com.example.moviesapp.utils.network.RequestListener
import com.example.moviesapp.viewModels.base.BaseViewModel
import com.mabrouk.loaderlib.RetryCallBack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PeopleViewModel<v : PeopleCallBack> @ViewModelInject constructor(application: Application, val repository: MovieRepository) : BaseViewModel<v>(application){
    var loader:ObservableBoolean = ObservableBoolean()
    var error:ObservableField<String> = ObservableField()
    lateinit var data:PagingData<People>
    var retryCallBack:RetryCallBack = object : RetryCallBack{
        override fun onRetry() {
           loadData()
        }
    }

    fun loadData(){
        loader.set(true)
        error.set(null)
        val flow =
            Pager(PagingConfig(pageSize = 20,enablePlaceholders = true)){
                 PeopleDataSource(repository,listener)
        }.flow.cachedIn(viewModelScope)

        viewModelScope.launch {
            flow.collect {
                data=it
               view.loadPeople(data)
            }
        }

    }

    val listener = object : RequestListener<MovieResponse>{
        override suspend fun onResponse(data: Flow<MovieResponse>) {
            loader.set(false)
        }

        override fun onError(msg: String) {
           loader.set(false)
            error.set(msg)
        }

        override fun onSessionExpired(msg: String) {
            loader.set(false)
            error.set(msg)
        }

        override fun onNetWorkError(msg: String) {
            loader.set(false)
            error.set(msg)
        }

    }

}