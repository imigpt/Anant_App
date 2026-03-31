package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Transaction(
    val id: String,
    val type: String,
    val amount: String,
    val date: String,
    val status: String,
    val isCredit: Boolean
)

data class TransactionScreenUiState(
    val transactions: List<Transaction> = emptyList(),
    val searchQuery: String = "",
    val filterType: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class TransactionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TransactionScreenUiState())
    val uiState: StateFlow<TransactionScreenUiState> = _uiState.asStateFlow()

    init {
        // Load sample transactions
        _uiState.value = _uiState.value.copy(
            transactions = listOf(
                Transaction(
                    id = "1",
                    type = "Top up Wallet",
                    amount = "₹500",
                    date = "Apr 15 2025",
                    status = "Success",
                    isCredit = true
                ),
                Transaction(
                    id = "2",
                    type = "Donation",
                    amount = "₹1000",
                    date = "Apr 12 2025",
                    status = "Success",
                    isCredit = false
                ),
                Transaction(
                    id = "3",
                    type = "Refund",
                    amount = "₹250",
                    date = "Apr 10 2025",
                    status = "Success",
                    isCredit = true
                )
            )
        )
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun setFilterType(type: String?) {
        _uiState.value = _uiState.value.copy(filterType = type)
    }

    fun getFilteredTransactions(): List<Transaction> {
        val query = _uiState.value.searchQuery.lowercase()
        val filterType = _uiState.value.filterType
        
        return _uiState.value.transactions.filter { transaction ->
            val matchesQuery = transaction.type.lowercase().contains(query) ||
                    transaction.amount.contains(query)
            val matchesFilter = filterType == null || transaction.type == filterType
            
            matchesQuery && matchesFilter
        }
    }
}
