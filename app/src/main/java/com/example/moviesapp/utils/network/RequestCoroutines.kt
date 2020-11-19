package com.example.moviesapp.utils.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.R
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


/*
* Created By mabrouk on 29/08/19
*/

interface RequestCoroutines {
     suspend fun <t> Deferred<t>.handelEx(context: Context, listener: RequestListener<t>) {
            try {
                val t = await()
                if (t == null) {
                    listener.onEmpty(context.getString(R.string.no_data_found))
                } else {
                    val data = flow<t>{
                        emit(t)
                    }.flowOn(Dispatchers.IO)
                    listener.onResponse(data)
                }
            } catch (e: Throwable) {
                CheckNetwork.isConnected(context, object : OnCheckConnection {
                    override fun ConnectionTrue() {
                        when (e) {
                            is HttpException -> {
                                val error: String = analysisError(e)
                                when (e.code()) {
                                    401, 403 -> listener.onSessionExpired(error)
                                    else -> listener.onError(error)
                                }
                            }
                            is SocketTimeoutException -> listener.onError(context.getString(R.string.socketTimeout))
                            is JsonSyntaxException -> listener.onError(e.message.toString())
                            is SSLHandshakeException -> listener.onError(e.message.toString())
                            else -> listener.onError("Api Error")
                        }
                    }

                    override fun ConnectionError() {
                        listener.onNetWorkError(context.getString(R.string.no_internet_connection))
                     }

                })
            }

    }

    private fun analysisError(e: HttpException): String {
        return try {
            val responseStrings: String = e.response()!!.errorBody().toString()
            val jsonObject = JSONObject(responseStrings)
            when {
                jsonObject.has("msg") -> jsonObject.get("msg").toString()
                jsonObject.has("error") -> jsonObject.get("error").toString()
                else -> e.message()
            }
        } catch (ex: Exception) {
            if (e.message().isEmpty()) e.localizedMessage else e.message()
        }
    }
}
