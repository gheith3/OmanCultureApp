package com.oman.culture.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oman.culture.ui.theme.Primary
import com.oman.culture.ui.theme.Tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isArabic: Boolean,
    isDarkMode: Boolean,
    onLanguageChange: (Boolean) -> Unit,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isArabic) "الإعدادات" else "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Language Section
            SettingsSection(
                title = if (isArabic) "اللغة" else "Language",
                icon = Icons.Default.Language
            ) {
                LanguageSelector(
                    isArabic = isArabic,
                    onLanguageChange = onLanguageChange
                )
            }
            
            // Theme Section
            SettingsSection(
                title = if (isArabic) "المظهر" else "Appearance",
                icon = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode
            ) {
                ThemeToggle(
                    isDarkMode = isDarkMode,
                    isArabic = isArabic,
                    onThemeChange = onThemeChange
                )
            }
            
            // About Section
            SettingsSection(
                title = if (isArabic) "حول التطبيق" else "About",
                icon = Icons.Default.Info
            ) {
                AboutContent(isArabic = isArabic)
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            content()
        }
    }
}

@Composable
private fun LanguageSelector(
    isArabic: Boolean,
    onLanguageChange: (Boolean) -> Unit
) {
    Column {
        // English option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLanguageChange(false) }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !isArabic,
                onClick = { onLanguageChange(false) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Primary
                )
            )
            Text(
                text = "🇬🇧  English",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        
        // Arabic option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLanguageChange(true) }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isArabic,
                onClick = { onLanguageChange(true) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Primary
                )
            )
            Text(
                text = "🇴🇲  العربية",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun ThemeToggle(
    isDarkMode: Boolean,
    isArabic: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = if (isArabic) "الوضع الداكن" else "Dark Mode",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = if (isDarkMode) {
                    if (isArabic) "مفعّل" else "Enabled"
                } else {
                    if (isArabic) "معطّل" else "Disabled"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = isDarkMode,
            onCheckedChange = onThemeChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Primary,
                checkedTrackColor = Primary.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
private fun AboutContent(isArabic: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // App Name
        AboutItem(
            label = if (isArabic) "اسم التطبيق" else "App Name",
            value = if (isArabic) "ثقافة عُمان" else "Oman Culture"
        )
        
        HorizontalDivider()
        
        // Version
        AboutItem(
            label = if (isArabic) "الإصدار" else "Version",
            value = "1.0.0"
        )
        
        HorizontalDivider()
        
        // Developer
        AboutItem(
            label = if (isArabic) "المطور" else "Developer",
            value = "Gheith Alrawahi 2120246006"
        )
        
        HorizontalDivider()
        
        // Description
        Text(
            text = if (isArabic) {
                "تطبيق يعرض التراث الثقافي العُماني الغني من خلال شخصيات عُمانية بارزة في مختلف المجالات."
            } else {
                "An app showcasing Oman's rich cultural heritage through famous Omani figures across various fields."
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Copyright
        Text(
            text = "© 2024 Oman Culture",
            style = MaterialTheme.typography.labelSmall,
            color = Tertiary
        )
    }
}

@Composable
private fun AboutItem(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
