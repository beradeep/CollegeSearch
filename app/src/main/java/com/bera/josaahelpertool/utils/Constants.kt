package com.bera.josaahelpertool.utils

import com.bera.josaahelpertool.BuildConfig
import com.bera.josaahelpertool.models.CutoffItem

object Constants {
    const val BASE_URL_CUTOFF = "https://api.npoint.io/"
    const val BASE_URL_QUOTES = "https://api.api-ninjas.com/"
    const val BASE_URL_UNSPLASH = "https://api.unsplash.com/"
    const val API_KEY_CUTOFF = BuildConfig.API_KEY_CUTOFF
    const val API_KEY_QUOTES = BuildConfig.API_KEY_QUOTES
    const val API_KEY_UNSPLASH = BuildConfig.API_KEY_UNSPLASH

    const val IIT_STRING = "Indian Institute of Technology"
    const val IIT_STRING_1 = "Indian Institute  of Technology"
    const val NIT_STRING = "National Institute of Technology"
    const val IIIT_STRING = "Indian Institute of Information Technology"
    const val QUERY = "university campus"
    val FakeCutoffItem = CutoffItem(
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}