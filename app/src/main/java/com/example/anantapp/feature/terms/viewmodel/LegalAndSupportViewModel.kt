package com.example.anantapp.feature.terms.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class LegalAndSupportResult {
    data object Idle : LegalAndSupportResult()
    data object ReadFullTerms : LegalAndSupportResult()
    data object ViewPrivacyPolicy : LegalAndSupportResult()
    data object ContactSupport : LegalAndSupportResult()
    data object BrowseFAQs : LegalAndSupportResult()
    data object NavigateHome : LegalAndSupportResult()
    data object AcceptTerms : LegalAndSupportResult()
    data class Error(val message: String) : LegalAndSupportResult()
}

data class LegalAndSupportState(
    val termsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    val messageText: String = ""
)

class LegalAndSupportViewModel : ViewModel() {
    private val _state = MutableStateFlow(LegalAndSupportState())
    val state: StateFlow<LegalAndSupportState> = _state.asStateFlow()

    private val _result = MutableStateFlow<LegalAndSupportResult>(LegalAndSupportResult.Idle)
    val result: StateFlow<LegalAndSupportResult> = _result.asStateFlow()

    fun onReadFullTermsClick() {
        _result.value = LegalAndSupportResult.ReadFullTerms
    }

    fun onViewPrivacyPolicyClick() {
        _result.value = LegalAndSupportResult.ViewPrivacyPolicy
    }

    fun onContactSupportClick() {
        _result.value = LegalAndSupportResult.ContactSupport
    }

    fun onBrowseFAQsClick() {
        _result.value = LegalAndSupportResult.BrowseFAQs
    }

    fun onHomeClick() {
        _result.value = LegalAndSupportResult.NavigateHome
    }

    fun onAcceptTermsClick() {
        _state.value = _state.value.copy(termsAccepted = true)
        _result.value = LegalAndSupportResult.AcceptTerms
    }

    fun resetResult() {
        _result.value = LegalAndSupportResult.Idle
    }

    fun updateMessage(message: String) {
        _state.value = _state.value.copy(messageText = message)
    }

    fun setLoading(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }
}
