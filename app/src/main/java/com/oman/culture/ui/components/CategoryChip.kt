package com.oman.culture.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import com.oman.culture.data.model.Category
import com.oman.culture.ui.theme.Primary

@Composable
fun AllCategoryChip(
    isSelected: Boolean,
    isArabic: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "chip_scale"
    )
    
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = tween(200),
        label = "chip_elevation"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Primary else Color.Transparent,
        animationSpec = tween(250),
        label = "chip_bg"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Primary,
        animationSpec = tween(250),
        label = "chip_content"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Transparent else Primary.copy(alpha = 0.4f),
        animationSpec = tween(250),
        label = "chip_border"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .background(
                brush = if (isSelected) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Primary,
                            Primary.copy(alpha = 0.85f)
                        )
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                }
            )
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Apps,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = contentColor
            )
            Text(
                text = if (isArabic) "الكل" else "All",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ),
                color = contentColor
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    isArabic: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "chip_scale"
    )
    
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 0.dp,
        animationSpec = tween(200),
        label = "chip_elevation"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) category.color else Color.Transparent,
        animationSpec = tween(250),
        label = "chip_bg"
    )
    
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else category.color,
        animationSpec = tween(250),
        label = "chip_content"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Transparent else category.color.copy(alpha = 0.4f),
        animationSpec = tween(250),
        label = "chip_border"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(50),
                ambientColor = category.color.copy(alpha = 0.3f),
                spotColor = category.color.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(50))
            .background(
                brush = if (isSelected) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            category.color,
                            category.color.copy(alpha = 0.85f)
                        )
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                }
            )
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = contentColor
            )
            Text(
                text = category.getDisplayName(isArabic),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                ),
                color = contentColor
            )
        }
    }
}
