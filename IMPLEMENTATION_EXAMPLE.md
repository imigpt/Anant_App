/**
 * IMPLEMENTATION EXAMPLE: ProfileSettingsScreen with MVVM + Themes
 * 
 * This shows exactly how to update your existing screens to use the new
 * MVVM theme architecture
 */

BEFORE (Without Theme System):
──────────────────────────────────

@Composable
fun ProfileSettingsScreen(
    onBackClick: () -> Unit = {},
    viewModel: ProfileSettingsViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Hardcoded colors scattered everywhere
        Image(
            painter = painterResource(id = R.drawable.orange_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile Settings",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black  // Hardcoded
            )
            
            // Hardcoded gradient colors in every place
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF6F00),
                                Color(0xFFE53935)
                            )
                        )
                    )
            )
        }
    }
}


AFTER (With MVVM + Themes):
──────────────────────────────

@Composable
fun ProfileSettingsScreen(
    onBackClick: () -> Unit = {},
    viewModel: ProfileSettingsViewModel = viewModel(),
    themeViewModel: ThemeViewModel = viewModel()  // NEW: Add theme VM
) {
    val uiState by viewModel.state.collectAsState()
    val themeState by themeViewModel.themeState.collectAsState()  // NEW: Get theme
    
    // NEW: Set theme when entering this screen
    LaunchedEffect(Unit) {
        themeViewModel.setProfileScreenTheme()
    }
    
    val theme = themeState.theme  // NEW: Use theme from state
    
    // NEW: Use themed gradient background
    ThemedGradientBackground(theme = theme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // NEW: Use themed title (color comes from theme)
            ThemedTitle("Profile Settings", theme = theme)
            
            // NEW: Use themed card (colors, shadows, border come from theme)
            ThemedCard(theme = theme) {
                Text(
                    text = "Email: ${uiState.email}",
                    color = theme.textSecondary  // Use theme color
                )
            }
            
            // NEW: Use themed button (styling from theme)
            ThemedButton(
                text = "Save Changes",
                onClick = { viewModel.saveProfile() },
                theme = theme
            )
        }
    }
}


QUICK CHECKLIST - Apply to Each Screen:
────────────────────────────────────────

For each screen (ProfileSettingsScreen, VerifyScreen, BalanceScreen, etc.):

✅ Step 1: Add ThemeViewModel parameter
   themeViewModel: ThemeViewModel = viewModel()

✅ Step 2: Observe theme state
   val themeState by themeViewModel.themeState.collectAsState()

✅ Step 3: Set theme on screen entry
   LaunchedEffect(Unit) {
       themeViewModel.setProfileScreenTheme()  // Or setWalletScreenTheme(), etc.
   }

✅ Step 4: Extract theme
   val theme = themeState.theme

✅ Step 5: Use theme colors
   color = theme.textPrimary
   backgroundColor = theme.backgroundColor
   primaryGradient = theme.primaryGradient

✅ Step 6: Use themed components
   ThemedCard(theme = theme)
   ThemedButton(text = "Click", theme = theme)
   ThemedTitle(text = "Title", theme = theme)


Which Theme to Use for Each Screen:
────────────────────────────────────

PROFILE THEME:
├── ProfileSettingsScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()
├── UserDetailsScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()
├── ContactInformationScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()
├── BirthdayCardScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()
├── FamilyDetailsScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()
└── FamilyInformationScreen.kt ✅ Change to: themeViewModel.setProfileScreenTheme()

WALLET THEME:
├── BalanceScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()
├── AddBalanceScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()
├── TransactionScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()
├── DonationHistoryScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()
├── DonorScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()
└── ThankyouScreen.kt ✅ Change to: themeViewModel.setWalletScreenTheme()

VERIFY THEME:
├── VerifyScreen.kt ✅ Change to: themeViewModel.setVerifyScreenTheme()
├── PhotoUploadScreen.kt ✅ Change to: themeViewModel.setVerifyScreenTheme()
├── VerifyBankScreen.kt ✅ Change to: themeViewModel.setVerifyScreenTheme()
└── VerifyAddressScreen.kt ✅ Change to: themeViewModel.setVerifyScreenTheme()

NOMINEE THEME:
├── NomineeDetailsScreen.kt ✅ Change to: themeViewModel.setNomineeScreenTheme()
├── FamilyMemberDetailsScreen.kt ✅ Change to: themeViewModel.setNomineeScreenTheme()
├── AddNomineeCardsScreen.kt ✅ Change to: themeViewModel.setNomineeScreenTheme()
└── NomineeOTPVerificationScreen.kt ✅ Change to: themeViewModel.setNomineeScreenTheme()

*/
