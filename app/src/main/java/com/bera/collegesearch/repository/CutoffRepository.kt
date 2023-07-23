package com.bera.collegesearch.repository

import com.bera.collegesearch.models.Cutoff
import com.bera.collegesearch.network.CutoffApi
import javax.inject.Inject

class CutoffRepository @Inject constructor(private val api: CutoffApi) {

    suspend fun getAllCutoffs(): Cutoff {
        return api.getAllCutoffs()
    }
}