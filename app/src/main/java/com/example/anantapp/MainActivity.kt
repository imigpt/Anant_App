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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.screen.BalanceScreen
import com.example.anantapp.presentation.screen.DashboardScreen
import com.example.anantapp.presentation.screen.DeclareInsuranceDetailsScreen
import com.example.anantapp.presentation.screen.DonationHistoryScreen
import com.example.anantapp.presentation.screen.DonorScreen
import com.example.anantapp.presentation.screen.EnableLocationScreen
import com.example.anantapp.presentation.screen.FamilyDetailsScreen
import com.example.anantapp.presentation.screen.GovernmentFundraisersScreen
import com.example.anantapp.presentation.screen.HomeScreen
import com.example.anantapp.presentation.screen.PaymentMethodScreen
import com.example.anantapp.presentation.screen.SelectFundraiserCategoryScreen
import com.example.anantapp.presentation.screen.CreateFundraiserScreen
import com.example.anantapp.presentation.screen.TransactionScreen
import com.example.anantapp.presentation.screen.VerifyIncomeScreen
import com.example.anantapp.ui.login.LoginScreen
import com.example.anantapp.ui.onboarding.OnboardingScreen
import com.example.anantapp.ui.theme.AnantAppTheme
import com.example.anantapp.ui.verify.PhotoUploadScreen
import com.example.anantapp.ui.verify.VerifyAddressScreen
import com.example.anantapp.ui.verify.VerifyBankScreen
import com.example.anantapp.ui.verify.VerifyScreen

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
    val currentScreen = remember { mutableStateOf("fundraiser") } // Start with fundraiser screen

    // Wrap the routing logic in a Box that uses the passed modifier.
    // This ensures every screen respects the safe-area padding from the Scaffold.
    Box(modifier = modifier) {
        when {
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
                            onProfileClick = { /* Handle profile */ }
                        )
                    }

                    "dashboard" -> {
                        DashboardScreen(
                            onHomeClick = { currentScreen.value = "home" },
                            onAnalyticsClick = { currentScreen.value = "dashboard" },
                            onNotificationClick = { /* Handle notifications */ },
                            onProfileClick = { /* Handle profile */ }
                        )
                    }

                    "balance" -> {
                        BalanceScreen(
                            onHomeClick = { currentScreen.value = "home" },
                            onAnalyticsClick = { currentScreen.value = "dashboard" },
                            onNotificationClick = { /* Handle notifications */ },
                            onProfileClick = { /* Handle profile */ }
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

                    "transaction" -> {
                        TransactionScreen(
                            onBackClick = { currentScreen.value = "home" }
                        )
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
