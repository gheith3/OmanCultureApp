package com.oman.culture.ui.screens.detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oman.culture.ui.components.FigureAvatar
import com.oman.culture.ui.components.StatsRow
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    figureId: Int,
    isArabic: Boolean,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(figureId) {
        viewModel.loadFigure(figureId)
    }
    
    LaunchedEffect(isFavorite) {
        viewModel.setFavoriteStatus(isFavorite)
    }
    
    // Share function
    fun shareFigure(name: String, description: String, imageUrl: String) {
        val shareText = """
            |$name
            |
            |$description
            |
            |$imageUrl
        """.trimMargin()
        
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Share button with modern styling
                    IconButton(
                        onClick = {
                            uiState.figure?.let { figure ->
                                val name = figure.getName(isArabic)
                                val description = figure.getDescription(isArabic)
                                shareFigure(name, description, figure.imageUrl)
                            }
                        },
                        modifier = Modifier
                            .padding(4.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Animated favorite button
                    val favoriteScale by animateFloatAsState(
                        targetValue = if (isFavorite) 1.1f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "fav_scale"
                    )
                    val favoriteColor by animateColorAsState(
                        targetValue = if (isFavorite) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(300),
                        label = "fav_color"
                    )
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .padding(4.dp)
                            .scale(favoriteScale)
                            .background(
                                color = if (isFavorite) Primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = favoriteColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            }
            uiState.figure == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isArabic) "الشخصية غير موجودة" else "Figure not found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            else -> {
                val figure = uiState.figure!!
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Modern Header with gradient background
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        figure.category.color.copy(alpha = 0.08f),
                                        Color.Transparent
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Avatar with glow effect
                            Box {
                                // Glow behind avatar
                                Box(
                                    modifier = Modifier
                                        .size(160.dp)
                                        .offset(y = 8.dp)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    figure.category.color.copy(alpha = 0.4f),
                                                    Color.Transparent
                                                )
                                            ),
                                            shape = CircleShape
                                        )
                                )
                                FigureAvatar(
                                    imageUrl = figure.imageUrl,
                                    contentDescription = figure.getName(isArabic),
                                    size = 160.dp,
                                    borderWidth = 5.dp
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Name with larger font
                            Text(
                                text = figure.getName(isArabic),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            
                            Spacer(modifier = Modifier.height(4.dp))
                            
                            // Secondary name
                            Text(
                                text = figure.getName(!isArabic),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(12.dp))
                            
                            // Modern category badge
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = figure.category.color.copy(alpha = 0.12f),
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(20.dp),
                                        ambientColor = figure.category.color.copy(alpha = 0.2f),
                                        spotColor = figure.category.color.copy(alpha = 0.1f)
                                    )
                            ) {
                                Text(
                                    text = figure.category.getDisplayName(isArabic),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    color = figure.category.color,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                    
                    // Stats Row
                    StatsRow(
                        era = figure.era,
                        worksCount = figure.worksCount,
                        yearsActive = figure.yearsActive,
                        isArabic = isArabic,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Modern Tabs with custom indicator
                    val tabs = if (isArabic) {
                        listOf("السيرة الذاتية", "الإنجازات")
                    } else {
                        listOf("Biography", "Achievements")
                    }
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tabs.forEachIndexed { index, title ->
                            val selected = uiState.selectedTab == index
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (selected) figure.category.color else Color.Transparent
                                    )
                                    .clickable { viewModel.onTabSelected(index) }
                                    .padding(vertical = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = title,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    
                    // Tab Content with modern cards
                    when (uiState.selectedTab) {
                        0 -> {
                            // Biography in a card
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 4.dp
                            ) {
                                Text(
                                    text = figure.getBiography(isArabic),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        lineHeight = 26.sp
                                    ),
                                    modifier = Modifier.padding(20.dp),
                                    textAlign = if (isArabic) TextAlign.End else TextAlign.Start,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                                )
                            }
                        }
                        1 -> {
                            // Achievements with modern list items
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                figure.getAchievements(isArabic).forEachIndexed { index, achievement ->
                                    Surface(
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        shadowElevation = 2.dp
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = if (isArabic) Arrangement.End else Arrangement.Start,
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            if (!isArabic) {
                                                // Modern bullet point
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .offset(y = 8.dp)
                                                        .background(
                                                            brush = Brush.linearGradient(
                                                                colors = listOf(
                                                                    figure.category.color,
                                                                    Secondary
                                                                )
                                                            ),
                                                            shape = CircleShape
                                                        )
                                                )
                                                Spacer(modifier = Modifier.size(12.dp))
                                            }
                                            Text(
                                                text = achievement,
                                                style = MaterialTheme.typography.bodyLarge,
                                                modifier = Modifier.weight(1f),
                                                textAlign = if (isArabic) TextAlign.End else TextAlign.Start,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                                            )
                                            if (isArabic) {
                                                Spacer(modifier = Modifier.size(12.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .size(8.dp)
                                                        .offset(y = 8.dp)
                                                        .background(
                                                            brush = Brush.linearGradient(
                                                                colors = listOf(
                                                                    Secondary,
                                                                    figure.category.color
                                                                )
                                                            ),
                                                            shape = CircleShape
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
