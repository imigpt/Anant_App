package com.example.anantapp.data.repository

import com.example.anantapp.data.model.VerifyBankResult
import kotlinx.coroutines.delay

/**
 * Repository interface for bank verification operations
 */
interface IVerifyBankRepository {
    suspend fun validateBankAccount(
        firstName: String,
        lastName: String,
        birthName: String,
        accountNumber: String,
        ifscCode: String
    ): VerifyBankResult

    suspend fun submitBankVerification(
        firstName: String,
        lastName: String,
        birthName: String,
        accountNumber: String,
        ifscCode: String,
        accountType: String
    ): VerifyBankResult
}

/**
 * Repository implementation for bank verification
 */
class VerifyBankRepository : IVerifyBankRepository {

    override suspend fun validateBankAccount(
        firstName: String,
        lastName: String,
        birthName: String,
        accountNumber: String,
        ifscCode: String
    ): VerifyBankResult {
        return try {
            delay(1500)

            // Validate all fields
            if (firstName.isBlank() || lastName.isBlank() || birthName.isBlank() ||
                accountNumber.isBlank() || ifscCode.isBlank()
            ) {
                VerifyBankResult.Error("Please fill all fields")
            } else if (accountNumber.length < 10) {
                VerifyBankResult.Error("Invalid account number")
            } else if (ifscCode.length != 11) {
                VerifyBankResult.Error("Invalid IFSC code (must be 11 characters)")
            } else {
                VerifyBankResult.Success("Bank account validated")
            }
        } catch (e: Exception) {
            VerifyBankResult.Error(e.message ?: "Error validating bank account")
        }
    }

    override suspend fun submitBankVerification(
        firstName: String,
        lastName: String,
        birthName: String,
        accountNumber: String,
        ifscCode: String,
        accountType: String
    ): VerifyBankResult {
        return try {
            delay(2000)

            // Validate all required fields
            val firstNameTrimmed = firstName.trim()
            val lastNameTrimmed = lastName.trim()
            val accountNumberTrimmed = accountNumber.trim()
            val ifscCodeTrimmed = ifscCode.trim()
            
            if (firstNameTrimmed.isEmpty()) {
                return VerifyBankResult.Error("First name is required")
            }
            if (lastNameTrimmed.isEmpty()) {
                return VerifyBankResult.Error("Last name is required")
            }
            if (accountNumberTrimmed.isEmpty()) {
                return VerifyBankResult.Error("Account number is required")
            }
            if (ifscCodeTrimmed.length < 11) {
                return VerifyBankResult.Error("IFSC code must be 11 characters")
            }
            
            VerifyBankResult.Success("Bank verification submitted successfully")
        } catch (e: Exception) {
            VerifyBankResult.Error(e.message ?: "Error submitting bank verification")
        }
    }
}
