package com.bera.collegesearch.use_cases

import com.bera.collegesearch.models.Quotes
import com.bera.collegesearch.repository.QuotesRepository
import com.bera.collegesearch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuotesRepository) {

    operator fun invoke(category: String): Flow<Resource<Quotes>> =
        flow {
            try {
                emit(Resource.Loading())
                emit(Resource.Success(repository.getAllQuotes(category = category)))
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}