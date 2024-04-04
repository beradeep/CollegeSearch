package com.bera.josaahelpertool.network

import com.bera.josaahelpertool.models.UniversityImageResponse
import com.bera.josaahelpertool.utils.Constants.API_KEY_UNSPLASH
import com.bera.josaahelpertool.utils.Constants.QUERY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface UnsplashApi {
    @GET("search/photos")
    suspend fun getUniversityImages(
        @Query("page") page: Int = 1,
        @Query("client_id") clientId: String = API_KEY_UNSPLASH,
        @Query("query") query: String = QUERY,
        @Query("per_page") perPage: Int = 30
    ): UniversityImageResponse
}