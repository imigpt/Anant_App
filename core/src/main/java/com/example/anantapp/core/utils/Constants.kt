package com.example.anantapp.core.utils

/**
 * Constants used across the app
 */
object Constants {
    // API Constants
    const val BASE_URL = "https://api.anantapp.com/"
    const val TIMEOUT_SECONDS = 30L

    // Navigation
    const val ROUTE_HOME = "home"
    const val ROUTE_PROFILE = "profile"
    const val ROUTE_SETTINGS = "settings"

    // Data Storage
    const val PREFERENCE_NAME = "anant_app_prefs"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_USER_ID = "user_id"
    const val KEY_THEME_MODE = "theme_mode"

    // Validation
    const val MIN_PASSWORD_LENGTH = 8
    const val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$"
    const val PHONE_REGEX = "^[0-9]{10}$"

    // Timeouts
    const val ANIMATION_DURATION_SHORT = 300
    const val ANIMATION_DURATION_MEDIUM = 500
    const val ANIMATION_DURATION_LONG = 800
}
