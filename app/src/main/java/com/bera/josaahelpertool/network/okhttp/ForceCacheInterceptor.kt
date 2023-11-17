package com.bera.josaahelpertool.network.okhttp

import com.bera.josaahelpertool.network.connectivity.ConnectivityObserver
import com.bera.josaahelpertool.network.connectivity.ConnectivityStatus
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class ForceCacheInterceptor @Inject constructor(private val connectivityObserver: ConnectivityObserver) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (connectivityObserver.status() == ConnectivityStatus.Unavailable) {
            builder.cacheControl(CacheControl.FORCE_CACHE)
        }
        return chain.proceed(builder.build())
    }
}