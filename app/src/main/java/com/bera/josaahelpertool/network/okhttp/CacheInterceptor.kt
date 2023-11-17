package com.bera.josaahelpertool.network.okhttp

import okhttp3.Interceptor

class CacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val response = chain.proceed(chain.request())
        val cacheControl = okhttp3.CacheControl.Builder()
            .maxAge(10, java.util.concurrent.TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}