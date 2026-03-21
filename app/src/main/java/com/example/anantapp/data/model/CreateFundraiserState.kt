package com.example.anantapp.data.model

/**
 * Represents a photo in the fundraiser gallery
 */
data class FundraiserPhoto(
    val id: String = "",
    val uri: String = "",
    val isSelected: Boolean = false
)

/**
 * Represents fundraiser creation state
 */
data class CreateFundraiserState(
    val title: String = "",
    val shortDescription: String = "",
    val fullStory: String = "",
    val selectedCategory: String = "Health",
    val photos: List<FundraiserPhoto> = emptyList(),
    val countryCode: String = "+91",
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDraft: Boolean = false,
    val isFormValid: Boolean = false
)

/**
 * Represents available fundraiser categories
 */
data class FundraiserCategoryItem(
    val id: String,
    val name: String
)

/**
 * Companion object for fundraiser categories
 */
object FundraiserCategories {
    val categories = listOf(
        FundraiserCategoryItem("health", "Health"),
        FundraiserCategoryItem("accident_relief", "Accident Relief"),
        FundraiserCategoryItem("death_support", "Death Support"),
        FundraiserCategoryItem("education", "Education"),
        FundraiserCategoryItem("other", "Other"),
        FundraiserCategoryItem("orphan_care", "Orphan Care")
    )
}

/**
 * Country code data
 */
data class CountryCode(
    val code: String,
    val name: String,
    val flag: String
)

object CountryCodes {
    val countries = listOf(
        CountryCode("+91", "India", "🇮🇳"),
        CountryCode("+1", "USA", "🇺🇸"),
        CountryCode("+44", "UK", "🇬🇧"),
        CountryCode("+86", "China", "🇨🇳"),
        CountryCode("+81", "Japan", "🇯🇵")
    )
}
