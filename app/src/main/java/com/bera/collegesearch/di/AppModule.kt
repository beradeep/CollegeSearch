package com.bera.collegesearch.di

import com.bera.collegesearch.network.CutoffApi
import com.bera.collegesearch.network.QuotesApi
import com.bera.collegesearch.repository.CutoffRepository
import com.bera.collegesearch.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCutoffRepository(api: CutoffApi) = CutoffRepository(api)

    @Singleton
    @Provides
    fun provideCutoffApi(): CutoffApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_CUTOFF)
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
}