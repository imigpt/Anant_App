# Fundraiser Feature Module

Complete fundraiser feature module with screens and ViewModels for the AnantApp.

## Module Structure

```
feature/fundraiser/
├── presentation/
│   ├── screens/
│   │   ├── SelectFundraiserCategoryScreen.kt    ← Category selection
│   │   ├── CreateFundraiserScreen.kt            ← Fundraiser creation form
│   │   ├── TargetAndPaymentsScreen.kt           ← Target amount & payment setup
│   │   └── PreviewAndSubmitScreen.kt            ← Review and submit fundraiser
│   └── viewmodel/
│       ├── SelectFundraiserCategoryViewModel.kt
│       ├── CreateFundraiserViewModel.kt
│       ├── TargetAndPaymentsViewModel.kt
│       └── PreviewAndSubmitViewModel.kt
└── data/ (To be created)
```

## Screens

### 1. SelectFundraiserCategoryScreen
**Purpose**: Allow users to select fundraiser category

**Key Features**:
- Category selection with chips (gradient border/fill)
- Custom title input for "Other" category (animated)
- Gradient background UI
- Next button to proceed

**States**:
- `selectedCategory`: Currently selected category
- `categories`: List of available categories (Health, Education, Emergency, Personal, Community, Other)

**Package**: `com.example.anantapp.feature.fundraiser.presentation.screens`

### 2. CreateFundraiserScreen
**Purpose**: Collect fundraiser details from user

**Form Fields**:
- Fundraiser Title *
- Description/Story *
- Category *
- Beneficiary Name *
- Beneficiary Relation *

**Buttons**:
- Draft (Outline button)
- Next (Gradient button)

**Package**: `com.example.anantapp.feature.fundraiser.presentation.screens`

### 3. TargetAndPaymentsScreen
**Purpose**: Set funding target and payment configuration

**Fields**:
- Target Amount with Currency Dropdown (INR, USD, EUR, GBP)
- Auto-topup Toggle

**Navigation**:
- Step 1: Target & Payments
- Step 2: Fundraiser Visibility (future)

**Package**: `com.example.anantapp.feature.fundraiser.presentation.screens`

### 4. PreviewAndSubmitScreen
**Purpose**: Review fundraiser details before final submission

**Two Screens**:
1. **Preview Screen**: Shows summary of fundraiser
   - Title
   - Story
   - Goal Amount
   - Warning about non-editable fields after submission

2. **Success Screen**: Celebration screen after successful submission
   - Green checkmark icon
   - Fundraiser ID display
   - Go to Home button

**Package**: `com.example.anantapp.feature.fundraiser.presentation.screens`

## ViewModels

All ViewModels follow MVVM pattern with StateFlow for UI state and SharedFlow for events.

### SelectFundraiserCategoryViewModel
```kotlin
- uiState: StateFlow<SelectFundraiserCategoryUiState>
  - categories: List<FundraiserCategory>
  - selectedCategory: String?
  - isLoading: Boolean
  - error: String?
- selectCategory(categoryId: String)
```

### CreateFundraiserViewModel
```kotlin
- uiState: StateFlow<CreateFundraiserUiState>
  - title, description, category
  - beneficiaryName, beneficiaryRelation
- updateTitle(title: String)
- updateDescription(description: String)
- updateCategory(category: String)
- updateBeneficiaryName(name: String)
- updateBeneficiaryRelation(relation: String)
```

### TargetAndPaymentsViewModel
```kotlin
- uiState: StateFlow<TargetAndPaymentsState>
  - targetAmount, currency
  - isAutoTopupEnabled
- result: StateFlow<TargetAndPaymentsResult>
- updateTargetAmount(amount: String)
- updateCurrency(currency: String)
- toggleAutoTopup()
- saveDraft()
- submitFundraiser()
```

### PreviewAndSubmitViewModel
```kotlin
- uiState: StateFlow<PreviewAndSubmitState>
- result: StateFlow<PreviewAndSubmitResult>
- saveDraft()
- submitFundraiser()
```

## Reusable Components

### FormLabel
```kotlin
@Composable
fun FormLabel(text: String)
```
Displays form field labels with proper styling.

### FundraiserInputField
```kotlin
@Composable
fun FundraiserInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    mainGradient: Brush,
    singleLine: Boolean = true,
    minHeight: Dp = 52.dp
)
```
Custom input field with gradient border and styling.

### TargetAmountInput
Currency dropdown with amount input field.

### AutoTopupToggle
Toggle switch for auto-topup feature.

### PreviewField
Display label and value pair for preview screen.

### SuccessScreen
Reusable success celebration screen component.

## Color Scheme

- **Primary Gradient**: Purple (0xFF9500FF) to Pink-Red (0xFFFF2F4B)
- **Accent Blue**: 0xFF1976D2 (for informational elements)
- **Success Green**: 0xFF4CAF50
- **Text Primary**: Black (0xFF000000)
- **Text Secondary**: Gray (0xFF999999)

## Usage Example

```kotlin
// In MainActivity or Navigation
NavigationDestination(
    route = "fundraiser/select_category",
    content = {
        SelectFundraiserCategoryScreen(
            onBackClick = { /* Navigate back */ },
            onNextClick = { category, title ->
                // Navigate to CreateFundraiserScreen
            }
        )
    }
)
```

## Next Steps

1. **Add Navigation Routes**: Create routes.kt with fundraiser routes
2. **Create Data Layer**: Add repositories and API services
3. **Connect ViewModels to APIs**: Implement network calls
4. **Add Form Validation**: Validate user inputs
5. **Error Handling**: Implement error states and messages
6. **Testing**: Add unit and UI tests

## Dependencies

- AndroidX Compose
- AndroidX Lifecycle (ViewModel, StateFlow)
- Kotlin Coroutines

---

**Created**: March 31, 2026  
**Status**: Active  
**Package**: `com.example.anantapp.feature.fundraiser`  
**Version**: 1.0
