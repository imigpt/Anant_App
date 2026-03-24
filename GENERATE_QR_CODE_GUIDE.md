# Generate QR Code Screen Implementation Guide

## Overview

The `GenerateQRCodeScreen` is a comprehensive Jetpack Compose UI that implements a QR code generation form with multiple sections for collecting personal, vehicle, emergency contact, and medical information. The implementation follows Material 3 design principles and includes glassmorphic styling with a blue bubble background theme.

## File Structure

### Created Files:
1. **GenerateQRCodeScreen.kt** - Main composable and component functions
   - Location: `app/src/main/java/com/example/anantapp/presentation/screen/GenerateQRCodeScreen.kt`
   - Contains: Main screen, data class, and composable components

2. **GenerateQRCodeViewModel.kt** - State management
   - Location: `app/src/main/java/com/example/anantapp/presentation/viewmodel/GenerateQRCodeViewModel.kt`
   - Contains: ViewModel with state management methods

## Components

### Data Class: `QRCodeFormState`
```kotlin
data class QRCodeFormState(
    val fullName: String = "",
    val selectedVehicleType: String? = null,              // "2 Wheeler", "3 Wheeler Auto", "Other", "4 Wheeler Car"
    val vehicleNumberPlate: String = "",
    val insurancePolicyNumber: String = "",
    val insuranceValidTill: String = "",
    val emergencyContactFamilyName: String = "",
    val emergencyContactFamilyPhone: String = "",
    val emergencyContactFriendName: String = "",
    val emergencyContactFriendPhone: String = "",
    val selectedMedicalConditions: Set<String> = emptySet(),  // Multiple selections allowed
    val uploadedPhotoPath: String? = null,
    val uploadedRCPath: String? = null,
    val uploadedInsurancePath: String? = null
)
```

### Main Composable: `GenerateQRCodeScreen`

#### Signature:
```kotlin
@Composable
fun GenerateQRCodeScreen(
    viewModel: GenerateQRCodeViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNextClick: (QRCodeFormState) -> Unit = {}
)
```

#### Parameters:
- `viewModel`: ViewModel instance for state management
- `onBackClick`: Callback when back button is pressed
- `onNextClick`: Callback when Next button is clicked, receives filled form data

#### Usage Example:
```kotlin
// In your navigation or composable:
GenerateQRCodeScreen(
    onBackClick = { navController.popBackStack() },
    onNextClick = { formData ->
        // Handle the completed form data
        Log.d("QRCode", "Form data: $formData")
        // Navigate to next screen or submit data
        navController.navigate("next_screen")
    }
)
```

## Composable Components

### 1. **BackgroundBubbles()**
- Creates blue gradient background
- Should be paired with `Box(background(Color(0xFF0066CC)))`

### 2. **HeaderSection()**
- Displays QR code icon and "Generate QR Code" title
- Centered layout at the top of the form

### 3. **CustomInputField()**
```kotlin
@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
)
```
- Translucent text field with rounded corners
- Supports custom keyboard types
- Placeholder text styling included

### 4. **UploadPhotoSection()**
```kotlin
@Composable
fun UploadPhotoSection(
    hasPhoto: Boolean,
    onTakeSelfieClick: () -> Unit,
    onChooseFromGalleryClick: () -> Unit
)
```
- Displays photo upload options
- Two buttons: "Take Selfie" and "Choose From Gallery"
- Handles file/camera intents

### 5. **VehicleTypeSection()**
```kotlin
@Composable
fun VehicleTypeSection(
    selectedType: String?,
    onTypeSelected: (String) -> Unit
)
```
- Selectable chips for vehicle types
- Selected chip: Blue background with black text
- Unselected chip: Translucent white/light-blue

### 6. **VehicleDetailsSection()**
- Three stacked input fields:
  - Vehicle Number Plate
  - Insurance Policy Number
  - Insurance Valid Till

### 7. **UploadDocumentSection()**
```kotlin
@Composable
fun UploadDocumentSection(
    title: String,
    description: String,
    onUploadClick: () -> Unit,
    isUploaded: Boolean = false
)
```
- Reusable card for document uploads
- Used for RC Copy and Insurance Copy
- Shows upload status

### 8. **EmergencyContactSection()**
```kotlin
@Composable
fun EmergencyContactSection(
    title: String,
    fullName: String,
    onNameChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit
)
```
- Two input fields: Full Name and Phone Number
- Used twice: for Family and Friend emergency contacts

### 9. **MedicalConditionSection()**
- Displays selectable chips for medical conditions
- Supports multiple selections
- Medical conditions include:
  - Diabetes
  - High Blood Pressure
  - Asthma
  - Heart Condition
  - Epilepsy/Seizures
  - Severe Allergies
  - Organ Transplant
  - Blood Clotting Disorder
  - Currently Pregnant
  - Other

### 10. **SelectableChip()**
```kotlin
@Composable
fun SelectableChip(
    label: String,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)
```
- Reusable chip component for selections
- Blue background when selected
- Translucent background when unselected

## ViewModel Methods

The `GenerateQRCodeViewModel` provides the following methods:

```kotlin
// Update individual fields
updateFullName(fullName: String)
updateVehicleType(vehicleType: String)
updateVehicleNumberPlate(plate: String)
updateInsurancePolicyNumber(policyNumber: String)
updateInsuranceValidTill(validTill: String)
updateEmergencyContactFamilyName(name: String)
updateEmergencyContactFamilyPhone(phone: String)
updateEmergencyContactFriendName(name: String)
updateEmergencyContactFriendPhone(phone: String)

// Handle selections
toggleMedicalCondition(condition: String)

// Handle uploads
updatePhotoPath(photoPath: String)
updateRCPath(rcPath: String)
updateInsurancePath(insurancePath: String)

// Form management
resetForm()                    // Clear all data
getFormData(): QRCodeFormState  // Get current state
```

## Styling Details

### Colors:
- **Primary Blue**: `Color(0xFF0066CC)` - Background
- **Glass Card**: `Color.White.copy(alpha = 0.6f)` - Main content area
- **Input Fields**: `Color(0xFFE8F0FF).copy(alpha = 0.6f)` - Input backgrounds
- **Selected Chip**: `Color(0xFF4A90E2)` - Active selection
- **Text Primary**: `Color(0xFF000000)` - Headers
- **Text Secondary**: `Color(0xFF666666)` - Descriptions

### Shapes:
- **Main Card**: `RoundedCornerShape(24.dp)` - Glassmorphic card
- **Input Fields**: `RoundedCornerShape(16.dp)` - Text field corners
- **Buttons**: `RoundedCornerShape(8.dp)` or `RoundedCornerShape(24.dp)` - Chip style
- **Chips**: `RoundedCornerShape(24.dp)` - Pill-shaped buttons

### Typography:
- **Title**: 24.sp, Bold, Black
- **Section Headers**: 14.sp, SemiBold, Black
- **Input Placeholder**: 14.sp, Gray
- **Button Text**: 12-16.sp, SemiBold

## Integration Steps

### 1. Add to Navigation Graph:
```kotlin
// In your navigation setup
composable("generateQRCode") {
    GenerateQRCodeScreen(
        onBackClick = { navController.popBackStack() },
        onNextClick = { formData ->
            // Save form data and navigate
            viewModel.saveQRFormData(formData)
            navController.navigate("nextScreen")
        }
    )
}
```

### 2. Add Required Dependencies:
Already included in the project:
- `androidx.compose.material3`
- `androidx.compose.ui`
- `androidx.lifecycle:lifecycle-viewmodel-compose`
- `coil:coil-compose` (for image loading)

### 3. Handle File Permissions:
Add to `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

## State Management

The screen uses Material3's state management:

```kotlin
// In ViewModel:
val formState: StateFlow<QRCodeFormState> = _formState.asStateFlow()

// In Composable:
val formState by viewModel.formState.collectAsState()
```

This ensures proper recomposition and state persistence.

## Glassmorphism Implementation

The glassmorphic effect is achieved through:

1. **Background Box**: Blue gradient background
2. **Main Card**: 
   - White background with 60% opacity
   - 24.dp rounded corners
   - Elevated with shadow
3. **Input Fields**: 
   - Translucent light-blue backgrounds
   - No outlines or underlines
   - Rounded corners

## File Upload Handling

The screen uses three separate file launchers for better control:

```kotlin
// Photo launcher - image files only
val photoLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.PickVisualMedia()
) { uri ->
    uri?.let { viewModel.updatePhotoPath(it.toString()) }
}

// RC document launcher - all file types
val rcFileLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.GetContent()
) { uri ->
    uri?.let { viewModel.updateRCPath(it.toString()) }
}

// Insurance document launcher - all file types
val insuranceFileLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.GetContent()
) { uri ->
    uri?.let { viewModel.updateInsurancePath(it.toString()) }
}
```

## Tips & Best Practices

1. **Validation**: Add form validation before calling `onNextClick`
2. **Loading States**: Add loading indicators during file uploads
3. **Error Handling**: Implement try-catch for file operations
4. **Accessibility**: Consider adding content descriptions to all icons
5. **Testing**: Test with various screen sizes and orientations
6. **Camera Support**: Ensure camera intent is properly handled

## Troubleshooting

### Screen layout issues:
- Ensure Box background color is set correctly
- Verify Card padding and modifier chain

### Input field not showing:
- Check if CustomInputField modifier includes `fillMaxWidth()`
- Verify height is sufficient (52.dp default)

### Chips not selectable:
- Ensure `clickable` modifier is properly applied
- Check MutableInteractionSource is created with `remember`

### File upload not working:
- Verify permissions in AndroidManifest.xml
- Check if launcher is properly initialized at composition time
- Ensure URI handling is correct in ViewModel

## Future Enhancements

1. Add real background image asset instead of gradient
2. Implement form validation with error messages
3. Add animations on section transitions
4. Implement image preview after upload
5. Add QR code generation preview
6. Implement auto-save functionality
7. Add accessibility features (TalkBack support)
