package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DonationRecord(
    val id: String,
    val fundraiserName: String,
    val fundraiserCategory: String,
    val amountDonated: String,
    val dateOfDonation: String,
    val status: String,
    val receiptUrl: String? = null
)

data class DonationHistoryUiState(
    val donations: List<DonationRecord> = emptyList(),
    val searchQuery: String = "",
    val filterCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DonationHistoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DonationHistoryUiState())
    val uiState: StateFlow<DonationHistoryUiState> = _uiState.asStateFlow()

    init {
        // Load sample donations
        _uiState.value = _uiState.value.copy(
            donations = listOf(
                DonationRecord(
                    id = "1",
                    fundraiserName = "Education for Underprivileged",
                    fundraiserCategory = "Education",
                    amountDonated = "₹1,000",
                    dateOfDonation = "Apr 15 2025",
                    status = "Success",
                    receiptUrl = "receipt_1.pdf"
                ),
                DonationRecord(
                    id = "2",
                    fundraiserName = "Medical Emergency Fund",
                    fundraiserCategory = "Health",
                    amountDonated = "₹5,000",
                    dateOfDonation = "Apr 10 2025",
                    status = "Success",
                    receiptUrl = "receipt_2.pdf"
                ),
                DonationRecord(
                    id = "3",
                    fundraiserName = "Community Development",
                    fundraiserCategory = "Community",
                    amountDonated = "₹2,500",
                    dateOfDonation = "Apr 05 2025",
                    status = "Success",
                    receiptUrl = "receipt_3.pdf"
                )
            )
        )
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun setFilterCategory(category: String?) {
        _uiState.value = _uiState.value.copy(filterCategory = category)
    }

    fun downloadReceipt(donationId: String) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // API call to download receipt would go here
    }

    fun getFilteredDonations(): List<DonationRecord> {
        val query = _uiState.value.searchQuery.lowercase()
        val filterCategory = _uiState.value.filterCategory

        return _uiState.value.donations.filter { donation ->
            val matchesQuery = donation.fundraiserName.lowercase().contains(query) ||
                    donation.amountDonated.contains(query)
            val matchesFilter = filterCategory == null || donation.fundraiserCategory == filterCategory

            matchesQuery && matchesFilter
        }
    }
}
