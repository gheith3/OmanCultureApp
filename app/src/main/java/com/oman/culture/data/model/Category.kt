package com.oman.culture.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Secondary
import com.oman.culture.ui.theme.Tertiary

enum class Category(
    val id: String,
    val displayNameEn: String,
    val displayNameAr: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val icon: ImageVector,
    val color: Color
) {
    HISTORICAL_LEADERS(
        id = "historical_leaders",
        displayNameEn = "Historical Leaders",
        displayNameAr = "القادة التاريخيون",
        descriptionEn = "Sultans and rulers who shaped Oman's history",
        descriptionAr = "السلاطين والحكام الذين شكلوا تاريخ عُمان",
        icon = Icons.Default.AccountBalance,
        color = Primary
    ),
    POETS_WRITERS(
        id = "poets_writers",
        displayNameEn = "Poets & Writers",
        displayNameAr = "الشعراء والكتّاب",
        descriptionEn = "Literary figures who enriched Omani culture",
        descriptionAr = "الأدباء الذين أثروا الثقافة العُمانية",
        icon = Icons.Default.MenuBook,
        color = Secondary
    ),
    ARTISTS(
        id = "artists",
        displayNameEn = "Artists & Musicians",
        displayNameAr = "الفنانون والموسيقيون",
        descriptionEn = "Creative talents in arts and music",
        descriptionAr = "المواهب الإبداعية في الفنون والموسيقى",
        icon = Icons.Default.MusicNote,
        color = Tertiary
    ),
    SPORTS(
        id = "sports",
        displayNameEn = "Sports Icons",
        displayNameAr = "أيقونات الرياضة",
        descriptionEn = "Athletes who brought glory to Oman",
        descriptionAr = "الرياضيون الذين جلبوا المجد لعُمان",
        icon = Icons.Default.SportsSoccer,
        color = Primary
    ),
    SCHOLARS(
        id = "scholars",
        displayNameEn = "Scientists & Scholars",
        displayNameAr = "العلماء والباحثون",
        descriptionEn = "Academics and researchers",
        descriptionAr = "الأكاديميون والباحثون",
        icon = Icons.Default.Science,
        color = Secondary
    ),
    MODERN(
        id = "modern",
        displayNameEn = "Modern Influencers",
        displayNameAr = "المؤثرون المعاصرون",
        descriptionEn = "Contemporary figures shaping modern Oman",
        descriptionAr = "الشخصيات المعاصرة التي تشكل عُمان الحديثة",
        icon = Icons.Default.TrendingUp,
        color = Tertiary
    );
    
    fun getDisplayName(isArabic: Boolean): String = 
        if (isArabic) displayNameAr else displayNameEn
    
    fun getDescription(isArabic: Boolean): String = 
        if (isArabic) descriptionAr else descriptionEn
    
    companion object {
        fun fromId(id: String): Category? = entries.find { it.id == id }
    }
}
