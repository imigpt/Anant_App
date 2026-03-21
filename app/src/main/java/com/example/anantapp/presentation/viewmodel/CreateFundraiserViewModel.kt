package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.anantapp.data.model.CreateFundraiserState
import com.example.anantapp.data.model.FundraiserPhoto
import com.example.anantapp.data.repository.CreateFundraiserRepository

class CreateFundraiserViewModel(
    private val repository: CreateFundraiserRepository = CreateFundraiserRepository()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateFundraiserState())
    val uiState: StateFlow<CreateFundraiserState> = _uiState.asStateFlow()
    
    /**
     * Update fundraiser title
     */
    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(
                title = title,
                isFormValid = isFormValid(currentState.copy(title = title))
            )
        }
    }
    
    /**
     * Update short description
     */
    fun updateShortDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(
                shortDescription = description,
                isFormValid = isFormValid(currentState.copy(shortDescription = description))
            )
        }
    }
    
    /**
     * Update full story
     */
    fun updateFullStory(story: String) {
        _uiState.update { currentState ->
            currentState.copy(
                fullStory = story,
                isFormValid = isFormValid(currentState.copy(fullStory = story))
            )
        }
    }
    
    /**
     * Update selected category
     */
    fun updateCategory(category: String) {
        _uiState.update { currentState ->
            currentState.copy(selectedCategory = category)
        }
    }
    
    /**
     * Update phone number
     */
    fun updatePhoneNumber(phone: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = phone,
                isFormValid = isFormValid(currentState.copy(phoneNumber = phone))
            )
        }
    }
    
    /**
     * Update country code
     */
    fun updateCountryCode(code: String) {
        _uiState.update { currentState ->
            currentState.copy(countryCode = code)
        }
    }
    
    /**
     * Add photo to gallery
     */
    fun addPhoto(photoUri: String) {
        _uiState.update { currentState ->
            val newPhoto = FundraiserPhoto(
                id = "${System.currentTimeMillis()}",
                uri = photoUri,
                isSelected = false
            )
            currentState.copy(
                photos = currentState.photos + newPhoto
            )
        }
    }
    
    /**
     * Remove photo from gallery
     */
    fun removePhoto(photoId: String) {
        _uiState.update { currentState ->
            currentState.copy(
                photos = currentState.photos.filter { it.id != photoId }
            )
        }
    }
    
    /**
     * Save as draft - async operation
     */
    fun saveDraft() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val currentState = _uiState.value
            val result = repository.saveDraft(currentState)
            result.onSuccess { draftId ->
                _uiState.update { 
                    it.copy(
                        isDraft = true,
                        isLoading = false,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to save draft: ${throwable.message}"
                    )
                }
            }
        }
    }
    
    /**
     * Proceed to next step - triggers submission
     */
    fun proceedNext(): Boolean {
        val currentState = _uiState.value
        if (!isFormValid(currentState)) {
            _uiState.update { it.copy(error = "Please fill all required fields") }
            return false
        }
        
        // Start submission
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.submitFundraiser(currentState)
            result.onSuccess { fundraiserId ->
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        error = "Failed to submit: ${throwable.message}"
                    )
                }
            }
        }
        return true
    }
    
    /**
     * Validate form
     */
    private fun isFormValid(state: CreateFundraiserState): Boolean {
        return state.title.isNotBlank() &&
                state.shortDescription.isNotBlank() &&
                state.fullStory.isNotBlank() &&
                state.phoneNumber.isNotBlank() &&
                state.phoneNumber.length >= 10
    }
    
    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(error = null)
        }
    }
}
