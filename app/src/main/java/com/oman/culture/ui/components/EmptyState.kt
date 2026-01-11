package com.oman.culture.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oman.culture.ui.theme.Primary

@Composable
fun EmptyFavoritesState(
    isArabic: Boolean,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "heart_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    EmptyStateContent(
        icon = Icons.Outlined.FavoriteBorder,
        iconScale = scale,
        title = if (isArabic) "لا توجد مفضلات بعد" else "No favorites yet",
        message = if (isArabic) 
            "اضغط على أيقونة القلب على أي شخصية لحفظها هنا" 
        else 
            "Tap the heart icon on any figure to save them here",
        buttonText = if (isArabic) "استكشف الشخصيات" else "Explore Figures",
        onButtonClick = onExploreClick,
        modifier = modifier
    )
}

@Composable
fun EmptySearchState(
    isArabic: Boolean,
    modifier: Modifier = Modifier
) {
    EmptyStateContent(
        icon = Icons.Outlined.SearchOff,
        title = if (isArabic) "لا توجد نتائج" else "No results found",
        message = if (isArabic) 
            "جرب البحث بكلمات مختلفة" 
        else 
            "Try searching with different keywords",
        modifier = modifier
    )
}

@Composable
private fun EmptyStateContent(
    icon: ImageVector,
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    iconScale: Float = 1f,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .scale(iconScale),
            tint = Primary.copy(alpha = 0.5f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        if (buttonText != null && onButtonClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text(buttonText)
            }
        }
    }
}
