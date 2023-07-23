package com.bera.collegesearch.network

import com.bera.collegesearch.models.Cutoff
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import javax.inject.Singleton

@Singleton
interface CutoffApi {
    @GET(value = "c3423fd80cca7707fe15")
    suspend fun getAllCutoffs(): Cutoff
}
//"7948f1a28863000f369f"
//"94c8e6e9c65166066355"