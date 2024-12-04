package com.android.app.cnbhttpclient.stateflow

import android.util.Log
import com.android.app.cnbhttpclient.Constants
import retrofit2.HttpException
import retrofit2.Response
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.Timestamp


/**Handle Error in HttpException and SocketTimeoutException*/
fun handleHttpErrorResponse(e: Throwable): String {
    return when (e) {
        is HttpException -> parseHTTPErrorResponse(e.response()!!)

        is SocketTimeoutException -> e.message!!

        is UnknownHostException -> Constants.MSG_CLIENT_NETWORK

        else -> e.message!!
    }
}

/**Developer can custom message response for easy understand based code error from httpException*/
fun parseHTTPErrorResponse(responseBody: Response<*>): String {
    val messageError = when (responseBody.code()) {
        400 -> Constants.MSG_BAD_REQ
//        401 -> Constants.MSG_UN_AUTH
        404 -> Constants.MSG_SERVER_RESTART
        405 -> Constants.MSG_METHOD_REQUEST
        500 -> Constants.MSG_INTERNAL_SERVER
        502 -> Constants.MSG_GATE_WAY
        408 -> Constants.MSG_TIMED_OUT // Client failed connect to server
        504 -> Constants.MSG_SERVER_TIMED_OUT // Sever to server
        else -> ""
    }

    val errorBody = hashMapOf(
        "timeStamp" to Timestamp(System.currentTimeMillis()),
        "code" to responseBody.code(),
        "message" to messageError,
//        "path" to responseBody.raw().request().url()
        "path" to responseBody.raw().request.url
    )

    Log.d("ParseHTTP", "errorBody: $errorBody")

    return messageError
}

private val hashMap = HashMap<String, String>()

fun handleTwoHttpErrorResponse(e: Throwable): HashMap<String, String>? {
    Log.d("handleTwoHttpErrorResponse:", "$e")
    when (e) {
        is HttpException -> parseTwoHTTPErrorResponse(e.response()!!)

//        is SocketTimeoutException -> hashMap["Timed-out"] = e.message!!
        is SocketTimeoutException -> hashMap["Exception HTTP408"] = Constants.MSG_TIMED_OUT

        is UnknownHostException -> hashMap["Network connection issue"] =
            Constants.MSG_CLIENT_NETWORK

        is InterruptedIOException -> hashMap["Exception HTTP408"] = Constants.MSG_TIMED_OUT

        else -> hashMap["Exception Error"] = e.message!!
    }

    return hashMap
}

fun parseTwoHTTPErrorResponse(responseBody: Response<*>): HashMap<String, String> {
    println("parseTwoHTTPErrorResponse: ${responseBody.code()}")

    when (responseBody.code()) {
        400 -> hashMap["Exception HTTP400"] = Constants.MSG_BAD_REQ
        401 -> hashMap["Exception HTTP401"] = Constants.MSG_UN_AUTH
        403 -> hashMap["Exception HTTP403"] = Constants.MSG_FORBIDDEN
        404 -> hashMap["Exception HTTP404"] = Constants.MSG_SERVER_RESTART
        405 -> hashMap["Exception HTTP45"] = Constants.MSG_METHOD_REQUEST
        500 -> hashMap["Exception HTTP500"] = Constants.MSG_INTERNAL_SERVER
        502 -> hashMap["Exception HTTP502"] = Constants.MSG_GATE_WAY
        503 -> hashMap["Exception HTTP503"] = Constants.MSG_UNAVAILABLE
        408 -> hashMap["Exception HTTP408"] = Constants.MSG_TIMED_OUT // Client failed connect to server
        504 -> hashMap["Exception HTTP504"] = Constants.MSG_SERVER_TIMED_OUT // Sever to server
        else -> hashMap["Exception HTTP${responseBody.code()}"] = Constants.MSG_DEFAULT
    }

//    val errorBody = hashMapOf(
//        "timeStamp" to Timestamp(System.currentTimeMillis()),
//        "code" to responseBody.code(),
//        "message" to messageError,
//        "path" to responseBody.raw().request().url()
//    )
//
//    Log.d("ParseHTTP", "errorBody: $errorBody")


    return hashMap
}