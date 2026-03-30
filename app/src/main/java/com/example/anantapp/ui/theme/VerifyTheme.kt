package com.example.anantapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Verify Screen Theme - Blue & Green gradient theme
 * Used for: PhotoUploadScreen, VerifyScreen, VerifyBankScreen, VerifyAddressScreen
 */
object VerifyTheme {
    // Primary Colors
    val primaryGradient = listOf(
        Color(0xFF2196F3),  // Blue
        Color(0xFF1976D2)   // Dark Blue
    )
    
    val accentColor = Color(0xFF4CAF50)  // Green
    val backgroundColor = Color.White
    val cardBackground = Color.White.copy(alpha = 0.95f)
    val textPrimary = Color(0xFF1a1a1a)
    val textSecondary = Color(0xFF666666)
    
    // Component Styles
    val cornerRadius: Dp = 16.dp
    val elevation: Dp = 8.dp
    val shadowColor = Color.Black.copy(alpha = 0.1f)
    
    // Specific to Verify
    val successColor = Color(0xFF4CAF50)
    val errorColor = Color(0xFFf44336)
    val warningColor = Color(0xFFFFC107)
}
