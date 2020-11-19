package com.example.moviesapp.utils.network

import android.annotation.SuppressLint
import android.net.NetworkInfo
import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log


/*
* Created By mabrouk on 16/03/19
* KotilnApp
*/

class CheckNetwork {
    companion object {

        fun isConnected(context: Context, OnCheckConnection: OnCheckConnection) {
            if (isOnline(context)) {
                OnCheckConnection.ConnectionTrue()
            } else {
                OnCheckConnection.ConnectionError()
            }
        }

        @SuppressLint("NewApi")
        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }
    }
}
