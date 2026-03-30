/**
 * MVVM THEME ARCHITECTURE - IMPLEMENTATION GUIDE
 * 
 * This guide shows you how to implement MVVM with theme support in AnantApp
 */

/*
═══════════════════════════════════════════════════════════════════════════════
1. STRUCTURE OVERVIEW
═══════════════════════════════════════════════════════════════════════════════

Theme Layer:
├── ui/theme/
│   ├── VerifyTheme.kt          ← Define Verify screen colors
│   ├── WalletTheme.kt          ← Define Wallet screen colors
│   ├── NomineeTheme.kt         ← Define Nominee screen colors
│   ├── ProfileTheme.kt         ← Define Profile screen colors
│   ├── ThemeManager.kt         ← Central theme config (maps theme to colors)
│   └── CompositionLocal.kt     ← Makes theme available app-wide

Data Layer:
├── data/repository/
│   └── ThemeRepository.kt      ← DataStore operations (save/load themes)

Presentation Layer:
├── presentation/viewmodel/
│   └── ThemeViewModel.kt       ← StateFlow for theme state management

UI Components:
└── ui/theme/
    └── ThemedComponents.kt     ← Reusable themed components


═══════════════════════════════════════════════════════════════════════════════
2. HOW TO USE IN SCREENS
═══════════════════════════════════════════════════════════════════════════════

Example: ProfileSettingsScreen using MVVM + Themes

// STEP 1: Get the ViewModel
val themeViewModel: ThemeViewModel = viewModel()
val themeState by themeViewModel.themeState.collectAsState()

// STEP 2: Set theme when entering the screen
LaunchedEffect(Unit) {
    themeViewModel.setProfileScreenTheme()
}

// STEP 3: Use the theme from state
val theme = themeState.theme

// STEP 4: Apply theme to UI
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = theme.primaryGradient
            )
        )
)

// STEP 5: Use themed components
ThemedCard(
    modifier = Modifier.fillMaxWidth(),
    theme = theme
) {
    ThemedTitle("Your Profile", theme = theme)
    ThemedSubtitle("Manage your account", theme = theme)
}


═══════════════════════════════════════════════════════════════════════════════
3. THEME MAPPING FOR ALL SCREENS
═══════════════════════════════════════════════════════════════════════════════

VERIFY THEME (Blue & Green):
  - PhotoUploadScreen
  - VerifyScreen
  - VerifyBankScreen
  - VerifyAddressScreen
  - ProfileScreen

WALLET THEME (Purple & Pink):
  - BalanceScreen
  - AddBalanceScreen
  - TransactionScreen
  - DonationHistoryScreen
  - DonorScreen
  - ThankyouScreen

NOMINEE THEME (Cyan & Blue):
  - NomineeDetailsScreen
  - FamilyMemberDetailsScreen
  - AddNomineeCardsScreen
  - NomineeOTPVerificationScreen

PROFILE THEME (Orange & Red):
  - ProfileSettingsScreen
  - UserDetailsScreen
  - ContactInformationScreen
  - BirthdayCardScreen
  - FamilyDetailsScreen
  - FamilyInformationScreen


═══════════════════════════════════════════════════════════════════════════════
4. STEP-BY-STEP IMPLEMENTATION
═══════════════════════════════════════════════════════════════════════════════

Step 1: Add DataStore Dependency to build.gradle.kts
────────────────────────────────────────────────────────
dependencies {
    // For DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}


Step 2: Create Screen-Specific ViewModel (Example)
───────────────────────────────────────────────────
package com.example.anantapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.anantapp.data.repository.MyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileScreenState(
    val userName: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ProfileScreenViewModel(
    private val myRepository: MyRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()
    
    fun loadProfile() {
        // Load profile data from repository
    }
    
    fun updateProfile(name: String, email: String) {
        // Update profile data
    }
}


Step 3: Update Screen to Use Theme + ViewModel
───────────────────────────────────────────────
@Composable
fun ProfileSettingsScreen(
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    themeViewModel: ThemeViewModel = viewModel(),
    screenViewModel: ProfileScreenViewModel = viewModel()
) {
    val themeState by themeViewModel.themeState.collectAsState()
    val screenState by screenViewModel.state.collectAsState()
    
    // Set theme when entering this screen
    LaunchedEffect(Unit) {
        themeViewModel.setProfileScreenTheme()
    }
    
    val theme = themeState.theme
    
    ThemedGradientBackground(theme = theme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ThemedTitle("Profile Settings", theme = theme)
            
            ThemedCard(theme = theme) {
                TextField(
                    value = screenState.userName,
                    onValueChange = { screenViewModel.updateName(it) },
                    label = { ThemedSubtitle("Name", theme = theme) }
                )
            }
            
            ThemedButton(
                text = "Save",
                onClick = {
                    onSaveClick()
                },
                theme = theme
            )
        }
    }
}


Step 4: Set CompositionLocal in MainActivity
─────────────────────────────────────────────
@Composable
fun MyApp(
    themeViewModel: ThemeViewModel = viewModel()
) {
    val themeState by themeViewModel.themeState.collectAsState()
    
    CompositionLocalProvider(
        LocalCurrentTheme provides themeState.theme
    ) {
        // Your main app content
        when (currentScreen.value) {
            "profile" -> ProfileSettingsScreen()
            "wallet" -> BalanceScreen()
            // ... other screens
        }
    }
}


═══════════════════════════════════════════════════════════════════════════════
5. BENEFITS OF THIS APPROACH
═══════════════════════════════════════════════════════════════════════════════

✅ Single Responsibility: Each theme is a separate object
✅ Reusable Components: ThemedCard, ThemedButton work with any theme
✅ Type-Safe: All theme colors are properly typed
✅ Persistent: Theme preference saved in DataStore
✅ Scalable: Easy to add 4th, 5th, or more themes
✅ MVVM Pattern: Clear separation of concerns
✅ No Props Drilling: CompositionLocal makes theme available everywhere
✅ Easy Testing: Theme logic is independent of UI


═══════════════════════════════════════════════════════════════════════════════
6. NEXT STEPS
═══════════════════════════════════════════════════════════════════════════════

1. ✅ Created: VerifyTheme.kt, WalletTheme.kt, NomineeTheme.kt, ProfileTheme.kt
2. ✅ Created: ThemeManager.kt (maps themes to colors)
3. ✅ Created: ThemeRepository.kt (DataStore for persistence)
4. ✅ Created: ThemeViewModel.kt (MVVM state management)
5. ✅ Created: CompositionLocal.kt (app-wide theme access)
6. ✅ Created: ThemedComponents.kt (reusable themed UI components)
7. TODO: Add DataStore dependency to build.gradle.kts
8. TODO: Update MainActivity to use CompositionLocalProvider
9. TODO: Update each screen to use ThemeViewModel + themed components

*/
