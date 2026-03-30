package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FamilyMemberItem(
    val id: String,
    val name: String,
    val relation: String = ""
)

data class ManageFamilyMembersState(
    val familyMembers: List<FamilyMemberItem> = listOf(
        FamilyMemberItem("1", "Kamla", "Mother"),
        FamilyMemberItem("2", "Raju", "Brother"),
        FamilyMemberItem("3", "Vimla", "Sister"),
        FamilyMemberItem("4", "Bunty", "Son")
    ),
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class ManageFamilyMembersViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(ManageFamilyMembersState())
    val state: StateFlow<ManageFamilyMembersState> = _state.asStateFlow()
    
    fun addFamilyMember(name: String, relation: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                val newMember = FamilyMemberItem(
                    id = _state.value.familyMembers.size.toString(),
                    name = name,
                    relation = relation
                )
                
                _state.value = _state.value.copy(
                    familyMembers = _state.value.familyMembers + newMember,
                    successMessage = "Family member added successfully",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun editFamilyMember(memberId: String, newName: String, newRelation: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                val updatedMembers = _state.value.familyMembers.map { member ->
                    if (member.id == memberId) {
                        member.copy(name = newName, relation = newRelation)
                    } else {
                        member
                    }
                }
                
                _state.value = _state.value.copy(
                    familyMembers = updatedMembers,
                    successMessage = "Family member updated successfully",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun deleteFamilyMember(memberId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            try {
                kotlinx.coroutines.delay(800)
                
                val updatedMembers = _state.value.familyMembers.filter { it.id != memberId }
                
                _state.value = _state.value.copy(
                    familyMembers = updatedMembers,
                    successMessage = "Family member removed successfully",
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = e.message ?: "An error occurred",
                    isLoading = false
                )
            }
        }
    }
    
    fun clearMessages() {
        _state.value = _state.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }
}
