package com.example.anantapp.feature.verify.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class InsurancePolicyData(
    val policyNumber: String = "",
    val accidentAmount: String = "",
    val deathAmount: String = "",
    val disabilityAmount: String = "",
    val insurerDetails: String = ""
)

data class DeclareInsuranceUiState(
    val policies: List<InsurancePolicyData> = listOf(InsurancePolicyData()),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

sealed class DeclareInsuranceResult {
    object Idle : DeclareInsuranceResult()
    data class Success(val message: String) : DeclareInsuranceResult()
    data class Error(val message: String) : DeclareInsuranceResult()
}

class DeclareInsuranceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DeclareInsuranceUiState())
    val uiState: StateFlow<DeclareInsuranceUiState> = _uiState.asStateFlow()

    fun updatePolicyField(policyIndex: Int, fieldName: String, value: String) {
        val updatedPolicies = _uiState.value.policies.toMutableList()
        val currentPolicy = updatedPolicies[policyIndex]
        
        updatedPolicies[policyIndex] = when (fieldName) {
            "policyNumber" -> currentPolicy.copy(policyNumber = value)
            "accidentAmount" -> currentPolicy.copy(accidentAmount = value)
            "deathAmount" -> currentPolicy.copy(deathAmount = value)
            "disabilityAmount" -> currentPolicy.copy(disabilityAmount = value)
            "insurerDetails" -> currentPolicy.copy(insurerDetails = value)
            else -> currentPolicy
        }
        
        _uiState.value = _uiState.value.copy(policies = updatedPolicies)
    }

    fun addPolicy() {
        val updatedPolicies = _uiState.value.policies.toMutableList()
        updatedPolicies.add(InsurancePolicyData())
        _uiState.value = _uiState.value.copy(policies = updatedPolicies)
    }

    fun removePolicy(policyIndex: Int) {
        val updatedPolicies = _uiState.value.policies.toMutableList()
        if (updatedPolicies.size > 1) {
            updatedPolicies.removeAt(policyIndex)
            _uiState.value = _uiState.value.copy(policies = updatedPolicies)
        }
    }

    fun submitInsuranceDetails() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        // Simulate API call and set success message
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            successMessage = "Insurance details submitted successfully"
        )
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            error = null
        )
    }
}
