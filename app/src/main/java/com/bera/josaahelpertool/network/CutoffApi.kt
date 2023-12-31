package com.bera.josaahelpertool.network

import com.bera.josaahelpertool.models.Cutoff
import com.bera.josaahelpertool.utils.Constants
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface CutoffApi {
    @GET(value = Constants.API_KEY_CUTOFF)
    suspend fun getAllCutoffs(): Cutoff
}
//"7948f1a28863000f369f"
//"94c8e6e9c65166066355"