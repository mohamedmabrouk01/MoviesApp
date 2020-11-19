package com.example.moviesapp.di

import android.app.Application
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.viewModels.base.BaseViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getViewModelFactory(app:Application,repository: MovieRepository) : BaseViewModelFactory =
        BaseViewModelFactory(app,repository)
}