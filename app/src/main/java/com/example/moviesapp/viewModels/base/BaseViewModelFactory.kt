package com.example.moviesapp.viewModels.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.callBack.PeopleCallBack
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.viewModels.PeopleViewModel

class BaseViewModelFactory(val application: Application,val repository: MovieRepository ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            return PeopleViewModel<PeopleCallBack>(application,repository) as T
        }
        throw Exception("this View Model not found ")
    }
}