package com.example.anantapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Theme Manager - Central theme configuration
 * Determines which theme to use based on the current screen context
 */
enum class ScreenTheme {
    VERIFY,
    WALLET,
    NOMINEE,
    PROFILE
}

data class CurrentTheme(
    val screenTheme: ScreenTheme,
    val primaryGradient: List<Color>,
    val accentColor: Color,
    val backgroundColor: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val cornerRadius: androidx.compose.ui.unit.Dp,
    val elevation: androidx.compose.ui.unit.Dp,
    val shadowColor: Color
)

object ThemeManager {
    
    fun getTheme(screenTheme: ScreenTheme): CurrentTheme {
        return when (screenTheme) {
            ScreenTheme.VERIFY -> CurrentTheme(
                screenTheme = ScreenTheme.VERIFY,
                primaryGradient = VerifyTheme.primaryGradient,
                accentColor = VerifyTheme.accentColor,
                backgroundColor = VerifyTheme.backgroundColor,
                cardBackground = VerifyTheme.cardBackground,
                textPrimary = VerifyTheme.textPrimary,
                textSecondary = VerifyTheme.textSecondary,
                cornerRadius = VerifyTheme.cornerRadius,
                elevation = VerifyTheme.elevation,
                shadowColor = VerifyTheme.shadowColor
            )
            
            ScreenTheme.WALLET -> CurrentTheme(
                screenTheme = ScreenTheme.WALLET,
                primaryGradient = WalletTheme.primaryGradient,
                accentColor = WalletTheme.accentColor,
                backgroundColor = WalletTheme.backgroundColor,
                cardBackground = WalletTheme.cardBackground,
                textPrimary = WalletTheme.textPrimary,
                textSecondary = WalletTheme.textSecondary,
                cornerRadius = WalletTheme.cornerRadius,
                elevation = WalletTheme.elevation,
                shadowColor = WalletTheme.shadowColor
            )
            
            ScreenTheme.NOMINEE -> CurrentTheme(
                screenTheme = ScreenTheme.NOMINEE,
                primaryGradient = NomineeTheme.primaryGradient,
                accentColor = NomineeTheme.accentColor,
                backgroundColor = NomineeTheme.backgroundColor,
                cardBackground = NomineeTheme.cardBackground,
                textPrimary = NomineeTheme.textPrimary,
                textSecondary = NomineeTheme.textSecondary,
                cornerRadius = NomineeTheme.cornerRadius,
                elevation = NomineeTheme.elevation,
                shadowColor = NomineeTheme.shadowColor
            )
            
            ScreenTheme.PROFILE -> CurrentTheme(
                screenTheme = ScreenTheme.PROFILE,
                primaryGradient = ProfileTheme.primaryGradient,
                accentColor = ProfileTheme.accentColor,
                backgroundColor = ProfileTheme.backgroundColor,
                cardBackground = ProfileTheme.cardBackground,
                textPrimary = ProfileTheme.textPrimary,
                textSecondary = ProfileTheme.textSecondary,
                cornerRadius = ProfileTheme.cornerRadius,
                elevation = ProfileTheme.elevation,
                shadowColor = ProfileTheme.shadowColor
            )
        }
    }
}
