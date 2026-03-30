package com.example.anantapp.ui.theme

import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal for CurrentTheme
 * Makes the theme available throughout the composition hierarchy
 * 
 * Usage:
 * val theme = LocalCurrentTheme.current
 */
val LocalCurrentTheme = compositionLocalOf<CurrentTheme> {
    ThemeManager.getTheme(ScreenTheme.PROFILE)
}
