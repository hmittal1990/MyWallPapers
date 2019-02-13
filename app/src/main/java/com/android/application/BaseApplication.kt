package com.android.application

import android.app.Application
import android.util.Log
import com.android.mywallpapers.BuildConfig
import com.android.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.android.network.IRetrofit


class BaseApplication : Application() {

    companion object {

        @Volatile
        private var IRETROFIT: IRetrofit? = null

        @Synchronized
        fun getRetrofitAPI(authorization: String? = null): IRetrofit? {

            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()
            val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            if (BuildConfig.DEBUG) {
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }

            IRETROFIT = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getHeader(authorization))
                .build()
                .create(IRetrofit::class.java)

            return IRETROFIT
        }

        private fun getHeader(authorizationValue: String?): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

            if (authorizationValue != null) {
                httpClient.addNetworkInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", authorizationValue)
                    val request = requestBuilder.build()
                    chain.proceed(request!!)
                }
            }
            return httpClient.build()
        }
    }
}
