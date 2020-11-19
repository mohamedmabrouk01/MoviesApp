package com.example.moviesapp.data.models

import androidx.paging.PagingSource
import com.example.moviesapp.data.api.MovieApi
import com.example.moviesapp.data.api.MovieRepository
import com.example.moviesapp.utils.network.RequestCoroutines
import com.example.moviesapp.utils.network.RequestListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class PeopleDataSource(var movieRepository: MovieRepository, val listener: RequestListener<MovieResponse>) : PagingSource<Int,People>()  {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, People> {
        var values:LoadResult<Int, People>? =null
        try {
            val nextPage=params.key?:1
             movieRepository.getAllPeople(nextPage,object:RequestListener<MovieResponse>{
                 override suspend fun onResponse(data: Flow<MovieResponse>) {
                    data.collect {
                        values = LoadResult.Page(it.results,if (nextPage>1) nextPage-1 else null , nextPage+1)
                        listener.onResponse(data)
                    }
                 }

                 override fun onError(msg: String) {
                   listener.onError(msg)
                 }

                 override fun onSessionExpired(msg: String) {
                    listener.onSessionExpired(msg)
                 }

                 override fun onNetWorkError(msg: String) {
                     listener.onNetWorkError(msg)
                 }
             })
            return values!!
        }catch (e:Exception){
           return LoadResult.Error(e)
        }
    }
}