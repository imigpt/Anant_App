package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Data class for a single transaction record
 */
data class Transaction(
    val id: String,
    val type: String,
    val description: String,
    val date: String,
    val amount: String,
    val isCredit: Boolean,
    val icon: String = if (type == "Top up balance") "+" else "-"
)

/**
 * Production-level ViewModel for Transaction Screen
 * Manages transaction history with search/filter functionality
 */
data class TransactionHistoryState(
    val transactions: List<Transaction> = emptyList(),
    val searchQuery: String = "",
    val selectedFilter: String = "all",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class TransactionViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(TransactionHistoryState(
        transactions = listOf(
            Transaction(
                id = "1",
                type = "Top up balance",
                description = "Top up balance",
                date = "5 Apr 2025",
                amount = "+Rs 50,000",
                isCredit = true
            ),
            Transaction(
                id = "2",
                type = "Donated",
                description = "Donated",
                date = "4 Apr 2025",
                amount = "-Rs 25,000",
                isCredit = false
            ),
            Transaction(
                id = "3",
                type = "Donated",
                description = "Donated",
                date = "4 Apr 2025",
                amount = "-Rs 25,000",
                isCredit = false
            ),
            Transaction(
                id = "4",
                type = "Top up balance",
                description = "Top up balance",
                date = "3 Apr 2025",
                amount = "+Rs 50,000",
                isCredit = true
            ),
            Transaction(
                id = "5",
                type = "Donated",
                description = "Donated",
                date = "1 Apr 2025",
                amount = "-Rs 10,000",
                isCredit = false
            ),
            Transaction(
                id = "6",
                type = "Donated",
                description = "Donated",
                date = "29 Mar 2025",
                amount = "-Rs 50,000",
                isCredit = false
            ),
            Transaction(
                id = "7",
                type = "Donated",
                description = "Donated",
                date = "29 Mar 2025",
                amount = "-Rs 50,000",
                isCredit = false
            ),
            Transaction(
                id = "8",
                type = "Top up balance",
                description = "Top up balance",
                date = "25 Mar 2025",
                amount = "+Rs 1,10,000",
                isCredit = true
            )
        )
    ))
    val uiState: StateFlow<TransactionHistoryState> = _uiState.asStateFlow()

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun updateFilter(filter: String) {
        _uiState.value = _uiState.value.copy(selectedFilter = filter)
    }
}
