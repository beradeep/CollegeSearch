package com.bera.josaahelpertool.models

import androidx.annotation.Keep

@Keep
data class Quote(
    val quote: String,
    val author: String,
    val category: String
)
