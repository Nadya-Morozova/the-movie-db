package com.example.themovies.network.interceptor

import com.example.themovies.network.di.NetworkModule
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newUrl: HttpUrl = request.url().newBuilder()
            .addQueryParameter("api_key", NetworkModule.API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}