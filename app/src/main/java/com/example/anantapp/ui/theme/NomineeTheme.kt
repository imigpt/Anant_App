package com.example.anantapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Nominee Details Screen Theme - Cyan & Blue gradient theme
 * Used for: NomineeDetailsScreen, FamilyMemberDetailsScreen, AddNomineeCardsScreen
 */
object NomineeTheme {
    // Primary Colors
    val primaryGradient = listOf(
        Color(0xFF00BCD4),  // Cyan
        Color(0xFF0288D1)   // Deep Blue
    )
    
    val accentColor = Color(0xFF2196F3)  // Blue
    val backgroundColor = Color.White
    val cardBackground = Color.White.copy(alpha = 0.5f)
    val textPrimary = Color(0xFF1a1a1a)
    val textSecondary = Color(0xFF666666)
    
    // Component Styles
    val cornerRadius: Dp = 20.dp
    val elevation: Dp = 8.dp
    val shadowColor = Color.Black.copy(alpha = 0.12f)
    
    // Specific to Nominee
    val activeColor = Color(0xFF4CAF50)     // Green for active nominee
    val inactiveColor = Color(0xFFBDBDBD)   // Gray for inactive
    val pendingColor = Color(0xFFFFC107)    // Amber for pending
}
