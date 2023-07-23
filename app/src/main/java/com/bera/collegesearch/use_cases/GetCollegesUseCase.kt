package com.bera.collegesearch.use_cases

import com.bera.collegesearch.models.Cutoff
import com.bera.collegesearch.models.CutoffItem
import com.bera.collegesearch.repository.CutoffRepository
import com.bera.collegesearch.utils.Constants.IIIT_STRING
import com.bera.collegesearch.utils.Constants.IIT_STRING
import com.bera.collegesearch.utils.Constants.IIT_STRING_1
import com.bera.collegesearch.utils.Constants.NIT_STRING
import com.bera.collegesearch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCollegesUseCase @Inject constructor(
    private val repository: CutoffRepository
) {
    operator fun invoke(category: String): Flow<Resource<ArrayList<CutoffItem>>> =
        flow {
            try {
                emit(Resource.Loading())
                val cutoffs = repository.getAllCutoffs()
                val filteredCutoffs = Cutoff()
                when(category) {
                    "iit" -> {
                        cutoffs.forEach {
                            if (it.Institute.contains(IIT_STRING) || it.Institute.contains(IIT_STRING_1)) {
                                filteredCutoffs.add(it)
                            }
                        }
                    }
                    "nit" -> {
                        cutoffs.forEach {
                            if (it.Institute.contains(NIT_STRING)) {
                                filteredCutoffs.add(it)
                            }
                        }
                    }
                    "iiit" -> {
                        cutoffs.forEach {
                            if (it.Institute.contains(IIIT_STRING)) {
                                filteredCutoffs.add(it)
                            }
                        }
                    }
                    else -> {
                        cutoffs.forEach {
                            if (!it.Institute.contains(IIT_STRING)
                                && !it.Institute.contains(IIT_STRING_1)
                                && !it.Institute.contains(NIT_STRING)
                                && !it.Institute.contains(IIIT_STRING)
                            ) {
                                filteredCutoffs.add(it)
                            }
                        }
                    }
                }
                emit(Resource.Success(filteredCutoffs))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
            }
        }
}