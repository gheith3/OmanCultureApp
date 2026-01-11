package com.oman.culture.data.model

data class Figure(
    val id: Int,
    val nameEn: String,
    val nameAr: String,
    val category: Category,
    val imageUrl: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val biographyEn: String,
    val biographyAr: String,
    val achievementsEn: List<String>,
    val achievementsAr: List<String>,
    val era: String,
    val yearsActive: Int = 0,
    val worksCount: Int = 0
) {
    fun getName(isArabic: Boolean): String = if (isArabic) nameAr else nameEn
    fun getDescription(isArabic: Boolean): String = if (isArabic) descriptionAr else descriptionEn
    fun getBiography(isArabic: Boolean): String = if (isArabic) biographyAr else biographyEn
    fun getAchievements(isArabic: Boolean): List<String> = if (isArabic) achievementsAr else achievementsEn
}
