package com.example.anantapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Profile Screen Theme - Orange & Red gradient theme
 * Used for: ProfileSettingsScreen, UserDetailsScreen, ContactInformationScreen, BirthdayCardScreen
 */
object ProfileTheme {
    // Primary Colors
    val primaryGradient = listOf(
        Color(0xFFFF6F00),  // Orange
        Color(0xFFE53935)   // Red
    )
    
    val accentColor = Color(0xFFFF7043)  // Light Red Orange
    val backgroundColor = Color(0xFFFFF3E0)
    val cardBackground = Color.White.copy(alpha = 0.45f)
    val textPrimary = Color(0xFF1a1a1a)
    val textSecondary = Color(0xFF666666)
    
    // Component Styles
    val cornerRadius: Dp = 32.dp
    val elevation: Dp = 12.dp
    val shadowColor = Color.Black.copy(alpha = 0.2f)
    
    // Specific to Profile
    val editColor = Color(0xFF2196F3)       // Blue for edit
    val deleteColor = Color(0xFFf44336)     // Red for delete
    val verifiedColor = Color(0xFF4CAF50)   // Green for verified
}
