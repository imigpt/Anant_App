package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class for a single donation record
 */
data class DonationRecord(
    val id: String,
    val date: String,
    val fundraiserName: String,
    val amount: String,
    val status: String
)

/**
 * Production-level ViewModel for Donation History Screen
 * Manages donation history records and search/filter functionality
 */
data class DonationHistoryState(
    val donations: List<DonationRecord> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "this_month",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class DonationHistoryViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(DonationHistoryState(
        donations = listOf(
            DonationRecord(
                id = "1",
                date = "09/07/25",
                fundraiserName = "Orphan Child\nEducation Drive",
                amount = "₹1,000",
                status = "Verified"
            ),
            DonationRecord(
                id = "2",
                date = "09/07/25",
                fundraiserName = "Orphan Child\nEducation Drive",
                amount = "₹1,000",
                status = "Verified"
            ),
            DonationRecord(
                id = "3",
                date = "09/07/25",
                fundraiserName = "Orphan Child\nEducation Drive",
                amount = "₹1,000",
                status = "Verified"
            )
        )
    ))
    val uiState: StateFlow<DonationHistoryState> = _uiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun updateFilter(filter: String) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }

    fun downloadReceipt(donationId: String) {
        // TODO: Implement receipt download logic
    }
}
