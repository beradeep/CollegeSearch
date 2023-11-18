package com.bera.josaahelpertool.screens.cutoffsbyrank

import androidx.annotation.Keep
import com.bera.josaahelpertool.models.CutoffItem

@Keep
data class CBRState(
    val rank: Int = 10000,
    val exam: String = "Main",
    val state: String = "HS",
    val gender: String = "Gender-Neutral",
    val quota: String = "OPEN",
    val pwd: Boolean = false,
    val cutoffs: ArrayList<CutoffItem> = ArrayList(),
    val loading: Boolean = false,
    val expandFilters: Boolean = true,
    val sortBy: String = "CR"
)
