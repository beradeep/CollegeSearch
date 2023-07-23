package com.bera.collegesearch.repository

import com.bera.collegesearch.models.Quotes
import com.bera.collegesearch.network.QuotesApi
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val api: QuotesApi) {

    suspend fun getAllQuotes(category: String): Quotes {
        return api.getQuotes(category = category)
    }
}