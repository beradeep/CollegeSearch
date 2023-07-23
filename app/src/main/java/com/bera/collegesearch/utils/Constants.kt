package com.bera.collegesearch.utils

import com.bera.collegesearch.BuildConfig
import com.bera.collegesearch.models.CutoffItem

object Constants {
    const val BASE_URL_CUTOFF = "https://api.npoint.io/"
    const val BASE_URL_QUOTES = "https://api.api-ninjas.com/"
    const val API_KEY_QUOTES = BuildConfig.API_KEY_QUOTES

    const val IIT_STRING = "Indian Institute of Technology"
    const val IIT_STRING_1 = "Indian Institute  of Technology"
    const val NIT_STRING = "National Institute of Technology"
    const val IIIT_STRING = "Indian Institute of Information Technology"
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