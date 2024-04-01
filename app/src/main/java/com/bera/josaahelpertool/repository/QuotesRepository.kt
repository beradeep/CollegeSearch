package com.bera.josaahelpertool.repository

import com.bera.josaahelpertool.models.Quotes
import com.bera.josaahelpertool.models.UniversityImageResponse
import com.bera.josaahelpertool.network.QuotesApi
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val api: QuotesApi) {

    suspend fun getAllQuotes(category: String): Quotes {
        return api.getQuotes(category = category)
    }
}