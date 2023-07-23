package com.bera.collegesearch.screens.cutoffsbyrank

import com.bera.collegesearch.models.CutoffItem

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
