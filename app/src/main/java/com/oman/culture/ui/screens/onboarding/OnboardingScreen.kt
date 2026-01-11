package com.oman.culture.ui.screens.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Secondary
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()
    var isArabic by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Skip button
        if (pagerState.currentPage < onboardingPages.lastIndex) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(onboardingPages.lastIndex)
                        }
                    }
                ) {
                    Text(
                        text = if (isArabic) "تخطي" else "Skip",
                        color = Primary
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(56.dp))
        }
        
        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(
                page = onboardingPages[page],
                isArabic = isArabic,
                isLastPage = page == onboardingPages.lastIndex,
                onLanguageSelected = { language ->
                    isArabic = language == "ar"
                    onLanguageSelected(language)
                }
            )
        }
        
        // Page Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { index ->
                PageIndicatorDot(isSelected = pagerState.currentPage == index)
            }
        }
        
        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            if (pagerState.currentPage > 0) {
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (isArabic) "رجوع" else "Back")
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Next/Start button
            if (pagerState.currentPage < onboardingPages.lastIndex) {
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text(if (isArabic) "التالي" else "Next")
                }
            } else {
                Button(
                    onClick = onComplete,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                ) {
                    Text(if (isArabic) "ابدأ الاستكشاف" else "Start Exploring")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    isArabic: Boolean,
    isLastPage: Boolean,
    onLanguageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Image
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .weight(1f)
                .padding(top = 32.dp, bottom = 16.dp)
                .clip(RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = page.getTitle(isArabic),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description
            Text(
                text = page.getDescription(isArabic),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            
            // Language selection on last page
            if (isLastPage) {
                Spacer(modifier = Modifier.height(32.dp))
                
                LanguageSelectionButtons(
                    isArabic = isArabic,
                    onLanguageSelected = onLanguageSelected
                )
            }
        }
    }
}

@Composable
private fun LanguageSelectionButtons(
    isArabic: Boolean,
    onLanguageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // English button
        OutlinedButton(
            onClick = { onLanguageSelected("en") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (!isArabic) Primary.copy(alpha = 0.1f) else Color.Transparent
            )
        ) {
            Text(
                text = "🇬🇧  English",
                style = MaterialTheme.typography.titleMedium,
                color = if (!isArabic) Primary else Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Arabic button
        OutlinedButton(
            onClick = { onLanguageSelected("ar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isArabic) Secondary.copy(alpha = 0.1f) else Color.Transparent
            )
        ) {
            Text(
                text = "🇴🇲  العربية",
                style = MaterialTheme.typography.titleMedium,
                color = if (isArabic) Secondary else Color.Gray
            )
        }
    }
}

@Composable
private fun PageIndicatorDot(isSelected: Boolean) {
    val size by animateDpAsState(
        targetValue = if (isSelected) 12.dp else 8.dp,
        label = "dot_size"
    )
    val color by animateColorAsState(
        targetValue = if (isSelected) Primary else Color.LightGray,
        label = "dot_color"
    )
    
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}
