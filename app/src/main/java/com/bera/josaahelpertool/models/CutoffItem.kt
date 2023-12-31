package com.bera.josaahelpertool.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CutoffItem(
    @SerializedName("Academic Program Name")
    val AcademicProgramName: String,
    @SerializedName("Closing Rank")
    val ClosingRank: String,
    @SerializedName("Gender")
    val Gender: String,
    @SerializedName("Institute")
    val Institute: String,
    @SerializedName("Opening Rank")
    val OpeningRank: String,
    @SerializedName("Quota")
    val Quota: String,
    @SerializedName("Seat Type")
    val SeatType: String
)