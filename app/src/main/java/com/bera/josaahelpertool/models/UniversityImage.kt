package com.bera.josaahelpertool.models

import com.bera.josaahelpertool.models.ui.TopHalfItem
import com.google.gson.annotations.SerializedName

data class UniversityImageResponse(
    val results: List<UniversityImage>
)

data class UniversityImage(
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String,
    val urls: Urls,
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    @SerializedName("small_s3")
    val smallS3: String
)

fun UniversityImage.toModel() =
    TopHalfItem(imageUrl = urls.regular, name = description ?: altDescription)