package com.android.app.cnbhttpclient.stateflow


sealed class BaseResponse<out T>(val messageTitle: String?, val hashMap: HashMap<String, String>) {
    class Loading<out T> : BaseResponse<T>("", hashMapOf())

//    class Failure<T>(msg: String = "") : BaseResponse<T>(msg, hashMapOf())
    class FailureAny<T>(msg: HashMap<String, String>) : BaseResponse<T>("", msg)
    class Success<T>(val data: T) : BaseResponse<T>("", hashMapOf())
    class Empty<out T> : BaseResponse<T>("", hashMapOf())
}
