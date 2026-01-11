package com.oman.culture.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oman.culture.ui.theme.Primary

@Composable
fun LanguageSwitcher(
    isArabic: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val enBackgroundColor by animateColorAsState(
        targetValue = if (!isArabic) Primary else Color.Transparent,
        label = "en_bg"
    )
    val arBackgroundColor by animateColorAsState(
        targetValue = if (isArabic) Primary else Color.Transparent,
        label = "ar_bg"
    )
    val enTextColor by animateColorAsState(
        targetValue = if (!isArabic) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "en_text"
    )
    val arTextColor by animateColorAsState(
        targetValue = if (isArabic) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "ar_text"
    )
    
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // English option
        Text(
            text = "EN",
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(enBackgroundColor)
                .clickable { onLanguageChange(false) }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (!isArabic) FontWeight.Bold else FontWeight.Normal,
            color = enTextColor
        )
        
        // Arabic option
        Text(
            text = "ع",
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(arBackgroundColor)
                .clickable { onLanguageChange(true) }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isArabic) FontWeight.Bold else FontWeight.Normal,
            color = arTextColor
        )
    }
}
