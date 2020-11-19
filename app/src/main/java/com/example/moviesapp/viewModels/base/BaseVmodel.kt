package com.example.moviesapp.viewModels.base

import com.example.moviesapp.callBack.BaseCallBack

interface BaseVmodel<V : BaseCallBack> {
    fun attachView(view:V)
}