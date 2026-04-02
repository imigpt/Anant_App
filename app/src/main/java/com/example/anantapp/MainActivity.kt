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
import androidx.activity.compose.BackHandler

// Presentation Screens
import com.example.anantapp.presentation.screen.*
// UI Screens
import com.example.anantapp.ui.login.LoginScreen
import com.example.anantapp.ui.onboarding.OnboardingScreen
import com.example.anantapp.ui.theme.AnantAppTheme
import com.example.anantapp.ui.screens.*
import com.example.anantapp.ui.verify.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnantAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Navigation and Flow States
    val currentScreen = remember { mutableStateOf("login") }
    val sosScreenOpen = remember { mutableStateOf(false) }
    val sosHistoryOpen = remember { mutableStateOf(false) }

    // Sub-states for specific screens
    val currentSubScreen = remember { mutableStateOf("") }
    val currentOTPType = remember { mutableStateOf("nominee") }
    val locationShareSuccess = remember { mutableStateOf(false) }

    // Handle system back button navigation
    BackHandler(enabled = true) {
        when {
            sosHistoryOpen.value -> sosHistoryOpen.value = false
            sosScreenOpen.value -> sosScreenOpen.value = false
            currentSubScreen.value.isNotEmpty() -> currentSubScreen.value = "" // Back from sub-screens like OTP
            currentScreen.value == "home" -> {
                @Suppress("DEPRECATION")
                (context as? ComponentActivity)?.finish()
            }
            // Onboarding reverse flow
            currentScreen.value == "insurance" -> currentScreen.value = "enable_location"
            currentScreen.value == "enable_location" -> currentScreen.value = "photo_upload"
            currentScreen.value == "photo_upload" -> currentScreen.value = "verify_address"
            currentScreen.value == "verify_address" -> currentScreen.value = "verify_income"
            currentScreen.value == "verify_income" -> currentScreen.value = "family_details"
            currentScreen.value == "family_details" -> currentScreen.value = "verify_bank"
            currentScreen.value == "verify_bank" -> currentScreen.value = "verify_document"
            currentScreen.value == "verify_document" -> currentScreen.value = "onboarding"
            currentScreen.value == "onboarding" -> currentScreen.value = "login"
            currentScreen.value == "login" -> {
                @Suppress("DEPRECATION")
                (context as? ComponentActivity)?.finish()
            }
            // Default for all other app screens: go back to home
            else -> currentScreen.value = "home"
        }
    }

    Box(modifier = modifier) {
        // --- Overlay Screens ---
        if (sosHistoryOpen.value) {
            SOSHistoryScreen(
                onBackClick = { sosHistoryOpen.value = false },
                onViewMapClick = { sosId -> /* Handle view on map */ },
                onDownloadPDFClick = { sosId -> /* Handle PDF download */ }
            )
        } else if (sosScreenOpen.value) {
            SOSScreen(
                onSOSClick = { sosScreenOpen.value = false },
                onEmergencyTypeClick = { emergencyType -> /* Handle emergency type */ },
                onCheckHistoryClick = {
                    sosScreenOpen.value = false
                    sosHistoryOpen.value = true
                },
                onBackClick = { sosScreenOpen.value = false }
            )
        } else {
            // --- Main App Navigation ---
            when (currentScreen.value) {
                // 1. Onboarding Workflow
                "login" -> {
                    LoginScreen(
                        viewModel = viewModel(),
                        onLoginSuccess = { currentScreen.value = "onboarding" }
                    )
                }
                "onboarding" -> {
                    OnboardingScreen(
                        onOnboardingComplete = { currentScreen.value = "verify_document" }
                    )
                }
                "verify_document" -> {
                    VerifyScreen(
                        viewModel = viewModel(),
                        onSkipClick = { currentScreen.value = "verify_bank" },
                        onVerifySuccess = { currentScreen.value = "verify_bank" }
                    )
                }
                "verify_bank" -> {
                    VerifyBankScreen(
                        viewModel = viewModel(),
                        onSkipClick = { currentScreen.value = "family_details" },
                        onSuccess = { currentScreen.value = "family_details" }
                    )
                }
                "family_details" -> {
                    FamilyDetailsScreen(
                        onSkip = { currentScreen.value = "verify_income" },
                        onSubmit = { currentScreen.value = "verify_income" }
                    )
                }
                "verify_income" -> {
                    VerifyIncomeScreen(
                        onSkip = { currentScreen.value = "verify_address" },
                        onSubmitClick = { currentScreen.value = "verify_address" },
                        onSubmit = { currentScreen.value = "verify_address" }
                    )
                }
                "verify_address" -> {
                    VerifyAddressScreen(
                        viewModel = viewModel(),
                        onSkipClick = { currentScreen.value = "photo_upload" },
                        onSuccess = { currentScreen.value = "photo_upload" }
                    )
                }
                "photo_upload" -> {
                    PhotoUploadScreen(
                        viewModel = viewModel(),
                        onSkipClick = { currentScreen.value = "enable_location" },
                        onSuccess = { currentScreen.value = "enable_location" }
                    )
                }
                "enable_location" -> {
                    EnableLocationScreen(
                        onSkip = { currentScreen.value = "insurance" },
                        onSuccess = { currentScreen.value = "insurance" }
                    )
                }
                "insurance" -> {
                    DeclareInsuranceDetailsScreen(
                        onSkip = { currentScreen.value = "home" },
                        onSubmit = { currentScreen.value = "home" }
                    )
                }

                // 2. Main App Screens
                "home" -> {
                    HomeScreen(
                        onDonorClick = { currentScreen.value = "donation_history" },
                        onHistoryClick = { currentScreen.value = "transaction" },
                        onHomeClick = { currentScreen.value = "home" },
                        onAnalyticsClick = { currentScreen.value = "dashboard" },
                        onNotificationClick = { /* Handle notifications */ },
                        onProfileClick = { currentScreen.value = "profile_settings" },
                        onTransferClick = { currentScreen.value = "add_balance" },
                        onNomineeClick = { currentScreen.value = "add_nominee_cards" },
                        onAddDonationClick = { currentScreen.value = "donor" },
                        onSettingsClick = { currentScreen.value = "wallet_settings" },
                        onQRCodeScannerClick = { currentScreen.value = "qr_scanner" },
                        onGenerateQRCodeClick = { currentScreen.value = "qr_generate" },
                        onGovernmentFundraisersClick = { currentScreen.value = "government_fundraisers" },
                        onSOSClick = { sosScreenOpen.value = true }
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

                // 3. Fundraising & Donation Flow
                "fundraiser", "dashboard_fundraiser" -> {
                    SelectFundraiserCategoryScreen(
                        viewModel = viewModel(),
                        onNextClick = { category, customTitle -> currentScreen.value = "create_fundraiser" },
                        onBackClick = { currentScreen.value = "home" }
                    )
                }
                "create_fundraiser" -> {
                    CreateFundraiserScreen(
                        viewModel = viewModel(),
                        onBackClick = { currentScreen.value = "fundraiser" },
                        onDraftSaved = { currentScreen.value = "home" },
                        onFundraiserCreated = { currentScreen.value = "target_payments" },
                        onNavigateToTargetPayments = { currentScreen.value = "target_payments" }
                    )
                }
                "target_payments" -> {
                    TargetAndPaymentsScreen(
                        viewModel = viewModel(),
                        onBackClick = { currentScreen.value = "create_fundraiser" },
                        onDraftSaved = { currentScreen.value = "home" },
                        onFundraiserPublished = { currentScreen.value = "preview_and_submit" }
                    )
                }
                "preview_and_submit" -> {
                    PreviewAndSubmitScreen(
                        viewModel = viewModel(),
                        onBackClick = { currentScreen.value = "target_payments" },
                        onDraftSaved = { currentScreen.value = "home" },
                        onSubmitSuccess = { currentScreen.value = "home" }
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
                        onPaymentComplete = { currentScreen.value = "home" }
                    )
                }
                "donation_history" -> DonationHistoryScreen(onBackClick = { currentScreen.value = "home" })
                "government_fundraisers" -> {
                    GovernmentFundraisersScreen(
                        onBackClick = { currentScreen.value = "home" },
                        onFinish = { currentScreen.value = "home" }
                    )
                }

                // 4. User Profile & Settings Flow
                "profile_settings" -> {
                    ProfileSettingsScreen(
                        onBackClick = { currentScreen.value = "home" },
                        onContactClick = { currentScreen.value = "contact_information" },
                        onFamilyClick = { currentScreen.value = "family_information" },
                        onBankClick = { /* TODO */ },
                        onInsuranceClick = { /* TODO */ },
                        onMedicalClick = { /* TODO */ },
                        onLogoutClick = { currentScreen.value = "login" }
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
                "wallet_settings" -> {
                    WalletSettingsScreen(
                        onBackClick = { currentScreen.value = "home" },
                        onFAQsClick = { currentScreen.value = "legal_support" }
                    )
                }
                "legal_support" -> {
                    LegalAndSupportScreen(
                        onReadFullTermsClick = { },
                        onViewPrivacyPolicyClick = { },
                        onContactSupportClick = { },
                        onBrowseFAQsClick = { },
                        onHomeClick = { currentScreen.value = "home" },
                        onAcceptTermsClick = { currentScreen.value = "home" }
                    )
                }
                "transaction" -> TransactionScreen(onBackClick = { currentScreen.value = "home" })
                "add_balance" -> AddBalanceScreen(onBackClick = { currentScreen.value = "home" })

                // 5. Nominee & Family Sharing Flow
                "add_nominee_cards" -> {
                    AddNomineeCardsScreen(
                        onAddNomineeClick = { currentScreen.value = "nominee_details" },
                        onAddFamilyMemberClick = { currentScreen.value = "family_member_details" },
                        onShareLocationClick = { currentScreen.value = "share_location" },
                        onSkipClick = { currentScreen.value = "home" }
                    )
                }
                "nominee_details" -> {
                    if (currentSubScreen.value == "otp_verification") {
                        NomineeOTPVerificationScreen(
                            screenType = "nominee",
                            onAddNomineeClick = { currentSubScreen.value = "" },
                            onSendOTPClick = { },
                            onVerifyOTPClick = {
                                currentScreen.value = "home"
                                currentSubScreen.value = ""
                            },
                            onGoBackClick = { currentSubScreen.value = "" }
                        )
                    } else {
                        NomineeDetailsScreen(
                            onSubmitClick = {
                                currentSubScreen.value = "otp_verification"
                                currentOTPType.value = "nominee"
                            },
                            onGoBackClick = { currentScreen.value = "add_nominee_cards" },
                            onUploadClick = { onFilesSelected -> onFilesSelected("Aadhaar_Front.pdf", "Aadhaar_Back.pdf") }
                        )
                    }
                }
                "family_member_details" -> {
                    if (currentSubScreen.value == "otp_verification") {
                        NomineeOTPVerificationScreen(
                            screenType = "family_member",
                            onAddNomineeClick = { currentSubScreen.value = "" },
                            onSendOTPClick = { },
                            onVerifyOTPClick = {
                                currentScreen.value = "add_nominee_cards"
                                currentSubScreen.value = ""
                            },
                            onGoBackClick = { currentSubScreen.value = "" }
                        )
                    } else {
                        FamilyMemberDetailsScreen(
                            onSubmitClick = {
                                currentSubScreen.value = "otp_verification"
                                currentOTPType.value = "family_member"
                            },
                            onGoBackClick = { currentScreen.value = "add_nominee_cards" },
                            onUploadClick = { onFilesSelected -> onFilesSelected("ID_Front.pdf", "ID_Back.pdf") }
                        )
                    }
                }
                "share_location" -> {
                    ShareRealTimeLocationScreen(
                        onBackClick = { currentScreen.value = "add_nominee_cards" },
                        onManageFamilyClick = { currentScreen.value = "manage_family_members" },
                        onShareLocationSuccess = { locationShareSuccess.value = true }
                    )
                }
                "manage_family_members" -> {
                    ManageFamilyMembersScreen(
                        onBackClick = { currentScreen.value = "share_location" },
                        onAddMemberClick = { currentScreen.value = "share_location" },
                        onEditMemberClick = { _, _ -> currentScreen.value = "share_location" }
                    )
                }

                // 6. QR Code Flows
                "qr_scanner", "qr_code_scanner" -> {
                    QRCodeScannerScreen(
                        onQRCodeDetected = { qrCode -> if (qrCode.isNotEmpty()) currentScreen.value = "home" },
                        onBackClick = { currentScreen.value = "home" }
                    )
                }
                "qr_generate" -> {
                    GenerateQRCodeScreen(
                        onBackClick = { currentScreen.value = "home" },
                        onNextClick = { currentScreen.value = "delivery_address" }
                    )
                }
                "delivery_address" -> {
                    DeliveryAddressScreen(
                        onBackClick = { currentScreen.value = "qr_generate" },
                        onNextClick = { currentScreen.value = "order_success" }
                    )
                }
                "order_success" -> {
                    OrderSuccessScreen(
                        onDownloadPDFClick = { },
                        onOrderStatusClick = { currentScreen.value = "order_status" },
                        onHomeClick = { currentScreen.value = "home" }
                    )
                }
                "order_status" -> {
                    OrderStatusScreen(
                        onTrackOnMapClick = { },
                        onDownloadQRClick = { },
                        onViewQRCodeClick = { currentScreen.value = "view_qr" },
                        onHomeClick = { currentScreen.value = "home" }
                    )
                }
                "view_qr" -> {
                    ViewQRCodeScreen(
                        onShareQRClick = { currentScreen.value = "home" },
                        onDownloadQRClick = { currentScreen.value = "home" }
                    )
                }

                else -> {
                    HomeScreen(
                        onDonorClick = { currentScreen.value = "donation_history" },
                        onHistoryClick = { currentScreen.value = "transaction" },
                        onHomeClick = { currentScreen.value = "home" },
                        onAnalyticsClick = { currentScreen.value = "dashboard" },
                        onNotificationClick = { /* Handle notifications */ },
                        onProfileClick = { currentScreen.value = "profile_settings" },
                        onTransferClick = { currentScreen.value = "add_balance" },
                        onNomineeClick = { currentScreen.value = "add_nominee_cards" },
                        onAddDonationClick = { currentScreen.value = "donor" },
                        onSettingsClick = { currentScreen.value = "wallet_settings" },
                        onQRCodeScannerClick = { currentScreen.value = "qr_scanner" },
                        onGenerateQRCodeClick = { currentScreen.value = "qr_generate" },
                        onGovernmentFundraisersClick = { currentScreen.value = "government_fundraisers" },
                        onSOSClick = { sosScreenOpen.value = true }
                    )
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