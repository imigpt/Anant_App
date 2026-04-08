package com.example.anantapp.presentation.viewmodel

/**
 * State class for Donor Registration Screen
 */
data class DonorScreenState(
    val donorType: String = "individual",
    val fullName: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val panNumber: String = "",
    val houseNo: String = "",
    val street: String = "",
    val state: String = "",
    val city: String = "",
    val pincode: String = "",
    val employeeId: String = "",
    val department: String = "",
    val designation: String = "",
    val companyName: String = "",
    val gstNumber: String = "",
    val contactPersonName: String = "",
    val officialEmail: String = "",
    val donationAmount: String = "",
    val selectedQuickAmount: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)
