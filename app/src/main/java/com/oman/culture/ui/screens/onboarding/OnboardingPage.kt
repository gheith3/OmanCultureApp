package com.oman.culture.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.oman.culture.R
import com.oman.culture.ui.theme.Primary

data class OnboardingPage(
    val titleEn: String,
    val titleAr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    @DrawableRes val imageRes: Int,
    val backgroundColor: Color = Color.White
) {
    fun getTitle(isArabic: Boolean): String = if (isArabic) titleAr else titleEn
    fun getDescription(isArabic: Boolean): String = if (isArabic) descriptionAr else descriptionEn
}

val onboardingPages = listOf(
    OnboardingPage(
        titleEn = "Welcome to Oman Culture",
        titleAr = "مرحباً بك في ثقافة عُمان",
        descriptionEn = "Discover the rich heritage of the Sultanate of Oman",
        descriptionAr = "اكتشف التراث الغني لسلطنة عُمان",
        imageRes = R.drawable.img0
    ),
    OnboardingPage(
        titleEn = "Land of Heritage",
        titleAr = "أرض التراث",
        descriptionEn = "Oman is known for its ancient forts, stunning landscapes, and warm hospitality spanning over 5,000 years of civilization.",
        descriptionAr = "تشتهر عُمان بقلاعها القديمة ومناظرها الخلابة وكرم الضيافة على مدى 5000 عام من الحضارة.",
        imageRes = R.drawable.img2
    ),
    OnboardingPage(
        titleEn = "Meet the Icons",
        titleAr = "تعرف على الرموز",
        descriptionEn = "Explore legendary leaders, poets, artists, athletes, and scholars who shaped Oman's identity.",
        descriptionAr = "استكشف القادة والشعراء والفنانين والرياضيين والعلماء الذين شكلوا هوية عُمان.",
        imageRes = R.drawable.img3
    ),
    OnboardingPage(
        titleEn = "Choose Your Language",
        titleAr = "اختر لغتك",
        descriptionEn = "Select your preferred language to continue",
        descriptionAr = "اختر لغتك المفضلة للمتابعة",
        imageRes = R.drawable.img4
    )
)
