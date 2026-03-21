># AnantApp - Complete File Structure & Theme System Guide

## Project Overview
**AnantApp** is a production-level fundraising and wallet management application built with:
- **Architecture**: MVVM + Clean Architecture
- **UI Framework**: Jetpack Compose
- **Language**: Kotlin
- **Target Android**: API 24+ (Android 7.0+)

---

## Complete Project Structure

```
AnantApp/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/anantapp/
│   │   │   │   ├── MainActivity.kt                    # Entry point
│   │   │   │   │
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── EnableLocationState.kt     # State for location
│   │   │   │   │   │   ├── LoginState.kt              # Auth state
│   │   │   │   │   │   ├── PhotoUploadState.kt        # Upload state
│   │   │   │   │   │   ├── VerifyAddressState.kt      # Address verification
│   │   │   │   │   │   ├── VerifyBankState.kt         # Bank verification
│   │   │   │   │   │   └── VerifyState.kt             # General verification
│   │   │   │   │   │
│   │   │   │   │   └── repository/
│   │   │   │   │       ├── AuthRepository.kt
│   │   │   │   │       ├── EnableLocationRepository.kt
│   │   │   │   │       ├── PhotoUploadRepository.kt
│   │   │   │   │       ├── VerifyAddressRepository.kt
│   │   │   │   │       ├── VerifyBankRepository.kt
│   │   │   │   │       └── VerifyRepository.kt
│   │   │   │   │
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── screen/
│   │   │   │   │   │   ├── AddBalanceScreen.kt              # Add funds screen
│   │   │   │   │   │   ├── BalanceScreen.kt                # Display balance
│   │   │   │   │   │   ├── DashboardScreen.kt              # Analytics
│   │   │   │   │   │   ├── DashboardScreenNew.kt           # Refactored analytics
│   │   │   │   │   │   ├── DeclareInsuranceDetailsScreen.kt # Insurance form
│   │   │   │   │   │   ├── DonationHistoryScreen.kt        # Donation logs
│   │   │   │   │   │   ├── DonorScreen.kt                  # Donor info
│   │   │   │   │   │   ├── EnableLocationScreen.kt         # Location permission
│   │   │   │   │   │   ├── FamilyDetailsScreen.kt          # Family info form
│   │   │   │   │   │   ├── GovernmentFundraisersScreen.kt  # Gov programs
│   │   │   │   │   │   ├── HomeScreen.kt                   # Main wallet screen
│   │   │   │   │   │   ├── PaymentMethodScreen.kt          # Payment options
│   │   │   │   │   │   ├── SelectFundraiserCategoryScreen.kt # Category picker
│   │   │   │   │   │   ├── TransactionScreen.kt            # Transaction list
│   │   │   │   │   │   ├── VerifyIncomeScreen.kt           # Income verification
│   │   │   │   │   │   ├── WalletSettingsScreen.kt         # Wallet settings (NEW)
│   │   │   │   │   │   └── ui/ (nested)
│   │   │   │   │   │       └── (deprecated - components moved to ui/components)
│   │   │   │   │   │
│   │   │   │   │   └── viewmodel/
│   │   │   │   │       ├── BalanceScreenViewModel.kt
│   │   │   │   │       ├── DashboardScreenViewModel.kt
│   │   │   │   │       ├── DonationHistoryViewModel.kt
│   │   │   │   │       ├── DonorScreenState.kt
│   │   │   │   │       ├── DonorScreenViewModel.kt
│   │   │   │   │       ├── EnableLocationViewModel.kt
│   │   │   │   │       ├── HomeScreenViewModel.kt
│   │   │   │   │       ├── PaymentMethodViewModel.kt
│   │   │   │   │       └── TransactionViewModel.kt
│   │   │   │   │
│   │   │   │   └── ui/
│   │   │   │       ├── components/
│   │   │   │       │   ├── AnantLogo.kt
│   │   │   │       │   ├── CommonComponents.kt
│   │   │   │       │   ├── OTPInputField.kt
│   │   │   │       │   ├── PhoneInputField.kt
│   │   │   │       │   └── PrimaryButton.kt
│   │   │   │       │
│   │   │   │       ├── login/
│   │   │   │       │   └── LoginScreen.kt
│   │   │   │       │
│   │   │   │       ├── onboarding/
│   │   │   │       │   └── OnboardingScreen.kt
│   │   │   │       │
│   │   │   │       ├── theme/
│   │   │   │       │   ├── Color.kt
│   │   │   │       │   ├── Theme.kt
│   │   │   │       │   ├── Type.kt
│   │   │   │       │   └── ProductionTheme.kt
│   │   │   │       │
│   │   │   │       └── verify/
│   │   │   │           ├── PhotoUploadScreen.kt
│   │   │   │           ├── VerifyAddressScreen.kt
│   │   │   │           ├── VerifyBankScreen.kt
│   │   │   │           └── VerifyScreen.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   │   └── (drawables, layouts, values - auto-generated)
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   ├── androidTest/
│   │   └── test/
│   │
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│
├── ARCHITECTURE.md
├── FILE_MANAGEMENT_GUIDE.md
└── README.md
```

---

## Theme System Architecture

### Overview
AnantApp uses a **modular, screen-specific theme system** that supports:
1. **Multiple theme variants** for different features
2. **Consistent design tokens** (colors, spacing, typography, elevation)
3. **Easy extensibility** for future theme additions
4. **Material 3 integration** for Android aesthetic consistency

---

## Theme Components

### 1. **Color.kt** - Base Color Palette
Defines foundational colors used across the app.

```kotlin
// Primary Colors
val PrimaryRed = Color(0xFFE74C3C)
val PrimaryRedDark = Color(0xFFC0392B)

// Neutral Colors (Greyscale)
val TextPrimary = Color(0xFF1F2937)        // Dark grey text
val TextSecondary = Color(0xFF6B7280)      // Medium grey text
val TextTertiary = Color(0xFF9CA3AF)       // Light grey text
val Background = Color(0xFFFAFAFA)         // Off-white background
val Surface = Color(0xFFFFFFFF)            // Pure white cards

// Semantic Colors (Status/Feedback)
val ErrorRed = Color(0xFFDC2626)
val SuccessGreen = Color(0xFF10B981)
val WarningAmber = Color(0xFFF59E0B)

// Material 3 Compatibility
val Purple40/80, PurpleGrey40/80, Pink40/80
```

**Usage**: Foundation for all custom themes.

---

### 2. **Type.kt** - Typography System
Defines Material 3 typography scales for consistent text styling.

```kotlin
Typography(
    titleLarge   = 28.sp Bold      // Page headings
    titleMedium  = 22.sp Bold      // Section titles
    titleSmall   = 18.sp SemiBold  // Subsection titles
    
    bodyLarge    = 16.sp Normal    // Main body text
    bodyMedium   = 14.sp Normal    // Secondary text
    bodySmall    = 12.sp Normal    // Helper text
    
    labelLarge   = 14.sp Medium    // Buttons, tags
    labelMedium  = 12.sp Medium    // Small labels
    labelSmall   = 11.sp Medium    // Micro labels
)
```

**Usage**: Applied via `Text(style = MaterialTheme.typography.bodyLarge)`

---

### 3. **Theme.kt** - Material 3 Base Theme
Provides system-wide Material Design support with dynamic colors.

```kotlin
@Composable
fun AnantAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,      // Android 12+ system colors
    content: @Composable () -> Unit
)
```

**Features**:
- ✅ Dynamic Material You colors (Android 12+)
- ✅ Light/Dark theme support
- ✅ Automatic system preference detection
- ✅ Material 3 compliant

---

### 4. **ProductionTheme.kt** - Custom Screen Themes
Three specialized themes tailored for different screen types:

#### **Theme 1: BankingTheme** 🏦
**Used for**: Home, Wallet, Transactions, Add Balance

```kotlin
object BankingTheme {
    object Colors {
        val GradientStart = Color(0xFFFF5252)      // Red
        val GradientOrange = Color(0xFFFF6E40)     // Orange
        val GradientPink = Color(0xFFE91E8C)       // Pink
        val GradientMagenta = Color(0xFFDB1B8F)    // Magenta
        val GradientPurple = Color(0xFF9C27B0)     // Purple (Primary)
        val GradientEnd = Color(0xFF6A1B9A)        // Deep Purple
        
        val CardBackground = Color.White
        val DarkBackground = Color(0xFF1B5E5E)
        val SuccessGreen = Color(0xFF4CAF50)
        val TextPrimary = Color.Black
        val TextOnGradient = Color.White
    }
    
    object Spacing {
        val ExtraSmall = 4.dp
        val Small = 8.dp
        val Medium = 16.dp       // Default padding
        val Large = 24.dp
        val ExtraLarge = 32.dp
        val Huge = 40.dp
    }
    
    object Radius {
        val Small = 8.dp
        val Medium = 16.dp
        val Large = 20.dp
        val Full = 100.dp        // Fully rounded
    }
    
    object Elevation {
        val None = 0.dp
        val Small = 2.dp
        val Medium = 4.dp
        val Large = 8.dp
        val Extreme = 16.dp
    }
}
```

**Color Gradient**: Red → Orange → Pink → Magenta → Purple → Deep Purple

**Example Usage**:
```kotlin
Box(
    modifier = Modifier
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    BankingTheme.Colors.GradientStart,
                    BankingTheme.Colors.GradientEnd
                )
            )
        )
)

Text(
    text = "Balance",
    style = MaterialTheme.typography.bodyLarge,
    color = BankingTheme.Colors.TextPrimary
)
```

---

#### **Theme 2: DashboardTheme** 📊
**Used for**: Analytics, Reports, Statistics

```kotlin
object DashboardTheme {
    object Colors {
        val Background = Color.Black           // Dark background
        val CardBackground = Color.White       // White cards
        val PrimaryAccent = Color(0xFF9C27B0)  // Purple accent
        val TextPrimary = Color.White          // White text on dark
        val TextSecondary = Color.Gray         // Grey text
    }
    
    val Spacing = BankingTheme.Spacing  // Reuses BankingTheme spacing
    val Radius = BankingTheme.Radius
    val Elevation = BankingTheme.Elevation
}
```

**Design Approach**: Dark-mode focused for better readability of data visualizations.

---

#### **Theme 3: BalanceTheme** 💜
**Used for**: Balance Display, Simple Info Screens

```kotlin
object BalanceTheme {
    object Colors {
        val GradientPink = Color(0xFFE91E8C)           // Pink
        val GradientMagenta = Color(0xFFDB1B8F)        // Magenta
        val GradientPurple = Color(0xFF9C27B0)         // Purple
        val GradientDarkPurple = Color(0xFF6A1B9A)     // Dark Purple
        val GradientBlack = Color(0xFF1A0033)          // Near Black
        
        val PrimaryAccent = Color(0xFF9C27B0)          // Purple
        val CardBackground = Color.White
        val TextOnGradient = Color.White               // White text on gradient
    }
}
```

**Color Gradient**: Pink → Magenta → Purple → Dark Purple → Black

---

## Design Tokens Summary

| Token Type | Values | Purpose |
|-----------|--------|---------|
| **Colors** | Primary, Secondary, Status | UI elements, backgrounds, text |
| **Spacing** | 4dp → 40dp | Padding, margins, gutters |
| **Radius** | 8dp → 100dp (Full) | Corner radius for cards/buttons |
| **Elevation** | 0dp → 16dp | Shadow depth for cards |
| **Typography** | Title, Body, Label | Text hierarchy |

---

## Color Scheme Distribution

### Banking Theme Gradient
```
Red(#FF5252)
    ↓
Orange(#FF6E40)
    ↓
Pink(#E91E8C)
    ↓
Magenta(#DB1B8F)
    ↓
Purple(#9C27B0) ← PRIMARY
    ↓
Deep Purple(#6A1B9A)
```

### Semantic Colors
| Color | Hex | Use Case |
|-------|-----|----------|
| Success Green | #4CAF50 | Successful transactions, confirmations |
| Error Red | #DC2626 | Errors, failed actions |
| Warning Amber | #F59E0B | Warnings, pending states |

---

## How to Use Themes in Screens

### Basic Theme Application
```kotlin
@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BankingTheme.Colors.CardBackground)
            .padding(BankingTheme.Spacing.Medium)
    ) {
        Text(
            text = "Wallet Balance",
            style = MaterialTheme.typography.titleLarge,
            color = BankingTheme.Colors.TextPrimary
        )
    }
}
```

### Gradient Background
```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    BankingTheme.Colors.GradientStart,
                    BankingTheme.Colors.GradientEnd
                )
            )
        )
)
```

### Material 3 Integration
```kotlin
// App-level theme wrapper
setContent {
    AnantAppTheme {
        MainScreen()  // Material 3 colors automatically applied
    }
}
```

---

## Screen to Theme Mapping

| Screen | Theme | Primary Color | Use Case |
|--------|-------|---------------|----------|
| HomeScreen | Banking | Purple | Main wallet display |
| BalanceScreen | Balance | Purple-Black Gradient | Balance visualization |
| DashboardScreen | Dashboard | Purple | Analytics & reports |
| TransactionScreen | Banking | Purple | Transaction history |
| DonationHistoryScreen | Banking | Purple | Donation records |
| WalletSettingsScreen | Banking | Purple | Settings menu |
| SelectFundraiserCategoryScreen | Banking | Purple-Pink | Category selection |
| EnableLocationScreen | Banking | Purple | Permission request |

---

## Extensibility: Adding New Themes

To add a new theme (e.g., for a new feature):

```kotlin
// In ProductionTheme.kt
object NewFeatureTheme {
    object Colors {
        val Background = Color(0xFFXXXXXX)
        val PrimaryAccent = Color(0xFFXXXXXX)
        // ... other colors
    }
    
    val Spacing = BankingTheme.Spacing  // Reuse or define new
    val Radius = BankingTheme.Radius
    val Elevation = BankingTheme.Elevation
}

// In your screen
override fun onCreate(...) {
    setContent {
        AnantAppTheme {
            MyScreen(theme = NewFeatureTheme)
        }
    }
}
```

---

## Best Practices

✅ **DO**:
- Use theme objects from `ProductionTheme.kt` for consistency
- Define all colors in `Color.kt`
- Use typography styles from `Typography`
- Reuse spacing tokens from `BankingTheme.Spacing`
- Follow the gradient patterns for brand consistency

❌ **DON'T**:
- Hardcode colors in composables
- Use arbitrary padding/margin values
- Mix different theme palettes in one screen
- Define colors locally instead of in `Color.kt`

---

## Light/Dark Mode Support

Currently, the app uses **Light Mode**. To add Dark Mode:

```kotlin
// Modify Theme.kt
@Composable
fun AnantAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
)
```

This is already configured for Material 3 dynamic colors on Android 12+.

---

## Summary

| Component | Location | Purpose |
|-----------|----------|---------|
| Base Colors | `Color.kt` | Foundation palette |
| Typography | `Type.kt` | Text styling |
| Material Theme | `Theme.kt` | System-wide theming |
| Custom Themes | `ProductionTheme.kt` | Screen-specific themes |

The theme system enables **consistency**, **scalability**, and **maintainability** across all screens while supporting multiple visual styles without code duplication.
