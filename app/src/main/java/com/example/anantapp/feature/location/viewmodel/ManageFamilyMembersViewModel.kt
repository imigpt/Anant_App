package com.example.anantapp.feature.location.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.Serializable

// Data class for managing family members
data class ManageFamilyMembersUiState(
    val familyMembers: List<FamilyMemberData> = emptyList(),
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) : Serializable

data class FamilyMemberData(
    val id: String = "",
    val name: String = "",
    val relationship: String = "",
    val phoneNumber: String = ""
) : Serializable

// Result sealed class for family member operations
sealed class ManageFamilyMembersResult {
    object Idle : ManageFamilyMembersResult()
    object Loading : ManageFamilyMembersResult()
    data class Success(val message: String = "Operation completed") : ManageFamilyMembersResult()
    data class Error(val message: String) : ManageFamilyMembersResult()
}

class ManageFamilyMembersViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(
        ManageFamilyMembersUiState(
            familyMembers = listOf(
                FamilyMemberData(id = "1", name = "Wife", relationship = "Spouse", phoneNumber = "+91-9876543210"),
                FamilyMemberData(id = "2", name = "Son", relationship = "Child", phoneNumber = "+91-9876543211"),
                FamilyMemberData(id = "3", name = "Mother", relationship = "Parent", phoneNumber = "+91-9876543212"),
                FamilyMemberData(id = "4", name = "Brother", relationship = "Sibling", phoneNumber = "+91-9876543213")
            )
        )
    )
    val state: StateFlow<ManageFamilyMembersUiState> = _state.asStateFlow()
    
    private val _operationResult = MutableStateFlow<ManageFamilyMembersResult>(ManageFamilyMembersResult.Idle)
    val operationResult: StateFlow<ManageFamilyMembersResult> = _operationResult.asStateFlow()
    
    fun addMember(name: String, relationship: String, phoneNumber: String) {
        if (name.isBlank()) {
            _operationResult.value = ManageFamilyMembersResult.Error("Name cannot be empty")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Adding member...")
        
        val newMember = FamilyMemberData(
            id = (System.currentTimeMillis()).toString(),
            name = name,
            relationship = relationship,
            phoneNumber = phoneNumber
        )
        
        _state.value = _state.value.copy(
            familyMembers = _state.value.familyMembers + newMember,
            isLoading = false,
            successMessage = "$name added successfully"
        )
        
        _operationResult.value = ManageFamilyMembersResult.Success("$name added successfully")
    }
    
    fun editMember(memberId: String, name: String, relationship: String, phoneNumber: String) {
        if (name.isBlank()) {
            _operationResult.value = ManageFamilyMembersResult.Error("Name cannot be empty")
            return
        }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Updating member...")
        
        val updatedMembers = _state.value.familyMembers.map {
            if (it.id == memberId) {
                it.copy(name = name, relationship = relationship, phoneNumber = phoneNumber)
            } else {
                it
            }
        }
        
        _state.value = _state.value.copy(
            familyMembers = updatedMembers,
            isLoading = false,
            successMessage = "$name updated successfully"
        )
        
        _operationResult.value = ManageFamilyMembersResult.Success("$name updated successfully")
    }
    
    fun removeMember(memberId: String) {
        val memberToRemove = _state.value.familyMembers.find { it.id == memberId }
        
        _state.value = _state.value.copy(isLoading = true, loadingMessage = "Removing member...")
        
        val updatedMembers = _state.value.familyMembers.filter { it.id != memberId }
        
        _state.value = _state.value.copy(
            familyMembers = updatedMembers,
            isLoading = false,
            successMessage = "${memberToRemove?.name} removed successfully"
        )
        
        _operationResult.value = ManageFamilyMembersResult.Success("Member removed successfully")
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null,
            loadingMessage = null
        )
        _operationResult.value = ManageFamilyMembersResult.Idle
    }
}
