# App Module Structure - Production Level Organization

This document explains the new production-level folder structure for the AnantApp.

## New Folder Structure

```
app/src/main/java/com/example/anantapp/
│
├── core/                           ← Shared components across all features
│   ├── presentation/
│   │   ├── theme/                  ← Theme system (Color, Type, Theme)
│   │   │   ├── Color.kt
│   │   │   ├── Type.kt
│   │   │   └── Theme.kt
│   │   ├── base/                   ← Base classes
│   │   │   └── BaseViewModel.kt    ← Abstract ViewModel with state management
│   │   └── components/             ← Reusable UI components
│   │       └── Buttons.kt
│   └── utils/                      ← Utilities and constants
│       ├── Constants.kt
│       └── ValidationUtils.kt
│
├── feature/                        ← Feature modules (separated by feature)
│   ├── nominee/                    ← Nominee feature
│   │   ├── presentation/
│   │   │   ├── screens/            ← All nominee screens
│   │   │   │   ├── AddNomineeCardsScreen.kt
│   │   │   │   ├── NomineeDetailsScreen.kt
│   │   │   │   └── ...
│   │   │   └── viewmodel/          ← ViewModels for nominee
│   │   │       └── NomineeViewModel.kt
│   │   └── data/
│   │
│   ├── verify/                     ← Verify feature
│   │   ├── presentation/
│   │   │   ├── screens/            ← All verify screens
│   │   │   └── viewmodel/          ← ViewModels for verify
│   │   └── data/
│   │
│   ├── wallet/                     ← Wallet feature
│   │   ├── presentation/
│   │   │   ├── screens/
│   │   │   └── viewmodel/
│   │   └── data/
│   │
│   ├── location/                   ← Location feature
│   │   ├── presentation/
│   │   │   ├── screens/
│   │   │   └── viewmodel/
│   │   └── data/
│   │
│   ├── fundraiser/                 ← Fundraiser feature
│   │   ├── presentation/
│   │   │   ├── screens/
│   │   │   └── viewmodel/
│   │   └── data/
│   │
│   └── qr/                         ← QR Code feature
│       ├── presentation/
│       │   ├── screens/
│       │   └── viewmodel/
│       └── data/
│
└── MainActivity.kt                 ← Entry point
```

## Key Benefits of This Structure

### 1. **Scalability**
- Each feature is isolated and independent
- Easy to add new features without affecting existing code
- Can develop features in parallel

### 2. **Maintainability**
- Related code is grouped together
- Easy to locate and modify feature code
- Clear separation of concerns

### 3. **Testability**
- Each feature can be tested independently
- Easier to write unit and integration tests
- Mock dependencies more easily

### 4. **Performance**
- Lazy loading of features possible in future
- Clear dependency graph
- Better build optimization potential

## Package Naming Convention

```
Screens:    com.example.anantapp.feature.<featureName>.presentation.screens
ViewModels: com.example.anantapp.feature.<featureName>.presentation.viewmodel
Data:       com.example.anantapp.feature.<featureName>.data
Core:       com.example.anantapp.core.<layer>
```

## Current Features Organized

### ✅ Core (Shared)
- Theme and UI system
- Base ViewModels
- Reusable components
- Utilities and constants

### 📋 Features to Organize
- **Nominee**: AddNomineeCardsScreen, NomineeDetailsScreen, etc.
- **Verify**: VerifyScreen, PhotoUploadScreen, DocumentScanScreen, etc.
- **Wallet**: BalanceScreen, TransactionScreen, PaymentMethodScreen, etc.
- **Location**: ShareRealTimeLocationScreen, ManageFamilyMembersScreen, etc.
- **Fundraiser**: CreateFundraiserScreen, SelectFundraiserCategoryScreen, etc.
- **QR**: GenerateQRCodeScreen, ViewQRCodeScreen, QRCodeScannerScreen, etc.

## How to Use This Structure

### Adding a New Screen
1. Create the screen in `feature/<featureName>/presentation/screens/`
2. Create its ViewModel in `feature/<featureName>/presentation/viewmodel/`
3. Use `BaseViewModel` as the parent class
4. Import theme from `core.presentation.theme`

### Adding Shared Components
Add to `core/presentation/components/` when:
- Used by multiple features
- Reusable across the app
- Part of design system

### Creating a New Feature Module
1. Create directory structure: `feature/<newFeature>/presentation/screens|viewmodel`
2. Create directory structure: `feature/<newFeature>/data`
3. Add screens and viewmodels
4. Create navigation routes for the feature

## Next Steps

1. **Move Existing Screens** - Move screens from `presentation/screen/` to appropriate `feature/<name>/presentation/screens/`
2. **Move Existing ViewModels** - Move from `presentation/viewmodel/` to `feature/<name>/presentation/viewmodel/`
3. **Update Imports** - Update all import statements to reflect new package structure
4. **Create Navigation** - Set up navigation routes for each feature
5. **Create Data Layers** - Add repositories and API services for each feature

## Build and Test

```bash
# Build entire app
./gradlew build

# Build specific feature (future: when split into modules)
./gradlew :feature:nominee:build

# Run tests
./gradlew test
```

---

This structure is the foundation for a scalable, production-level Android application following MVVM architecture and clean code principles.

**Updated**: March 31, 2026  
**Status**: Active  
**Version**: 1.0
