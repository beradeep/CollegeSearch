package com.bera.josaahelpertool.repository

import android.util.Log
import com.bera.josaahelpertool.models.UniversityImageResponse
import com.bera.josaahelpertool.network.UnsplashApi
import javax.inject.Inject
import kotlin.random.Random

class UniversityImageRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
    suspend fun getUniversityImages(): UniversityImageResponse {
        val page = Random.nextInt(1, 4)
        return unsplashApi.getUniversityImages(page)
    }
}