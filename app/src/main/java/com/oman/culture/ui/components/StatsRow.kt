package com.oman.culture.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Secondary
import com.oman.culture.ui.theme.Tertiary

@Composable
fun StatsRow(
    era: String,
    worksCount: Int,
    yearsActive: Int,
    isArabic: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(
                value = era,
                label = if (isArabic) "الفترة" else "Era",
                accentColor = Secondary,
                modifier = Modifier.weight(1f)
            )
            
            // Modern divider
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
            
            StatItem(
                value = worksCount.toString(),
                label = if (isArabic) "الأعمال" else "Works",
                accentColor = Primary,
                modifier = Modifier.weight(1f)
            )
            
            // Modern divider
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
            
            StatItem(
                value = yearsActive.toString(),
                label = if (isArabic) "سنوات" else "Years",
                accentColor = Tertiary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Accent dot
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = accentColor,
                    shape = CircleShape
                )
        )
        
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(6.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}
