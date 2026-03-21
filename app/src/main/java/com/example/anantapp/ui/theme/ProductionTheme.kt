package com.example.anantapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Production-level theme configuration
 * Supports different theme schemes for different screens/features
 */

// Theme 1: Banking/Wallet (Red/Orange to Blue gradient)
object BankingTheme {
    object Colors {
        val GradientStart = Color(0xFFFF5252)
        val GradientOrange = Color(0xFFFF6E40)
        val GradientPink = Color(0xFFE91E8C)
        val GradientMagenta = Color(0xFFDB1B8F)
        val GradientPurple = Color(0xFF9C27B0)
        val GradientEnd = Color(0xFF6A1B9A)
        val PrimaryAccent = Color(0xFF9C27B0)
        val SecondaryAccent = Color(0xFF6A1B9A)
        val CardBackground = Color.White
        val DarkBackground = Color(0xFF1B5E5E)
        val SpecialCardBackground = Color(0xFFFFD699)
        val SuccessGreen = Color(0xFF4CAF50)
        val TextPrimary = Color.Black
        val TextSecondary = Color.Gray
        val TextOnGradient = Color.White
        val Divider = Color.LightGray
        val Border = Color(0xFF6A1B9A)
    }

    object Spacing {
        val ExtraSmall = 4.dp
        val Small = 8.dp
        val Medium = 16.dp
        val Large = 24.dp
        val ExtraLarge = 32.dp
        val Huge = 40.dp
    }

    object Radius {
        val Small = 8.dp
        val Medium = 16.dp
        val Large = 20.dp
        val Full = 100.dp
    }

    object Elevation {
        val None = 0.dp
        val Small = 2.dp
        val Medium = 4.dp
        val Large = 8.dp
        val Extreme = 16.dp
    }
}

// Theme 2: Analytics/Dashboard (Dark theme)
object DashboardTheme {
    object Colors {
        val Background = Color.Black
        val CardBackground = Color.White
        val PrimaryAccent = Color(0xFF9C27B0)
        val TextPrimary = Color.White
        val TextSecondary = Color.Gray
    }

    val Spacing = BankingTheme.Spacing
    val Radius = BankingTheme.Radius
    val Elevation = BankingTheme.Elevation
}

// Theme 3: Balance/Simple (Purple to Black gradient)
object BalanceTheme {
    object Colors {
        val GradientPink = Color(0xFFE91E8C)
        val GradientMagenta = Color(0xFFDB1B8F)
        val GradientPurple = Color(0xFF9C27B0)
        val GradientDarkPurple = Color(0xFF6A1B9A)
        val GradientBlack = Color(0xFF1A0033)
        val PrimaryAccent = Color(0xFF9C27B0)
        val CardBackground = Color.White
        val TextOnGradient = Color.White
    }

    val Spacing = BankingTheme.Spacing
    val Radius = BankingTheme.Radius
    val Elevation = BankingTheme.Elevation
}
