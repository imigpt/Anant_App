package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GovernmentFundraiserItem(
    val id: String,
    val title: String,
    val description: String,
    val amountRaised: String,
    val isVerified: Boolean
)

data class GovernmentFundraisersUiState(
    val fundraisers: List<GovernmentFundraiserItem> = emptyList(),
    val selectedFundraiser: GovernmentFundraiserItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class GovernmentFundraisersResult {
    object Idle : GovernmentFundraisersResult()
    data class Success(val fundraiser: GovernmentFundraiserItem) : GovernmentFundraisersResult()
    data class Error(val message: String) : GovernmentFundraisersResult()
}

class GovernmentFundraisersViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        GovernmentFundraisersUiState(
            fundraisers = listOf(
                GovernmentFundraiserItem(
                    id = "1",
                    title = "PM CARES FUNDS",
                    description = "Contribute to the Prime Minister's...",
                    amountRaised = "₹ 2,50,000.00",
                    isVerified = true
                ),
                GovernmentFundraiserItem(
                    id = "2",
                    title = "NDRF Relief Fund",
                    description = "Support disaster response and...",
                    amountRaised = "₹ 2,50,000.00",
                    isVerified = true
                ),
                GovernmentFundraiserItem(
                    id = "3",
                    title = "Beti Bachao, Beti Padhao",
                    description = "Donate for the empowerment of...",
                    amountRaised = "₹ 2,50,000.00",
                    isVerified = false
                ),
                GovernmentFundraiserItem(
                    id = "4",
                    title = "Clean India Initiative",
                    description = "Help in creating a cleaner and gr...",
                    amountRaised = "₹ 2,50,000.00",
                    isVerified = false
                )
            )
        )
    )
    val uiState: StateFlow<GovernmentFundraisersUiState> = _uiState.asStateFlow()

    fun selectFundraiser(fundraiser: GovernmentFundraiserItem) {
        _uiState.value = _uiState.value.copy(selectedFundraiser = fundraiser)
    }

    fun fetchFundraisers() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call would go here
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedFundraiser = null)
    }
}
