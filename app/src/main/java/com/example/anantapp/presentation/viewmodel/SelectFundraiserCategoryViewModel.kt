package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class FundraiserCategoryState(
    val categories: List<FundraiserCategory> = listOf(
        FundraiserCategory("health", "Health"),
        FundraiserCategory("accident_relief", "Accident Relief"),
        FundraiserCategory("death_support", "Death Support"),
        FundraiserCategory("education", "Education"),
        FundraiserCategory("other", "Other"),
        FundraiserCategory("orphan_care", "Orphan Care")
    ),
    val selectedCategory: String? = "accident_relief",
    val isLoading: Boolean = false,
    val error: String? = null
)

data class FundraiserCategory(
    val id: String,
    val name: String
)

class SelectFundraiserCategoryViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(FundraiserCategoryState())
    val uiState: StateFlow<FundraiserCategoryState> = _uiState.asStateFlow()
    
    /**
     * Select a fundraiser category
     * @param categoryId The ID of the selected category
     */
    fun selectCategory(categoryId: String) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = categoryId)
        }
    }
    
    /**
     * Clear the selected category
     */
    fun clearSelection() {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = null)
        }
    }
    
    /**
     * Get the currently selected category
     */
    fun getSelectedCategory(): String? = _uiState.value.selectedCategory
    
    /**
     * Check if a category is selected
     */
    fun isCategorySelected(): Boolean = _uiState.value.selectedCategory != null
}
