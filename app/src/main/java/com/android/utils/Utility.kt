package com.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Utility {

    fun isInternetConnectionAvailable(mContext: Context?): Boolean {
        if (null == mContext) {
            return true
        }
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo: NetworkInfo?
        netInfo = connectivityManager.activeNetworkInfo
        return null != netInfo && netInfo.isAvailable && netInfo.isConnected
    }
}