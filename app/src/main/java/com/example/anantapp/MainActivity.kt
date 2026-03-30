package com.example.anantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.screen.BalanceScreen
import com.example.anantapp.presentation.screen.DashboardScreen
import com.example.anantapp.presentation.screen.DeclareInsuranceDetailsScreen
import com.example.anantapp.presentation.screen.DeliveryAddressScreen
import com.example.anantapp.presentation.screen.OrderSuccessScreen
import com.example.anantapp.presentation.screen.OrderStatusScreen
import com.example.anantapp.presentation.screen.ViewQRCodeScreen
import com.example.anantapp.presentation.screen.GenerateQRCodeInfoScreen
import com.example.anantapp.presentation.screen.DonationHistoryScreen
import com.example.anantapp.presentation.screen.DonorScreen
import com.example.anantapp.presentation.screen.EnableLocationScreen
import com.example.anantapp.presentation.screen.FamilyDetailsScreen
import com.example.anantapp.presentation.screen.GenerateQRCodeScreen
import com.example.anantapp.presentation.screen.GovernmentFundraisersScreen
import com.example.anantapp.presentation.screen.HomeScreen
import com.example.anantapp.presentation.screen.PaymentMethodScreen
import com.example.anantapp.presentation.screen.PreviewAndSubmitScreen
import com.example.anantapp.presentation.screen.SelectFundraiserCategoryScreen
import com.example.anantapp.presentation.screen.CreateFundraiserScreen
import com.example.anantapp.presentation.screen.TargetAndPaymentsScreen
import com.example.anantapp.presentation.screen.ProfileSettingsScreen
import com.example.anantapp.presentation.screen.FamilyInformationScreen
import com.example.anantapp.presentation.screen.ContactInformationScreen
import com.example.anantapp.presentation.screen.TransactionScreen
import com.example.anantapp.presentation.screen.UserDetailsScreen
import com.example.anantapp.presentation.screen.VerifyIncomeScreen
import com.example.anantapp.ui.login.LoginScreen
import com.example.anantapp.ui.onboarding.OnboardingScreen
import com.example.anantapp.ui.theme.AnantAppTheme
import com.example.anantapp.ui.screens.QRCodeScannerScreen
import com.example.anantapp.ui.screens.AddNomineeCardsScreen
import com.example.anantapp.ui.screens.NomineeDetailsScreen
import com.example.anantapp.ui.screens.NomineeOTPVerificationScreen
import com.example.anantapp.ui.screens.FamilyMemberDetailsScreen
import com.example.anantapp.ui.verify.PhotoUploadScreen
import com.example.anantapp.presentation.screen.ShareRealTimeLocationScreen
import com.example.anantapp.presentation.screen.ManageFamilyMembersScreen
import com.example.anantapp.presentation.screen.LocationSharedSuccessScreen
import com.example.anantapp.presentation.screen.ThankyouScreen
import com.example.anantapp.presentation.screen.LiveLocationMapScreen
import com.example.anantapp.ui.verify.VerifyAddressScreen
import com.example.anantapp.ui.verify.VerifyBankScreen
import com.example.anantapp.ui.verify.VerifyScreen
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.BackHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnantAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // innerPadding is passed as a modifier here
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val orderSuccessComplete = remember { mutableStateOf(true) }
    val orderStatusComplete = remember { mutableStateOf(true) }
    val viewQRCodeComplete = remember { mutableStateOf(true) }
    val generateQRInfoComplete = remember { mutableStateOf(true) }
    val generateQRCodeComplete = remember { mutableStateOf(true) }
    val userDetailsComplete = remember { mutableStateOf(true) }
    val deliveryAddressComplete = remember { mutableStateOf(true) }
    val onboardingComplete = remember { mutableStateOf(true) }
    val loginComplete = remember { mutableStateOf(true) }
    val documentVerified = remember { mutableStateOf(true) }
    val bankVerified = remember { mutableStateOf(true) }
    val addressVerified = remember { mutableStateOf(true) }
    val photoUploaded = remember { mutableStateOf(true) }
    val locationVerified = remember { mutableStateOf(true) }
    val familyDetailsComplete = remember { mutableStateOf(true) }
    val insuranceDetailsComplete = remember { mutableStateOf(true) }
    val verifyIncomeComplete = remember { mutableStateOf(true) }
    val governmentFundraisersComplete = remember { mutableStateOf(true) }
    val currentScreen = remember { mutableStateOf("share_location") } // Start with Share Real Time Location screen
    val locationShareSuccess = remember { mutableStateOf(false) } // Track if location was shared
    val viewMapOpen = remember { mutableStateOf(false) } // Track if viewing map
    val currentSubScreen = remember { mutableStateOf("") } // For sub-screens within nominee flow
    val currentOTPType = remember { mutableStateOf("nominee") } // Track OTP screen type: "nominee" or "family_member"

    // Handle system back button navigation
    BackHandler(enabled = true) {
        when (currentScreen.value) {
            "home" -> {
                // App will close when user presses back from home
                @Suppress("DEPRECATION")
                (context as? ComponentActivity)?.finish()
            }
            "donor" -> currentScreen.value = "home"
            "donation_history" -> currentScreen.value = "home"
            "payment" -> currentScreen.value = "donor"
            "profile_settings" -> currentScreen.value = "home"
            "transaction" -> currentScreen.value = "home"
            "share_location" -> {
                if (locationShareSuccess.value && viewMapOpen.value) {
                    viewMapOpen.value = false
                } else if (locationShareSuccess.value) {
                    currentScreen.value = "add_nominee_cards"
                    locationShareSuccess.value = false
                } else {
                    currentScreen.value = "add_nominee_cards"
                }
            }
            "add_nominee_cards" -> currentScreen.value = "share_location"
            "nominee_details" -> currentScreen.value = "add_nominee_cards"
            "family_member_details" -> currentScreen.value = "add_nominee_cards"
            "manage_family_members" -> currentScreen.value = "share_location"
            else -> {
                // Default behavior
                @Suppress("DEPRECATION")
                (context as? ComponentActivity)?.finish()
            }
        }
    }

    // Wrap the routing logic in a Box that uses the passed modifier.
    // This ensures every screen respects the safe-area padding from the Scaffold.
    Box(modifier = modifier) {
        when {
            // Show Share Real Time Location first if currentScreen is share_location
            currentScreen.value == "share_location" && locationShareSuccess.value && viewMapOpen.value -> {
                LiveLocationMapScreen(
                    onBackClick = {
                        viewMapOpen.value = false
                        locationShareSuccess.value = true
                    },
                    onNavigateClick = {
                        // Handle navigate to location
                    },
                    onShowMyLocationClick = {
                        // Handle show my location
                    },
                    onShowAllMembersClick = {
                        // Handle show all members
                    }
                )
            }

            currentScreen.value == "share_location" && locationShareSuccess.value && !viewMapOpen.value -> {
                LocationSharedSuccessScreen(
                    onBackClick = {
                        currentScreen.value = "add_nominee_cards"
                        locationShareSuccess.value = false
                    },
                    onManageAccessClick = {
                        // Handle manage access
                    },
                    onViewOnMapClick = {
                        viewMapOpen.value = true
                    },
                    onDoneClick = {
                        currentScreen.value = "home"
                        locationShareSuccess.value = false
                    }
                )
            }

            currentScreen.value == "share_location" && (
                orderSuccessComplete.value &&
                orderStatusComplete.value &&
                viewQRCodeComplete.value &&
                generateQRInfoComplete.value &&
                generateQRCodeComplete.value &&
                userDetailsComplete.value &&
                deliveryAddressComplete.value &&
                onboardingComplete.value &&
                loginComplete.value &&
                documentVerified.value &&
                bankVerified.value &&
                addressVerified.value &&
                photoUploaded.value &&
                locationVerified.value &&
                familyDetailsComplete.value &&
                insuranceDetailsComplete.value &&
                verifyIncomeComplete.value &&
                governmentFundraisersComplete.value
            ) && !locationShareSuccess.value -> {
                ShareRealTimeLocationScreen(
                    onBackClick = {
                        currentScreen.value = "add_nominee_cards"
                    },
                    onManageFamilyClick = {
                        // Navigate to manage family members
                        currentScreen.value = "manage_family_members"
                    },
                    onShareLocationSuccess = {
                        // Navigate to success screen
                        locationShareSuccess.value = true
                    }
                )
            }

            currentScreen.value == "add_nominee_cards" && (
                orderSuccessComplete.value &&
                orderStatusComplete.value &&
                viewQRCodeComplete.value &&
                generateQRInfoComplete.value &&
                generateQRCodeComplete.value &&
                userDetailsComplete.value &&
                deliveryAddressComplete.value &&
                onboardingComplete.value &&
                loginComplete.value &&
                documentVerified.value &&
                bankVerified.value &&
                addressVerified.value &&
                photoUploaded.value &&
                locationVerified.value &&
                familyDetailsComplete.value &&
                insuranceDetailsComplete.value &&
                verifyIncomeComplete.value &&
                governmentFundraisersComplete.value
            ) -> {
                AddNomineeCardsScreen(
                    onAddNomineeClick = {
                        // Navigate to Nominee Details
                        currentScreen.value = "nominee_details"
                    },
                    onAddFamilyMemberClick = {
                        // Navigate to Family Member Details
                        currentScreen.value = "family_member_details"
                    },
                    onShareLocationClick = {
                        // Navigate to Share Location
                        currentScreen.value = "share_location"
                    },
                    onSkipClick = {
                        // Handle skip
                        currentScreen.value = "home"
                    }
                )
            }

            !orderSuccessComplete.value -> {
                OrderSuccessScreen(
                    onDownloadPDFClick = {
                        // Handle PDF download
                    },
                    onOrderStatusClick = {
                        orderSuccessComplete.value = true
                    },
                    onHomeClick = {
                        orderSuccessComplete.value = true
                    }
                )
            }

            !orderStatusComplete.value -> {
                OrderStatusScreen(
                    onTrackOnMapClick = {
                        // Handle track on map
                    },
                    onDownloadQRClick = {
                        // Handle download QR
                    },
                    onViewQRCodeClick = {
                        viewQRCodeComplete.value = true
                    },
                    onHomeClick = {
                        currentScreen.value = "home"
                        orderStatusComplete.value = true
                    }
                )
            }

            !viewQRCodeComplete.value -> {
                ViewQRCodeScreen(
                    onShareQRClick = {
                        generateQRInfoComplete.value = true
                    },
                    onDownloadQRClick = {
                        generateQRInfoComplete.value = true
                    }
                )
            }

            !generateQRInfoComplete.value -> {
                GenerateQRCodeInfoScreen(
                    onShareQRClick = {
                        // Handle share QR
                    },
                    onDownloadQRClick = {
                        // Handle download QR
                    }
                )
            }

            !generateQRCodeComplete.value -> {
                GenerateQRCodeScreen(
                    onBackClick = {
                        // App initialization - no screen to go back to
                    },
                    onNextClick = { formState ->
                        // QR Code form submitted - proceed to user details screen
                        generateQRCodeComplete.value = true
                    }
                )
            }

            !userDetailsComplete.value -> {
                UserDetailsScreen(
                    onBackClick = {
                        generateQRCodeComplete.value = false
                    },
                    onNextClick = { formState ->
                        // User details form submitted - proceed to delivery address screen
                        userDetailsComplete.value = true
                    }
                )
            }

            !deliveryAddressComplete.value -> {
                DeliveryAddressScreen(
                    onBackClick = {
                        userDetailsComplete.value = false
                    },
                    onNextClick = { formState ->
                        // Delivery address form submitted - proceed to next flow
                        deliveryAddressComplete.value = true
                    }
                )
            }

            !onboardingComplete.value -> {
                OnboardingScreen(
                    onOnboardingComplete = {
                        onboardingComplete.value = true
                    }
                )
            }

            !loginComplete.value -> {
                LoginScreen(
                    viewModel = viewModel(),
                    onLoginSuccess = {
                        loginComplete.value = true
                    }
                )
            }

            !documentVerified.value -> {
                VerifyScreen(
                    viewModel = viewModel(),
                    onSkipClick = {
                        loginComplete.value = false
                    },
                    onVerifySuccess = {
                        documentVerified.value = true
                    }
                )
            }

            !bankVerified.value -> {
                VerifyBankScreen(
                    viewModel = viewModel(),
                    onSkipClick = {
                        documentVerified.value = false
                    },
                    onSuccess = {
                        bankVerified.value = true
                    }
                )
            }

            !addressVerified.value -> {
                VerifyAddressScreen(
                    viewModel = viewModel(),
                    onSkipClick = {
                        bankVerified.value = false
                    },
                    onSuccess = {
                        addressVerified.value = true
                    }
                )
            }

            !photoUploaded.value -> {
                PhotoUploadScreen(
                    viewModel = viewModel(),
                    onSkipClick = {
                        addressVerified.value = false
                    },
                    onSuccess = {
                        photoUploaded.value = true
                    }
                )
            }

            !locationVerified.value -> {
                EnableLocationScreen(
                    onSkip = {
                        photoUploaded.value = false
                    },
                    onSuccess = {
                        locationVerified.value = true
                    }
                )
            }

            !familyDetailsComplete.value -> {
                FamilyDetailsScreen(
                    onSkip = {
                        locationVerified.value = false
                    },
                    onSubmit = {
                        familyDetailsComplete.value = true
                    }
                )
            }

            !insuranceDetailsComplete.value -> {
                DeclareInsuranceDetailsScreen(
                    onSkip = {
                        familyDetailsComplete.value = false
                    },
                    onSubmit = {
                        insuranceDetailsComplete.value = true
                    }
                )
            }

            !verifyIncomeComplete.value -> {
                VerifyIncomeScreen(
                    onSkip = {
                        insuranceDetailsComplete.value = false
                    },
                    onSubmit = {
                        verifyIncomeComplete.value = true
                    }
                )
            }

            !governmentFundraisersComplete.value -> {
                GovernmentFundraisersScreen(
                    onBackClick = {
                        verifyIncomeComplete.value = false
                    },
                    onFinish = {
                        governmentFundraisersComplete.value = true
                    }
                )
            }

            else -> {
                // All verifications complete - navigate based on current screen
                when (currentScreen.value) {
                    "qr_scanner" -> {
                        QRCodeScannerScreen(
                            onQRCodeDetected = { qrCode ->
                                // Handle detected QR code
                                if (qrCode.isNotEmpty()) {
                                    currentScreen.value = "home"
                                }
                            }
                        )
                    }

                    "fundraiser" -> {
                        SelectFundraiserCategoryScreen(
                            viewModel = viewModel(),
                            onNextClick = { category, customTitle ->
                                currentScreen.value = "create_fundraiser"
                            },
                            onBackClick = {
                                currentScreen.value = "home"
                            }
                        )
                    }

                    "create_fundraiser" -> {
                        CreateFundraiserScreen(
                            viewModel = viewModel(),
                            onBackClick = {
                                currentScreen.value = "fundraiser"
                            },
                            onDraftSaved = {
                                currentScreen.value = "home"
                            },
                            onFundraiserCreated = { fundraiserId ->
                                currentScreen.value = "target_payments"
                            },
                            onNavigateToTargetPayments = {
                                currentScreen.value = "target_payments"
                            }
                        )
                    }

                    "target_payments" -> {
                        TargetAndPaymentsScreen(
                            viewModel = viewModel(),
                            onBackClick = {
                                currentScreen.value = "create_fundraiser"
                            },
                            onDraftSaved = {
                                currentScreen.value = "home"
                            },
                            onFundraiserPublished = {
                                currentScreen.value = "preview_and_submit"
                            }
                        )
                    }

                    "preview_and_submit" -> {
                        PreviewAndSubmitScreen(
                            viewModel = viewModel(),
                            onBackClick = {
                                currentScreen.value = "target_payments"
                            },
                            onDraftSaved = {
                                currentScreen.value = "home"
                            },
                            onSubmitSuccess = { fundraiserId ->
                                currentScreen.value = "home"
                            }
                        )
                    }

                    "home" -> {
                        HomeScreen(
                            onDonorClick = { currentScreen.value = "donor" },
                            onHistoryClick = { currentScreen.value = "donation_history" },
                            onHomeClick = { currentScreen.value = "home" },
                            onAnalyticsClick = { currentScreen.value = "dashboard" },
                            onNotificationClick = { /* Handle notifications */ },
                            onProfileClick = { currentScreen.value = "profile_settings" }
                        )
                    }

                    "dashboard" -> {
                        DashboardScreen(
                            onHomeClick = { currentScreen.value = "home" },
                            onAnalyticsClick = { currentScreen.value = "dashboard" },
                            onNotificationClick = { /* Handle notifications */ },
                            onProfileClick = { currentScreen.value = "profile_settings" }
                        )
                    }

                    "balance" -> {
                        BalanceScreen(
                            onHomeClick = { currentScreen.value = "home" },
                            onAnalyticsClick = { currentScreen.value = "dashboard" },
                            onNotificationClick = { /* Handle notifications */ },
                            onProfileClick = { currentScreen.value = "profile_settings" }
                        )
                    }

                    "donor" -> {
                        DonorScreen(
                            onBackClick = { currentScreen.value = "home" },
                            onPaymentClick = { currentScreen.value = "payment" }
                        )
                    }

                    "payment" -> {
                        PaymentMethodScreen(
                            onBackClick = { currentScreen.value = "donor" },
                            onPaymentComplete = {
                                // Handle payment completion
                                currentScreen.value = "home"
                            }
                        )
                    }

                    "donation_history" -> {
                        DonationHistoryScreen(
                            onBackClick = { currentScreen.value = "home" }
                        )
                    }

                    "profile_settings" -> {
                        ProfileSettingsScreen(
                            onBackClick = { currentScreen.value = "home" },
                            onContactClick = { currentScreen.value = "contact_information" },
                            onFamilyClick = { currentScreen.value = "family_information" },
                            onBankClick = { /* TODO: Navigate to bank accounts screen */ },
                            onInsuranceClick = { /* TODO: Navigate to insurance screen */ },
                            onMedicalClick = { /* TODO: Navigate to medical info screen */ },
                            onLogoutClick = { currentScreen.value = "home" }
                        )
                    }
                    "family_information" -> {
                        FamilyInformationScreen(
                            onBackClick = { currentScreen.value = "profile_settings" },
                            onUpdateClick = { currentScreen.value = "profile_settings" }
                        )
                    }

                    "contact_information" -> {
                        ContactInformationScreen(
                            onBackClick = { currentScreen.value = "profile_settings" },
                            onUpdateClick = { currentScreen.value = "profile_settings" }
                        )
                    }

                    "transaction" -> {
                        TransactionScreen(
                            onBackClick = { currentScreen.value = "home" }
                        )
                    }

                    "manage_family_members" -> {
                        ManageFamilyMembersScreen(
                            onBackClick = { currentScreen.value = "share_location" },
                            onAddMemberClick = {
                                // Navigate to add family member form
                                // For now, just return to share location
                                currentScreen.value = "share_location"
                            },
                            onEditMemberClick = { memberId, memberName ->
                                // Navigate to edit family member form
                                // For now, just return to share location
                                currentScreen.value = "share_location"
                            }
                        )
                    }

                    "nominee_details" -> {
                        when (currentSubScreen.value) {
                            "otp_verification" -> {
                                NomineeOTPVerificationScreen(
                                    screenType = "nominee",
                                    onAddNomineeClick = {
                                        // Reset to form
                                        currentSubScreen.value = ""
                                    },
                                    onSendOTPClick = {
                                        // Handle send OTP
                                    },
                                    onVerifyOTPClick = {
                                        // Handle verify OTP - completion
                                        currentScreen.value = "home"
                                        currentSubScreen.value = ""
                                    },
                                    onGoBackClick = {
                                        currentSubScreen.value = ""
                                    }
                                )
                            }
                            else -> {
                                NomineeDetailsScreen(
                                    onSubmitClick = {
                                        // Navigate to OTP Verification
                                        currentSubScreen.value = "otp_verification"
                                        currentOTPType.value = "nominee"
                                    },
                                    onGoBackClick = {
                                        currentScreen.value = "add_nominee_cards"
                                    },
                                    onUploadClick = { onFilesSelected ->
                                        // Simulate file selection for front and back side of ID
                                        // In production, you would use ActivityResultContracts to launch file picker
                                        onFilesSelected("Aadhaar_Front.pdf", "Aadhaar_Back.pdf")
                                    }
                                )
                            }
                        }
                    }

                    "family_member_details" -> {
                        when (currentSubScreen.value) {
                            "otp_verification" -> {
                                NomineeOTPVerificationScreen(
                                    screenType = "family_member",
                                    onAddNomineeClick = {
                                        // Reset to form
                                        currentSubScreen.value = ""
                                    },
                                    onSendOTPClick = {
                                        // Handle send OTP
                                    },
                                    onVerifyOTPClick = {
                                        // Handle verify OTP - completion
                                        currentScreen.value = "add_nominee_cards"
                                        currentSubScreen.value = ""
                                    },
                                    onGoBackClick = {
                                        currentSubScreen.value = ""
                                    }
                                )
                            }
                            else -> {
                                FamilyMemberDetailsScreen(
                                    onSubmitClick = {
                                        // Navigate to OTP Verification
                                        currentSubScreen.value = "otp_verification"
                                        currentOTPType.value = "family_member"
                                    },
                                    onGoBackClick = {
                                        currentScreen.value = "add_nominee_cards"
                                    },
                                    onUploadClick = { onFilesSelected ->
                                        // Simulate file selection for front and back side of ID
                                        // In production, you would use ActivityResultContracts to launch file picker
                                        onFilesSelected("ID_Front.pdf", "ID_Back.pdf")
                                    }
                                )
                            }
                        }
                    }

                    else -> {
                        // Default to fundraiser screen
                        SelectFundraiserCategoryScreen(
                            viewModel = viewModel(),
                            onNextClick = { category, customTitle ->
                                currentScreen.value = "create_fundraiser"
                            },
                            onBackClick = {
                                currentScreen.value = "home"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    AnantAppTheme {
        VerifyScreen(
            viewModel = viewModel(),
            onSkipClick = {},
            onVerifySuccess = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AnantAppTheme {
        LoginScreen(
            viewModel = viewModel(),
            onLoginSuccess = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    AnantAppTheme {
        DashboardScreen(
            onHomeClick = {},
            onAnalyticsClick = {},
            onNotificationClick = {},
            onProfileClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AnantAppTheme {
        HomeScreen(
            onDonorClick = {},
            onHistoryClick = {},
            onHomeClick = {},
            onAnalyticsClick = {},
            onNotificationClick = {},
            onProfileClick = {}
        )
    }
}
