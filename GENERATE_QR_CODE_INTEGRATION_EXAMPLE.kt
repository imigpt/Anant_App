/**
 * SAMPLE INTEGRATION GUIDE
 * 
 * This file shows example code for integrating GenerateQRCodeScreen into your app.
 * Copy the relevant sections into your navigation and activity files.
 */

// ============================================================================
// 1. ADD TO YOUR NAVIGATION FILE (e.g., FundraiserNavigation.kt)
// ============================================================================

// Add this import at the top:
// import com.example.anantapp.presentation.screen.GenerateQRCodeScreen
// import com.example.anantapp.presentation.screen.QRCodeFormState

// Add this composable in your NavHost:
composable("generateQRCode") {
    GenerateQRCodeScreen(
        onBackClick = {
            navController.popBackStack()
        },
        onNextClick = { formData ->
            // Handle the form submission
            handleQRCodeFormSubmission(formData)
            
            // Navigate to next screen
            navController.navigate("qrCodePreview") {
                // Optional: Clear back stack
                // popUpTo("generateQRCode") { inclusive = true }
            }
        }
    )
}

// ============================================================================
// 2. ADD A HANDLER FUNCTION
// ============================================================================

private fun handleQRCodeFormSubmission(formData: QRCodeFormState) {
    // Log the data
    println("QR Form Submitted:")
    println("Full Name: ${formData.fullName}")
    println("Vehicle Type: ${formData.selectedVehicleType}")
    println("Medical Conditions: ${formData.selectedMedicalConditions}")
    
    // Validate data
    if (formData.fullName.isEmpty()) {
        // Show error
        return
    }
    
    // Save to local database or send to API
    // Example: viewModel.saveQRCodeData(formData)
    
    // You can also:
    // - Generate QR code from the data
    // - Encrypt and store the data
    // - Display a preview screen
}

// ============================================================================
// 3. ADD NAVIGATION ROUTE
// ============================================================================

// In your sealed class for navigation routes:
sealed class Screen {
    data object GenerateQRCode : Screen()
    data object QRCodePreview : Screen()
    // ... other screens
    
    companion object {
        fun toRoute(screen: Screen): String = when (screen) {
            is GenerateQRCode -> "generateQRCode"
            is QRCodePreview -> "qrCodePreview"
            // ... other routes
        }
    }
}

// Navigate to the screen:
navController.navigate(Screen.toRoute(Screen.GenerateQRCode))

// ============================================================================
// 4. SAMPLE VIEWMODEL INTEGRATION
// ============================================================================

class QRCodeManagerViewModel : ViewModel() {
    private val generateQRViewModel = GenerateQRCodeViewModel()
    
    fun saveQRCodeData(formData: QRCodeFormState) {
        viewModelScope.launch {
            try {
                // Validate the form
                if (!validateQRCodeForm(formData)) {
                    return@launch
                }
                
                // Save to database
                // repository.saveQRCodeData(formData)
                
                // Generate QR code
                val qrCodeBitmap = generateQRCode(formData)
                
                // Store the result
                // _qrCodeResult.value = qrCodeBitmap
                
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
    
    private fun validateQRCodeForm(formData: QRCodeFormState): Boolean {
        return formData.fullName.isNotEmpty() &&
                formData.selectedVehicleType != null &&
                formData.vehicleNumberPlate.isNotEmpty() &&
                formData.emergencyContactFamilyName.isNotEmpty() &&
                formData.emergencyContactFamilyPhone.isNotEmpty()
    }
    
    private fun generateQRCode(formData: QRCodeFormState): Bitmap {
        // Use a QR code library like ZXing or QRGen
        val qrContent = formData.toJsonString()
        // Generate QR code bitmap from qrContent
        return createQRCodeBitmap(qrContent)
    }
}

// ============================================================================
// 5. EXTENSION FUNCTION TO CONVERT FORM TO JSON
// ============================================================================

fun QRCodeFormState.toJsonString(): String {
    val json = """
        {
            "fullName": "$fullName",
            "vehicleType": "$selectedVehicleType",
            "vehicleNumberPlate": "$vehicleNumberPlate",
            "insurancePolicyNumber": "$insurancePolicyNumber",
            "insuranceValidTill": "$insuranceValidTill",
            "emergencyContactFamily": {
                "name": "$emergencyContactFamilyName",
                "phone": "$emergencyContactFamilyPhone"
            },
            "emergencyContactFriend": {
                "name": "$emergencyContactFriendName",
                "phone": "$emergencyContactFriendPhone"
            },
            "medicalConditions": ${selectedMedicalConditions.toList()}
        }
    """.trimIndent()
    return json
}

// ============================================================================
// 6. ADD TO MAIN ACTIVITY IF USING DIRECT COMPOSITION
// ============================================================================

// In MainActivity.kt MainContent:
val currentScreen = remember { mutableStateOf("generateQRCode") }

when (currentScreen.value) {
    "generateQRCode" -> {
        GenerateQRCodeScreen(
            onBackClick = {
                // Handle back
            },
            onNextClick = { formData ->
                currentScreen.value = "nextScreen"
            }
        )
    }
}

// ============================================================================
// 7. ADD PERMISSIONS TO ANDROIDMANIFEST.XML
// ============================================================================

<!-- Add these permissions to your AndroidManifest.xml -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<!-- For Android 10+ -->
<uses-permission android:name="android.permission.ACCESS_MEDIA_LOCATION" />

// ============================================================================
// 8. REQUEST RUNTIME PERMISSIONS (Activity or Composable)
// ============================================================================

// Use Accompanist Permissions or AndroidX Permissions
val permissionsState = rememberPermissionState(
    android.Manifest.permission.CAMERA
)

LaunchedEffect(Unit) {
    if (!permissionsState.status.isGranted) {
        permissionsState.launchPermissionRequest()
    }
}

// ============================================================================
// 9. HANDLE CAMERA INTENT (IF NEEDED)
// ============================================================================

// Create temporary file for camera image:
private fun createImageFile(): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

// ============================================================================
// 10. SAMPLE API CALL FOR FORM SUBMISSION
// ============================================================================

suspend fun submitQRCodeForm(formData: QRCodeFormState) {
    try {
        val response = apiService.submitQRCode(
            SubmitQRCodeRequest(
                fullName = formData.fullName,
                vehicleType = formData.selectedVehicleType,
                vehicleNumberPlate = formData.vehicleNumberPlate,
                insurancePolicyNumber = formData.insurancePolicyNumber,
                insuranceValidTill = formData.insuranceValidTill,
                emergencyContactFamilyName = formData.emergencyContactFamilyName,
                emergencyContactFamilyPhone = formData.emergencyContactFamilyPhone,
                emergencyContactFriendName = formData.emergencyContactFriendName,
                emergencyContactFriendPhone = formData.emergencyContactFriendPhone,
                medicalConditions = formData.selectedMedicalConditions.toList(),
                photoPath = formData.uploadedPhotoPath,
                rcPath = formData.uploadedRCPath,
                insurancePath = formData.uploadedInsurancePath
            )
        )
        
        if (response.isSuccessful) {
            // Handle success
            Log.d("QRCode", "Form submitted successfully")
        }
    } catch (e: Exception) {
        // Handle error
        Log.e("QRCode", "Error submitting form", e)
    }
}

data class SubmitQRCodeRequest(
    val fullName: String,
    val vehicleType: String?,
    val vehicleNumberPlate: String,
    val insurancePolicyNumber: String,
    val insuranceValidTill: String,
    val emergencyContactFamilyName: String,
    val emergencyContactFamilyPhone: String,
    val emergencyContactFriendName: String,
    val emergencyContactFriendPhone: String,
    val medicalConditions: List<String>,
    val photoPath: String?,
    val rcPath: String?,
    val insurancePath: String?
)

// ============================================================================
// USAGE NOTES:
// ============================================================================
/*
1. Import the screen into your navigation file:
   import com.example.anantapp.presentation.screen.GenerateQRCodeScreen

2. Add the composable route to your NavHost

3. Configure callbacks for onBackClick and onNextClick

4. Implement form validation as needed

5. Add proper error handling and loading states

6. Request runtime permissions for camera and file access

7. Consider using a repository pattern for data persistence

8. Implement proper logging for debugging

9. Add analytics tracking for form submission

10. Test on different screen sizes and orientations
*/
