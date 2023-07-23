package com.bera.collegesearch.network

import com.bera.collegesearch.models.Quotes
import com.bera.collegesearch.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface QuotesApi {

    @Headers("X-Api-Key: ${Constants.API_KEY_QUOTES}")
    @GET(value = "v1/quotes")
    suspend fun getQuotes(@Query("category") category: String): Quotes
}