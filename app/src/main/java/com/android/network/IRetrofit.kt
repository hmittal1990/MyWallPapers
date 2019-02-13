package com.android.network

import com.android.modal.ResponseWallPaper
import retrofit2.Call
import retrofit2.http.*

interface IRetrofit {

    @GET("/v1/curated?")
    fun getWallpapers(@Query("per_page") perPage: Int?, @Query("page") pageNumber: Int?): Call<ResponseWallPaper>

}