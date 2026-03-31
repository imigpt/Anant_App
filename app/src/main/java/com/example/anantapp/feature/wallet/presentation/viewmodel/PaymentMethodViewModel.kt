package com.example.anantapp.feature.wallet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PaymentMethodUiState(
    val selectedPaymentMethod: String? = null,
    val cards: List<CardInfo> = emptyList(),
    val bankAccounts: List<BankAccount> = emptyList(),
    val upiMethods: List<UpiMethod> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CardInfo(
    val id: String,
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val last4Digits: String,
    val isDefault: Boolean = false
)

data class BankAccount(
    val id: String,
    val bankName: String,
    val accountNumber: String,
    val ifscCode: String,
    val accountHolder: String,
    val isDefault: Boolean = false
)

data class UpiMethod(
    val id: String,
    val upiId: String,
    val name: String,
    val isDefault: Boolean = false
)

sealed class PaymentMethodResult {
    object Idle : PaymentMethodResult()
    data class Success(val methodId: String) : PaymentMethodResult()
    data class Error(val message: String) : PaymentMethodResult()
}

class PaymentMethodViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentMethodUiState())
    val uiState: StateFlow<PaymentMethodUiState> = _uiState.asStateFlow()

    fun selectPaymentMethod(methodId: String) {
        _uiState.value = _uiState.value.copy(selectedPaymentMethod = methodId)
    }

    fun addCard(card: CardInfo) {
        val updatedCards = _uiState.value.cards + card
        _uiState.value = _uiState.value.copy(cards = updatedCards)
    }

    fun addBankAccount(account: BankAccount) {
        val updatedAccounts = _uiState.value.bankAccounts + account
        _uiState.value = _uiState.value.copy(bankAccounts = updatedAccounts)
    }

    fun addUpiMethod(upi: UpiMethod) {
        val updatedUpi = _uiState.value.upiMethods + upi
        _uiState.value = _uiState.value.copy(upiMethods = updatedUpi)
    }

    fun removeCard(cardId: String) {
        val updatedCards = _uiState.value.cards.filter { it.id != cardId }
        _uiState.value = _uiState.value.copy(cards = updatedCards)
    }

    fun setDefaultCard(cardId: String) {
        val updatedCards = _uiState.value.cards.map { card ->
            card.copy(isDefault = card.id == cardId)
        }
        _uiState.value = _uiState.value.copy(cards = updatedCards)
    }
}
