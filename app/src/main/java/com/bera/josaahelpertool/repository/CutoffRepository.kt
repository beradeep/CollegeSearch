package com.bera.josaahelpertool.repository

import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.network.CutoffApi
import javax.inject.Inject

class CutoffRepository @Inject constructor(private val api: CutoffApi) {

    suspend fun getAllCutoffs(): Cutoff {
        return api.getAllCutoffs()
    }
}