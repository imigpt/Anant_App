# Wallet Feature Module Implementation

## Overview
Complete Wallet feature module with 4 screens and 4 ViewModels implementing the wallet top-up, payment methods, transaction history, and settings management flows.

## Package Structure
```
com.example.anantapp.feature.wallet
├── presentation
│   ├── screens/        (6 screens)
│   │   ├── AddBalanceScreen.kt
│   │   ├── PaymentMethodScreen.kt
│   │   ├── TransactionScreen.kt
│   │   ├── WalletSettingsScreen.kt
│   │   ├── DonorScreen.kt
│   │   └── DonationHistoryScreen.kt
│   └── viewmodel/      (6 ViewModels)
│       ├── AddBalanceViewModel.kt
│       ├── PaymentMethodViewModel.kt
│       ├── TransactionViewModel.kt
│       ├── WalletSettingsViewModel.kt
│       ├── DonorScreenViewModel.kt
│       └── DonationHistoryViewModel.kt
```

## Screens Implementation

### 1. AddBalanceScreen
**Purpose**: Allow users to add funds to their wallet with quick amount selection

**Features**:
- Amount input field with currency symbol (₹)
- 6 quick amount buttons: ₹500, ₹1000, ₹2500, ₹5000, ₹10000
- Custom input clearing
- Gradient header (Purple to Pink)

**UI Components**:
- Back button header
- Rupee symbol input prefix
- BasicTextField for custom amount entry
- Grid of suggested amount buttons
- Proceed action button (gradient filled)

**Form Interaction**:
- Users can type custom amount
- Quick selection updates amount field
- Clear button removes entered amount
- Proceed button triggers payment flow

**ViewModel**: AddBalanceViewModel
- updateAmount() - manual entry
- selectQuickAmount() - quick button selection
- proceedWithPayment() - triggers payment

---

### 2. PaymentMethodScreen
**Purpose**: Display and manage payment method selection

**Payment Options**:
- **Credit/Debit Card**: Add debit card with validation
- **Bank Transfer**: Manual bank deposit option
- **UPI**: Universal Payments Interface integration

**Features**:
- Single selection radio pattern
- Method checkboxes with gradient border
- Sectioned layout for organization
- Clean pill-shaped payment option cards

**UI Components**:
- Gradient header
- Payment method option boxes with selection state
- Gradient border for selected options
- Light purple background for selected cards
- Continue action button

**Data Classes**:
```kotlin
data class CardInfo(
    val id: String,
    val cardNumber: String,
    val cardHolder: String,
    val expiryDate: String,
    val last4Digits: String,
    val isDefault: Boolean = false
)

data class BankAccount(
    val id: String,
    val bankName: String,
    val accountNumber: String,
    val ifscCode: String,
    val accountHolder: String,
    val isDefault: Boolean = false
)

data class UpiMethod(
    val id: String,
    val upiId: String,
    val name: String,
    val isDefault: Boolean = false
)
```

**ViewModel**: PaymentMethodViewModel
- selectPaymentMethod() - select payment option
- addCard() - add new card
- addBankAccount() - add bank account
- addUpiMethod() - add UPI ID
- removeCard() - remove saved card
- setDefaultCard() - set default payment method

---

### 3. TransactionScreen
**Purpose**: Display user's transaction history with search and filter options

**Features**:
- Real-time search across transactions
- Filter by transaction type
- Month-grouped transaction display
- Transaction status indicators
- Credit/Debit color coding

**Transaction Data**:
```kotlin
data class Transaction(
    val id: String,
    val type: String,           // "Top up Wallet", "Donation", "Refund"
    val amount: String,         // "₹500"
    val date: String,          // "Apr 15 2025"
    val status: String,        // "Success", "Pending", "Failed"
    val isCredit: Boolean       // true for credit (+), false for debit (-)
)
```

**UI Components**:
- Search field with search icon
- Filter button with gradient background
- Transaction list with circular icon badges
- Credit amount (green) / Debit amount (red)
- Grouped by month headers

**ViewModel**: TransactionViewModel
- updateSearchQuery() - filter by text
- setFilterType() - filter by transaction type
- getFilteredTransactions() - computed filtered list

---

### 4. WalletSettingsScreen
**Purpose**: Manage wallet-related settings and policies

**Settings Options**:
1. **Auto-Debit Setup** - Enable/disable automatic wallet deduction
2. **NACH Top-Up Threshold** - Set automatic top-up amount trigger
3. **Withdrawal Rule** - Configure withdrawal policies
4. **User Settings** - Account and profile settings
5. **Service Center** - Contact support
6. **FAQ's** - Help and documentation

**UI Components**:
- Gradient back button with border stroke
- Settings list with circular icon backgrounds
- Right chevron navigation indicators
- Clean card-based layout
- White circular icon containers

**ViewModel**: WalletSettingsViewModel
- toggleAutoDebit() - enable/disable auto-debit
- updateTopUpThreshold() - set threshold amount
- acceptWithdrawalRules() - confirm policy acceptance
- toggleNotifications() - notification settings
- saveSettings() - persist settings

---

### 5. DonorScreen
**Purpose**: Allow donors to register and make donations with donor type selection

**Donor Types**:
1. **Government Employee** - Employee ID + PAN validation
2. **Individual Donor** - PAN + Aadhar validation
3. **Corporate/Company** - Company Details + Tax ID

**Features**:
- 3 donor type selection buttons (radio pattern)
- Dynamic form fields based on selected donor type
- Quick donation amount buttons: ₹100, ₹500, ₹1000
- Custom donation amount input field
- Government Employee form (Employee ID, PAN)
- Individual Donor form (PAN, Aadhar)
- Corporate form (Company Name, Registration, Tax ID)

**Data Classes**:
```kotlin
data class DonorScreenUiState(
    val selectedDonorType: String? = null,  // "government", "individual", "corporate"
    val donationAmount: String = "",
    val panNumber: String = "",
    val aadharNumber: String = "",
    val companyName: String = "",
    val companyRegistration: String = "",
    val taxId: String = "",
    val employeeId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
```

**UI Components**:
- Gradient header (Purple to Pink)
- 3 donor type selection buttons
- Conditional form rendering based on type
- Quick amount buttons (₹100, ₹500, ₹1000)
- Custom amount field
- Proceed button

**ViewModel**: DonorScreenViewModel
- selectDonorType() - select donor category
- updateDonationAmount() - set donation amount
- updatePanNumber() - PAN entry
- updateAadharNumber() - Aadhar entry
- updateCompanyName() - Company name entry
- updateCompanyRegistration() - Registration entry
- updateTaxId() - Tax ID entry
- updateEmployeeId() - Employee ID entry
- proceedToDonation() - trigger payment flow

---

### 6. DonationHistoryScreen
**Purpose**: Display user's donation history with search and filtering capabilities

**Features**:
- Complete donation history display
- Real-time search by fundraiser name or amount
- Filter by donation category
- Monthly grouping of donations
- Receipt download functionality
- Status indicators (Success, Pending, Failed)
- Detailed donation information display

**Donation Record**:
```kotlin
data class DonationRecord(
    val id: String,
    val fundraiserName: String,
    val fundraiserCategory: String,
    val amountDonated: String,
    val dateOfDonation: String,
    val status: String,
    val receiptUrl: String? = null
)
```

**UI Components**:
- Gradient header with back button
- Search field with search icon
- Filter button with gradient background
- Donation cards with shadow elevation
- Card content layout:
  - Date display
  - Fundraiser name
  - Amount donated
  - Status badge (green for success)
  - Download Receipt button (gradient filled)
- Month-grouped display

**ViewModel**: DonationHistoryViewModel
- updateSearchQuery() - live search filtering
- setFilterCategory() - filter by category
- downloadReceipt() - trigger receipt download
- getFilteredDonations() - computed filtered list
- Sample data initialization for UI testing

---

## Data Classes & State Management

### AddBalanceUiState
```kotlin
data class AddBalanceUiState(
    val amount: String = "500",
    val selectedAmount: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### PaymentMethodUiState
```kotlin
data class PaymentMethodUiState(
    val selectedPaymentMethod: String? = null,
    val cards: List<CardInfo> = emptyList(),
    val bankAccounts: List<BankAccount> = emptyList(),
    val upiMethods: List<UpiMethod> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### TransactionScreenUiState
```kotlin
data class TransactionScreenUiState(
    val transactions: List<Transaction> = emptyList(),
    val searchQuery: String = "",
    val filterType: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### WalletSettingsUiState
```kotlin
data class WalletSettingsUiState(
    val autoDebitEnabled: Boolean = false,
    val topUpThreshold: String = "₹500",
    val withdrawalRulesAccepted: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### DonorScreenUiState
```kotlin
data class DonorScreenUiState(
    val selectedDonorType: String? = null,
    val donationAmount: String = "",
    val panNumber: String = "",
    val aadharNumber: String = "",
    val companyName: String = "",
    val companyRegistration: String = "",
    val taxId: String = "",
    val employeeId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
```

### DonationHistoryUiState
```kotlin
data class DonationHistoryUiState(
    val donations: List<DonationRecord> = emptyList(),
    val searchQuery: String = "",
    val filterCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class DonationRecord(
    val id: String,
    val fundraiserName: String,
    val fundraiserCategory: String,
    val amountDonated: String,
    val dateOfDonation: String,
    val status: String,
    val receiptUrl: String? = null
)
```

---

## Result Sealed Classes

### AddBalanceResult
```kotlin
sealed class AddBalanceResult {
    object Idle : AddBalanceResult()
    data class Success(val transactionId: String) : AddBalanceResult()
    data class Error(val message: String) : AddBalanceResult()
}
```

### PaymentMethodResult
```kotlin
sealed class PaymentMethodResult {
    object Idle : PaymentMethodResult()
    data class Success(val methodId: String) : PaymentMethodResult()
    data class Error(val message: String) : PaymentMethodResult()
}
```

### WalletSettingsResult
```kotlin
sealed class WalletSettingsResult {
    object Idle : WalletSettingsResult()
    data class SettingsUpdated(val message: String) : WalletSettingsResult()
    data class Error(val message: String) : WalletSettingsResult()
}
```

### DonorScreenResult
```kotlin
sealed class DonorScreenResult {
    object Idle : DonorScreenResult()
    data class Success(val donorId: String) : DonorScreenResult()
    data class Error(val message: String) : DonorScreenResult()
}
```

---

## Color Scheme

### Primary Gradient
- Start: `#9500FF` (Purple)
- End: `#FF6264` (Pink-Red)

### Supporting Colors
- Text Primary: `#333333` (Dark Gray)
- Text Secondary: `#888888` (Medium Gray)
- Text Disabled: `#AAAAAA` (Light Gray)
- Border: `#E0E0E0` / `#E5E5E5` (Light Border)
- Input Background: `#F5F6F8` (Light Blue-Gray)
- Card Background: `#F5F3FF` (Light Purple tint when selected)
- Credit Amount: `#4CAF50` (Green)
- Debit Amount: `#E53935` (Red)
- Success: `#00D084` (Green)

---

## Key Features

### 1. Add Balance Flow
- Quick amounts for faster selection
- Custom amount entry with validation
- Clear functionality for easy correction
- Seamless transition to payment methods

### 2. Payment Methods
- Card storage with optional default setting
- Bank account information (IFSC, account number)
- UPI ID management
- Add/remove/set default functionality

### 3. Transaction History
- Real-time filtering by amount/type
- Search functionality
- Credit/debit color coding
- Status indicators
- Monthly grouping

### 4. Wallet Settings
- Settings list with navigation
- Toggle configurations
- Threshold management
- Notification control

---

## Usage Integration

### Screen Navigation
```kotlin
composable("wallet/addBalance") {
    AddBalanceScreen(
        onBackClick = { navController.popBackStack() },
        onProceedClick = { navController.navigate("wallet/paymentMethod") }
    )
}

composable("wallet/paymentMethod") {
    PaymentMethodScreen(
        onBackClick = { navController.popBackStack() },
        onPaymentComplete = { navController.navigate("wallet/success") }
    )
}

composable("wallet/transactions") {
    TransactionScreen(
        onBackClick = { navController.popBackStack() }
    )
}

composable("wallet/settings") {
    WalletSettingsScreen(
        onBackClick = { navController.popBackStack() }
    )
}
```

### State Collection Pattern
```kotlin
val uiState by viewModel.uiState.collectAsState()
val result by viewModel.result.collectAsState()
```

---

## Next Steps

1. **Data Layer** - Create repositories for wallet operations
2. **API Integration** - Connect to backend wallet service
3. **Payment Gateway** - Integrate payment processor
4. **Transaction Export** - Add PDF/receipt generation
5. **Advanced Filters** - Date range, category filters for transactions
6. **Wallet Balance Display** - Add balance card component
7. **Top-up History** - Dedicated history by type
8. **Notification System** - Real-time transaction alerts

---

## Dependencies Required

Current:
- AndroidX Lifecycle
- Jetpack Compose
- Kotlin Coroutines
- Material 3

For full implementation:
- Payment Gateway SDK (Razorpay, Stripe, etc.)
- PDF Library (PDF generation for receipts)
- Firebase (Analytics, Crashlytics)
- Retrofit (API communication)

---

## Testing Considerations

1. **Screen Preview** - All screens have @Preview composables
2. **State Management** - Test ViewModel state updates
3. **Search/Filter** - Test transaction filtering logic
4. **Payment Flow** - Test payment method selection
5. **Error Handling** - Test error state displays
6. **Form Validation** - Test amount constraints
7. **Navigation** - Test screen transitions

---

## Status
✅ **Complete** - All 6 screens and 6 ViewModels created with full MVVM pattern

## Total Wallet Screens & Features
- 6 screens with full UI implementation
- 6 ViewModels with StateFlow state management
- Support for 3 donor types
- Transaction history with search & filter
- Payment method management
- Wallet settings and policies
- Donation history with receipt download
- All screens have @Preview composables
- Full integration with core theme system
