package com.sf.tadami.network.requests.okhttp

interface Callback<T> {
    fun onData(data: T?){

    }
    fun onError(message : String?, errorCode : Int? = null){

    }
}