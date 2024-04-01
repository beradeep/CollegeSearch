package com.bera.josaahelpertool.use_cases

import com.bera.josaahelpertool.models.UniversityImage
import com.bera.josaahelpertool.models.toModel
import com.bera.josaahelpertool.models.ui.TopHalfItem
import com.bera.josaahelpertool.repository.UniversityImageRepository
import javax.inject.Inject

class GetUniversityImagesUseCase @Inject constructor(private val repository: UniversityImageRepository) {
    suspend fun getAllImages(): List<TopHalfItem> {
        try {
            val response = repository.getUniversityImages()
            val universities = response.results
            val keywords = listOf("student", "university", "college")
            val filtered = filterImagesByKeywords(universities, keywords)

            return filtered.map { it.toModel() }

        } catch (e: Exception) {

        }
        return emptyList()
    }
}

fun filterImagesByKeywords(
    images: List<UniversityImage>,
    keywords: List<String>
): List<UniversityImage> {
    return images.filter { image ->
        val description = image.description ?: image.altDescription
        val hasKeyword = keywords.any { keyword -> description.lowercase().contains(keyword) }
        description.length < 100 && hasKeyword
    }
}