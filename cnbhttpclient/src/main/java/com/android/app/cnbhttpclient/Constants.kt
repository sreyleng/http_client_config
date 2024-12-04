package com.android.app.cnbhttpclient

import com.google.gson.Gson
import java.io.Reader


object Constants {

    const val MSG_CLIENT_NETWORK = "Device no internet connection or unstable network."

    const val MSG_TIMED_OUT = "Your network connection is weak. \n Please check your signal strength and retry."

    const val MSG_SERVER_TIMED_OUT = "Could not receive a response from server."

    const val MSG_GATE_WAY = "Got invalid response from server."

    const val MSG_BAD_REQ = "The server cannot or will not process the request due to something that is perceived to be a client error."

    const val MSG_UN_AUTH = "User request has not been completed because it lacks valid authentication credentials for the requested resource."

    const val MSG_METHOD_REQUEST = "Wrong method request between client and server side"

    const val MSG_SERVER_RESTART = "Incorrect URL or Server on restarting."

    const val MSG_INTERNAL_SERVER = "We're working on fixing the problem. Be back soon"

    const val MSG_FORBIDDEN = "Access denied. You don't have permission to access this resource."

    const val MSG_UNAVAILABLE = "Sorry, the service is temporarily unavailable. We're working to restore it. Please try again later."

    const val MSG_DEFAULT = "We're working on fixing the problem. Be back soon"

    fun <T> errorResponse(json: Reader, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }
}