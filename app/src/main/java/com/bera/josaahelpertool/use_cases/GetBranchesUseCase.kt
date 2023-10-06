package com.bera.josaahelpertool.use_cases

import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.models.CutoffItem
import com.bera.josaahelpertool.repository.CutoffRepository
import com.bera.josaahelpertool.utils.Resource
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