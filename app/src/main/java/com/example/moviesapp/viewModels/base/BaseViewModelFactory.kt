package com.example.moviesapp.viewModels.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.callBack.PeopleCallBack
import com.example.moviesapp.callBack.PersonDetailsCallBack
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.viewModels.PeopleViewModel
import com.example.moviesapp.viewModels.PersonViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

class BaseViewModelFactory(val application: Application, @ApplicationContext val context: Context, val repository: MovieRepository ) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            return PeopleViewModel<PeopleCallBack>(application,repository) as T
        }else if (modelClass.isAssignableFrom(PersonViewModel::class.java)){
            return PersonViewModel<PersonDetailsCallBack>(repository,context,application) as T
        }
        throw Exception("this View Model not found ")
    }
}