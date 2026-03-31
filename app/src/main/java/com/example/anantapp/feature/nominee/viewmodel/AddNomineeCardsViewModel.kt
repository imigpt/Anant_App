package com.example.anantapp.feature.nominee.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AddNomineeCardsResult {
    data object Idle : AddNomineeCardsResult()
    data object NavigateToAddNominee : AddNomineeCardsResult()
    data object NavigateToAddFamilyMember : AddNomineeCardsResult()
    data object NavigateToShareLocation : AddNomineeCardsResult()
    data object SkipAndClose : AddNomineeCardsResult()
    data class Error(val message: String) : AddNomineeCardsResult()
}

data class AddNomineeCardsState(
    val isLoading: Boolean = false,
    val messageText: String = ""
)

class AddNomineeCardsViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddNomineeCardsState())
    val state: StateFlow<AddNomineeCardsState> = _state.asStateFlow()

    private val _result = MutableStateFlow<AddNomineeCardsResult>(AddNomineeCardsResult.Idle)
    val result: StateFlow<AddNomineeCardsResult> = _result.asStateFlow()

    fun onAddNomineeClick() {
        _result.value = AddNomineeCardsResult.NavigateToAddNominee
    }

    fun onAddFamilyMemberClick() {
        _result.value = AddNomineeCardsResult.NavigateToAddFamilyMember
    }

    fun onShareLocationClick() {
        _result.value = AddNomineeCardsResult.NavigateToShareLocation
    }

    fun onSkipClick() {
        _result.value = AddNomineeCardsResult.SkipAndClose
    }

    fun resetResult() {
        _result.value = AddNomineeCardsResult.Idle
    }

    fun updateMessage(message: String) {
        _state.value = _state.value.copy(messageText = message)
    }
}
