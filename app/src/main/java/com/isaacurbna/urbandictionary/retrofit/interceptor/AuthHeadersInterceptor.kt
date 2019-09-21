package com.isaacurbna.urbandictionary.retrofit.interceptor

import com.isaacurbna.urbandictionary.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeadersInterceptor : Interceptor {

    companion object {
        const val HOST_HEADER = "X-RapidAPI-Host"
        const val KEY_HEADER = "X-RapidAPI-Key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header(HOST_HEADER, BuildConfig.API_HOST)
            .header(KEY_HEADER, BuildConfig.API_KEY)
            .method(originalRequest.method(), originalRequest.body())
            .build()

        return chain.proceed(newRequest)
    }

}