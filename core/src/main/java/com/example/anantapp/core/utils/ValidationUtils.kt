package com.example.anantapp.core.utils

/**
 * Utility functions for validation
 */
object ValidationUtils {

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex(Constants.EMAIL_REGEX))
    }

    /**
     * Validate phone number format
     */
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex(Constants.PHONE_REGEX))
    }

    /**
     * Validate password strength
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.MIN_PASSWORD_LENGTH
    }

    /**
     * Validate non-empty string
     */
    fun isNotEmpty(text: String): Boolean {
        return text.isNotBlank()
    }

    /**
     * Validate number range
     */
    fun isInRange(value: Int, min: Int, max: Int): Boolean {
        return value in min..max
    }
}
