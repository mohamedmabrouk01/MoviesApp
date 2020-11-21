package com.example.moviesapp

import android.app.Application
import android.content.Context
import com.example.moviesapp.callBack.BaseCallBack
import com.example.moviesapp.callBack.PeopleCallBack
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.data.models.MovieResponse
import com.example.moviesapp.utils.network.RequestListener
import com.example.moviesapp.viewModels.PeopleViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PeopleViewModelTest {
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()
    @RelaxedMockK
    lateinit var application: Application

    @RelaxedMockK
    lateinit var repository: MovieRepository

    @RelaxedMockK
    lateinit var listener: RequestListener<MovieResponse>

    @RelaxedMockK
    lateinit var view:PeopleCallBack

    lateinit var viewModel:PeopleViewModel<PeopleCallBack>

    @Before
    fun before(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel= PeopleViewModel(application,repository)
        viewModel.attachView(view)
    }

    @After
    fun after(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun test(){
        coEvery {repository.getAllPeople(any(),listener)}.throws(Exception("trtrtrt"))

    }

}