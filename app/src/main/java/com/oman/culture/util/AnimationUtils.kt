package com.oman.culture.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

fun Modifier.staggeredAnimation(
    index: Int,
    baseDelay: Int = 50
): Modifier = composed {
    val animatable = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        delay((index * baseDelay).toLong())
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )
    }
    
    graphicsLayer {
        alpha = animatable.value
        translationY = (1f - animatable.value) * 50f
    }
}

fun Modifier.fadeSlideIn(
    durationMillis: Int = 300,
    delayMillis: Int = 0
): Modifier = composed {
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(30f) }
    
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
        )
    }
    
    LaunchedEffect(Unit) {
        delay(delayMillis.toLong())
        offsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis, easing = FastOutSlowInEasing)
        )
    }
    
    graphicsLayer {
        this.alpha = alpha.value
        translationY = offsetY.value
    }
}

object AnimationDurations {
    const val SHORT = 150
    const val MEDIUM = 300
    const val LONG = 500
}

object AnimationDelays {
    const val STAGGER_BASE = 50
    const val SCREEN_TRANSITION = 100
}
