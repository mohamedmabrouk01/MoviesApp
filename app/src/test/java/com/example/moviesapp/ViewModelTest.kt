package com.example.moviesapp

import com.example.moviesapp.data.api.MovieApi
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.data.models.MovieResponse
import com.example.moviesapp.utils.network.RequestListener
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class ViewModelTest {
    private val testDispatcher= TestCoroutineDispatcher()
    private val testScope= TestCoroutineScope()

    @Mock
    lateinit var listener:RequestListener<MovieResponse>

    @Mock
    lateinit var repository: MovieApi

    @Mock
    lateinit var mockResponse:MovieResponse

    @Before
    fun before(){
        Dispatchers.setMain(testDispatcher)
        listener= Mockito.mock(RequestListener::class.java) as RequestListener<MovieResponse>
        repository=Mockito.mock(MovieApi::class.java)
         mockResponse = Mockito.mock(MovieResponse::class.java)

        every {  }
    }

    @After
    fun after(){
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun testListPeople() = testScope.runBlockingTest {



       val resf:Deferred<MovieResponse> = Mockito.mock(Deferred::class.java) as Deferred<MovieResponse>
        whenever(repository.getAllPeople(any())).thenReturn(resf)
        verify(resf,)
//        Mockito.`when`(repository.getAllPeople(1)).thenReturn(resf)
//
//        val deferred = Mockito.doAnswer {
//
//            it.getArgument<Deferred<MovieResponse>>(0)
//        }.`when`(resf)
//
//
//        testScope.launch {
//            assert(deferred.await()!=null)
//
//        }

    }

}