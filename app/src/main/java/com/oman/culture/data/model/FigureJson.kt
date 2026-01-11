package com.oman.culture.data.model

import com.google.gson.annotations.SerializedName

data class FiguresResponse(
    @SerializedName("figures")
    val figures: List<FigureJson>
)

data class FigureJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nameEn")
    val nameEn: String,
    @SerializedName("nameAr")
    val nameAr: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("descriptionEn")
    val descriptionEn: String,
    @SerializedName("descriptionAr")
    val descriptionAr: String,
    @SerializedName("biographyEn")
    val biographyEn: String,
    @SerializedName("biographyAr")
    val biographyAr: String,
    @SerializedName("achievementsEn")
    val achievementsEn: List<String>,
    @SerializedName("achievementsAr")
    val achievementsAr: List<String>,
    @SerializedName("era")
    val era: String,
    @SerializedName("yearsActive")
    val yearsActive: Int,
    @SerializedName("worksCount")
    val worksCount: Int
) {
    fun toFigure(): Figure {
        val categoryEnum = try {
            Category.valueOf(category)
        } catch (e: Exception) {
            Category.MODERN
        }
        
        return Figure(
            id = id,
            nameEn = nameEn,
            nameAr = nameAr,
            category = categoryEnum,
            imageUrl = imageUrl,
            descriptionEn = descriptionEn,
            descriptionAr = descriptionAr,
            biographyEn = biographyEn,
            biographyAr = biographyAr,
            achievementsEn = achievementsEn,
            achievementsAr = achievementsAr,
            era = era,
            yearsActive = yearsActive,
            worksCount = worksCount
        )
    }
}
