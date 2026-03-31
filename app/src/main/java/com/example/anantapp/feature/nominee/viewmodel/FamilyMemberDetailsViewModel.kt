package com.example.anantapp.feature.nominee.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class FamilyMemberDetailsResult {
    data object Idle : FamilyMemberDetailsResult()
    data object SubmitSuccess : FamilyMemberDetailsResult()
    data object GoBack : FamilyMemberDetailsResult()
    data class Error(val message: String) : FamilyMemberDetailsResult()
    data object Loading : FamilyMemberDetailsResult()
}

data class FamilyMemberDetailsState(
    val fullName: String = "",
    val relationToUser: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val mobileNumber: String = "",
    val emailId: String = "",
    val houseNo: String = "",
    val areaStreet: String = "",
    val city: String = "",
    val pinCode: String = "",
    val selectedIdProof: String = "Aadhaar", // Aadhaar, PAN, Passport, Driving License
    val idNumber: String = "",
    val isIdProofUploaded: Boolean = false,
    val uploadedFrontFileName: String = "",
    val uploadedBackFileName: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

class FamilyMemberDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(FamilyMemberDetailsState())
    val state: StateFlow<FamilyMemberDetailsState> = _state.asStateFlow()

    private val _result = MutableStateFlow<FamilyMemberDetailsResult>(FamilyMemberDetailsResult.Idle)
    val result: StateFlow<FamilyMemberDetailsResult> = _result.asStateFlow()

    fun updateFullName(name: String) {
        _state.value = _state.value.copy(fullName = name.take(100))
    }

    fun updateRelationToUser(relation: String) {
        _state.value = _state.value.copy(relationToUser = relation)
    }

    fun updateDateOfBirth(dob: String) {
        _state.value = _state.value.copy(dateOfBirth = dob)
    }

    fun updateGender(gender: String) {
        _state.value = _state.value.copy(gender = gender)
    }

    fun updateMobileNumber(phone: String) {
        val filteredPhone = phone.filter { it.isDigit() }.take(10)
        _state.value = _state.value.copy(mobileNumber = filteredPhone)
    }

    fun updateEmailId(email: String) {
        _state.value = _state.value.copy(emailId = email)
    }

    fun updateHouseNo(houseNo: String) {
        _state.value = _state.value.copy(houseNo = houseNo.take(100))
    }

    fun updateAreaStreet(area: String) {
        _state.value = _state.value.copy(areaStreet = area.take(100))
    }

    fun updateCity(city: String) {
        _state.value = _state.value.copy(city = city.take(50))
    }

    fun updatePinCode(pin: String) {
        val filteredPin = pin.filter { it.isDigit() }.take(6)
        _state.value = _state.value.copy(pinCode = filteredPin)
    }

    fun updateSelectedIdProof(idType: String) {
        _state.value = _state.value.copy(selectedIdProof = idType, idNumber = "")
    }

    fun updateIdNumber(idNum: String) {
        // Format based on ID type
        val formatted = when (_state.value.selectedIdProof) {
            "Aadhaar" -> formatAadhar(idNum)
            "PAN" -> idNum.uppercase().take(10)
            else -> idNum
        }
        _state.value = _state.value.copy(idNumber = formatted)
    }

    fun handleIdProofUpload(frontFile: String, backFile: String) {
        _state.value = _state.value.copy(
            isIdProofUploaded = true,
            uploadedFrontFileName = frontFile,
            uploadedBackFileName = backFile,
            errorMessage = ""
        )
    }

    fun submitForm() {
        if (!validateForm()) {
            return
        }

        _state.value = _state.value.copy(isLoading = true)
        _result.value = FamilyMemberDetailsResult.Loading

        // Simulate API call
        _state.value = _state.value.copy(isLoading = false)
        _result.value = FamilyMemberDetailsResult.SubmitSuccess
    }

    fun goBack() {
        _result.value = FamilyMemberDetailsResult.GoBack
    }

    private fun validateForm(): Boolean {
        val state = _state.value

        return when {
            state.fullName.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("Full name is required")
                false
            }
            state.relationToUser.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("Relation to user is required")
                false
            }
            state.dateOfBirth.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("Date of birth is required")
                false
            }
            state.gender.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("Gender is required")
                false
            }
            state.mobileNumber.length != 10 -> {
                _result.value = FamilyMemberDetailsResult.Error("Mobile number must be 10 digits")
                false
            }
            !isValidEmail(state.emailId) -> {
                _result.value = FamilyMemberDetailsResult.Error("Please enter a valid email")
                false
            }
            state.houseNo.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("House number is required")
                false
            }
            state.areaStreet.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("Area/Street is required")
                false
            }
            state.city.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("City is required")
                false
            }
            state.pinCode.length != 6 -> {
                _result.value = FamilyMemberDetailsResult.Error("Pin code must be 6 digits")
                false
            }
            state.idNumber.isBlank() -> {
                _result.value = FamilyMemberDetailsResult.Error("ID number is required")
                false
            }
            !state.isIdProofUploaded -> {
                _result.value = FamilyMemberDetailsResult.Error("Please upload ID proof")
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        if (email.isBlank()) return false
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
    }

    private fun formatAadhar(aadhar: String): String {
        // Remove non-digits
        val digits = aadhar.filter { it.isDigit() }
        if (digits.length <= 4) return digits
        if (digits.length <= 8) return "${digits.substring(0, 4)}-${digits.substring(4)}"
        return "${digits.substring(0, 4)}-${digits.substring(4, 8)}-${digits.substring(8).take(4)}"
    }

    fun resetResult() {
        _result.value = FamilyMemberDetailsResult.Idle
    }
}
