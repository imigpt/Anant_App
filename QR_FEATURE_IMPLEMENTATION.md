# QR Feature Module Implementation

## Overview
Complete QR code feature module with 7 screens and 7 ViewModels implementing the full QR sticker ordering flow.

## Package Structure
```
com.example.anantapp.feature.qr
├── presentation
│   ├── screens/        (7 screens)
│   │   ├── GenerateQRCodeScreen.kt
│   │   ├── ViewQRCodeScreen.kt
│   │   ├── QRCodeScannerScreen.kt
│   │   ├── UserDetailsScreen.kt
│   │   ├── DeliveryAddressScreen.kt
│   │   ├── OrderStatusScreen.kt
│   │   └── OrderSuccessScreen.kt
│   └── viewmodel/      (7 ViewModels)
│       ├── GenerateQRCodeViewModel.kt
│       ├── ViewQRCodeViewModel.kt
│       ├── QRCodeScannerViewModel.kt
│       ├── UserDetailsViewModel.kt
│       ├── DeliveryAddressViewModel.kt
│       ├── OrderStatusViewModel.kt
│       └── OrderSuccessViewModel.kt
```

## Screens Implementation

### 1. GenerateQRCodeScreen
**Purpose**: Collect vehicle and insurance information for QR code generation

**Form Fields**:
- Full Name * (text input)
- Vehicle Type * (chip selector: 2 Wheeler, 4 Wheeler, Other)
- Vehicle Number Plate * (text input)
- Insurance Policy Number * (text input)
- Insurance Valid Till * (date input: MM/DD/YYYY)
- Emergency Contact Name * (text input)
- Emergency Contact Phone * (phone input)

**UI Components**:
- Back button header
- QRFormLabel (reusable form label)
- QRInputField (reusable input component with gradient border)
- Vehicle type chips with gradient selection
- "Generate QR Code" action button

**ViewModel**: GenerateQRCodeViewModel
- Manages 7 update methods for each field
- selectedVehicleType tracked for chip state

---

### 2. ViewQRCodeScreen
**Purpose**: Display generated QR code with sharing and download options

**Features**:
- QR code display placeholder
- Share QR button (outline style)
- Download QR button (gradient style)
- Decorative background circles
- Clean card-based layout

**UI Components**:
- Decorative blue circles (top-right, bottom-left)
- White card container with rounded corners
- QR code display area (bordered rectangle)
- Action button row with two buttons

**ViewModel**: ViewQRCodeViewModel
- shareQRCode() method
- downloadQRCode() method
- Result sealed class for async operations

---

### 3. QRCodeScannerScreen
**Purpose**: Enable camera-based QR code scanning with ML Kit

**Features**:
- Camera preview placeholder
- Full-screen scanning interface
- Scanning instructions text
- Security message with lock icon
- Start Scanning button

**UI Components**:
- Decorative blue circles (top-left, bottom-right)
- White card container
- Black camera preview box with blue border
- Security badge with lock icon
- "Start Scanning" action button

**ViewModel**: QRCodeScannerViewModel
- startScanning() method
- stopScanning() method
- onQRCodeScanned(qrCode: String) method
- Manages scanning state (isScanning, scannedQRCode, error)

---

### 4. UserDetailsScreen
**Purpose**: Collect personal details and medical information

**Form Fields**:
- Full Name * (text input)
- Email Address * (email input)
- Phone Number * (phone input)
- Blood Type (chip selector: O+, O-, A+, A-, B+, B-, AB+, AB-)
- Emergency Contact Name * (text input)
- Emergency Contact Phone * (phone input)
- Address (text input)

**UI Components**:
- Back button header
- QRFormLabel and QRInputField components
- Blood type chip selector (8 options)
- Form fields with validation markers (*)
- "Continue" action button

**ViewModel**: UserDetailsViewModel
- 7 update methods for all fields
- Blood type selection state management
- Emergency contact tracking

---

### 5. DeliveryAddressScreen
**Purpose**: Collect shipping address information for QR sticker delivery

**Form Fields**:
- Full Name * (text input)
- House Number / Building Name * (text input)
- Street / Locality * (text input)
- City * (text input)
- State * (text input)
- Pincode * (numeric input)
- Mobile Number * (phone input)

**UI Components**:
- Back button header
- QRFormLabel and QRInputField components
- 7 address form fields
- "Continue" action button

**ViewModel**: DeliveryAddressViewModel
- 7 update methods for address fields
- Address data state management

---

### 6. OrderStatusScreen
**Purpose**: Display order tracking information and status

**Display Elements**:
- Order ID display
- Order status tracker
- Estimated delivery date
- Delivery address preview

**UI Components**:
- Decorative gradient border card
- Shipping icon (Material Icon)
- Status information boxes
- Two action buttons:
  - Track Order (gradient, filled)
  - Download QR Sticker (outline)

**ViewModel**: OrderStatusViewModel
- trackOrder() method
- downloadQR() method
- OrderStatusResult sealed class for tracking events
- Status state management

---

### 7. OrderSuccessScreen
**Purpose**: Confirmation screen after successful QR sticker order

**Display Elements**:
- Success checkmark icon in circle (green gradient)
- Order confirmation message
- Order ID
- Delivery address details
- Estimated delivery date
- Order details card

**UI Components**:
- Full-screen centered layout
- Green success checkmark (circular background)
- Order details card (gray background)
- Three action buttons:
  - Download Invoice PDF (green gradient)
  - View Order Status (gray)
  - Back to Home (outline text)

**ViewModel**: OrderSuccessViewModel
- downloadPDF() method
- viewOrderStatus() method
- OrderSuccessResult sealed class for async operations

---

## Shared Components (Reusable)

### QRFormLabel
```kotlin
@Composable
fun QRFormLabel(text: String)
```
- Displays form field labels with semi-bold weight
- Font size: 14sp
- Color: #333333

### QRInputField
```kotlin
@Composable
fun QRInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    mainGradient: Brush,
    keyboardType: KeyboardType = KeyboardType.Text
)
```
- Bordered input field with gradient border
- Height: 52dp
- Border radius: 12dp
- Supports custom keyboard types
- Gradient border matches main theme color

---

## ViewModel Patterns

All ViewModels follow the established pattern:
1. **State Data Class** - Composable UI state with defaults
2. **MutableStateFlow** - Private state holder
3. **StateFlow** - Public immutable state exposure
4. **Update Methods** - Direct state mutation methods
5. **Result Sealed Class** (where needed) - For async operations

### Example ViewModel Structure
```kotlin
data class ScreenUiState(
    val field1: String = "",
    val field2: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ScreenResult {
    object Idle : ScreenResult()
    data class Success(val data: String) : ScreenResult()
    data class Error(val message: String) : ScreenResult()
}

class ScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ScreenUiState())
    val uiState: StateFlow<ScreenUiState> = _uiState.asStateFlow()

    private val _result = MutableSharedFlow<ScreenResult>()
    val result: SharedFlow<ScreenResult> = _result.asSharedFlow()

    fun updateField1(value: String) {
        _uiState.value = _uiState.value.copy(field1 = value)
    }
}
```

---

## Color Scheme

### Main Theme Gradient
- Start: `#9500FF` (Purple)
- End: `#FF2F4B` (Pink-Red)

### Alternate Gradients
- Blue (Scanning): `#60A5FA` → `#3B82F6`
- Success (Green): `#00D084` → `#00B372`

### Supporting Colors
- Text Primary: `#000000` (Black)
- Text Secondary: `#999999` (Gray)
- Background: `#F5F5F5` (Light Gray)
- Card Background: `#FFF5F3FF` (Very Light Purple)

---

## Usage Integration

### Screen Navigation Pattern
```kotlin
// In NavGraph
composable("generateQRCode") {
    GenerateQRCodeScreen(
        onNextClick = { navController.navigate("userDetails") },
        onBackClick = { navController.popBackStack() }
    )
}

composable("userDetails") {
    UserDetailsScreen(
        onNextClick = { navController.navigate("deliveryAddress") },
        onBackClick = { navController.popBackStack() }
    )
}
```

### ViewModel Collection Pattern
```kotlin
val uiState by viewModel.uiState.collectAsState()
```

---

## Next Steps

1. **Navigation Integration** - Add routes to main NavGraph
2. **Data Layer** - Create repositories for API calls
3. **Image Upload** - Implement photo picker for GenerateQRCodeScreen
4. **Real QR Generation** - Integrate QR library (ZXing or QRGen)
5. **Camera Integration** - Implement ML Kit for QRCodeScannerScreen
6. **PDF Generation** - Add PDF download functionality
7. **State Persistence** - Add SavedStateHandle for form recovery

---

## Dependencies Required

Currently using:
- AndroidX Lifecycle
- Jetpack Compose
- Kotlin Coroutines
- Material 3 Icons

For full implementation add:
- ZXing Core (QR generation)
- ML Kit Vision (QR scanning)
- PDF Library (PDF generation)
- Coil/Glide (Image loading)

---

## Testing Considerations

1. **Screen Preview** - All screens have @Preview composables
2. **State Management** - Test ViewModel state updates
3. **Navigation Flow** - Test screen transitions
4. **Input Validation** - Test form field constraints
5. **Error Handling** - Test error states in ViewModels

---

## Status
✅ **Complete** - All 7 screens and 7 ViewModels created with full MVVM pattern
