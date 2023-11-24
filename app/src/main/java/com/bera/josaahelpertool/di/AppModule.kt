package com.bera.josaahelpertool.di

import android.content.Context
import com.bera.josaahelpertool.network.CutoffApi
import com.bera.josaahelpertool.network.QuotesApi
import com.bera.josaahelpertool.network.connectivity.ConnectivityObserver
import com.bera.josaahelpertool.network.okhttp.CacheInterceptor
import com.bera.josaahelpertool.network.okhttp.ForceCacheInterceptor
import com.bera.josaahelpertool.repository.CutoffRepository
import com.bera.josaahelpertool.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCutoffRepository(api: CutoffApi) = CutoffRepository(api)

    @Singleton
    @Provides
    fun provideCutoffApi(@ApplicationContext appContext: Context, connectivityObserver: ConnectivityObserver): CutoffApi {

        val okHttpClient = OkHttpClient().newBuilder()
            .cache(Cache(File(appContext.cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
            .addNetworkInterceptor(CacheInterceptor()) // only if Cache-Control header is not enabled from the server
            .addInterceptor(ForceCacheInterceptor(connectivityObserver))
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_CUTOFF)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CutoffApi::class.java)
    }

    @Singleton
    @Provides
    fun provideQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_QUOTES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuotesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectivityObserver(@ApplicationContext appContext: Context): ConnectivityObserver =
        ConnectivityObserver(appContext)

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context = appContext
}