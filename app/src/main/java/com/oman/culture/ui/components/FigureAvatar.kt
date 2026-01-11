package com.oman.culture.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.PrimaryLight
import com.oman.culture.ui.theme.Secondary
import com.oman.culture.ui.theme.SecondaryLight
import com.oman.culture.ui.theme.Tertiary

@Composable
fun FigureAvatar(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    borderWidth: Dp = 4.dp,
    animated: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "avatar_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                ambientColor = Primary.copy(alpha = 0.3f),
                spotColor = Secondary.copy(alpha = 0.2f)
            )
    ) {
        // Gradient border with optional rotation
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (animated) Modifier.rotate(rotation) else Modifier
                )
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Primary,
                            PrimaryLight,
                            Tertiary,
                            SecondaryLight,
                            Secondary,
                            Primary
                        )
                    ),
                    shape = CircleShape
                )
                .padding(borderWidth)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build(),
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.White),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Primary.copy(alpha = 0.3f),
                            modifier = Modifier.size(size / 2)
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Primary.copy(alpha = 0.5f),
                            modifier = Modifier.size(size / 2)
                        )
                    }
                }
            )
        }
    }
}
