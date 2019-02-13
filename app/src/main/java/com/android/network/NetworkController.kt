package com.android.network

import com.android.application.BaseApplication
import com.android.modal.ResponseWallPaper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkController(val token: String?) {

    fun getWallpapers(perPage: Int?, pageNumber: Int?, listener: (ResponseWallPaper?, String?) -> Unit) {
        BaseApplication.getRetrofitAPI(token)?.getWallpapers(perPage, pageNumber)
            ?.enqueue(object : Callback<ResponseWallPaper> {
                override fun onResponse(call: Call<ResponseWallPaper>, response: Response<ResponseWallPaper>) {
                    if (response.body() != null) {
                        listener(response.body()!!, null)
                    } else {
                        listener(null, response.message())
                    }
                }

                override fun onFailure(call: Call<ResponseWallPaper>, t: Throwable) {
                    listener(null, t.message)
                }
            })
    }
}