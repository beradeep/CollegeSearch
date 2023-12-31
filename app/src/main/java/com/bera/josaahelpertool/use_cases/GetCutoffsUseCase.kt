package com.bera.josaahelpertool.use_cases

import com.bera.josaahelpertool.models.CutoffItem
import com.bera.josaahelpertool.repository.CutoffRepository
import com.bera.josaahelpertool.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCutoffsUseCase @Inject constructor(
    private val repository: CutoffRepository
){
    operator fun invoke(): Flow<Resource<ArrayList<CutoffItem>>> = flow {
        try {
            emit(Resource.Loading())
            val cutoffs = repository.getAllCutoffs()
            emit(Resource.Success(cutoffs))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?: "Unexpected error"))
        }
    }
}