package com.bera.collegesearch.use_cases

import com.bera.collegesearch.models.Cutoff
import com.bera.collegesearch.models.CutoffItem
import com.bera.collegesearch.repository.CutoffRepository
import com.bera.collegesearch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBranchesUseCase @Inject constructor(
    private val repository: CutoffRepository
){
    operator fun invoke(college: String): Flow<Resource<ArrayList<CutoffItem>>> = flow {
        try {
            emit(Resource.Loading())
            val cutoffs = repository.getAllCutoffs()
            val filteredCutoffs = Cutoff()
            cutoffs.forEach {
                if (it.Institute == college) {
                    filteredCutoffs.add(it)
                }
            }
            emit(Resource.Success(filteredCutoffs))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage?: "Unexpected error"))
        }
    }
}