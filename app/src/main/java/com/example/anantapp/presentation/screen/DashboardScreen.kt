package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onHomeClick: () -> Unit = {},
    onAnalyticsClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // FIX: Used rememberSaveable to survive configuration changes (like screen rotation)
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    var fundraiserFlowState by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            if (selectedItemIndex != 1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Home
                        NavigationItemDashboard(
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home,
                            isSelected = selectedItemIndex == 0,
                            onClick = {
                                selectedItemIndex = 0
                                onHomeClick()
                            }
                        )

                        // Analytics/Chart
                        NavigationItemDashboard(
                            selectedIcon = Icons.Filled.BarChart, // FIX: Changed to Filled
                            unselectedIcon = Icons.Outlined.BarChart,
                            isSelected = selectedItemIndex == 1,
                            onClick = {
                                selectedItemIndex = 1
                                fundraiserFlowState = "select_category"
                                onAnalyticsClick()
                            }
                        )

                        // Notifications
                        NavigationItemDashboard(
                            selectedIcon = Icons.Filled.Notifications, // FIX: Changed to Filled
                            unselectedIcon = Icons.Outlined.Notifications,
                            isSelected = selectedItemIndex == 2,
                            onClick = {
                                selectedItemIndex = 2
                                onNotificationClick()
                            }
                        )

                        // Profile
                        NavigationItemDashboard(
                            selectedIcon = Icons.Filled.Person, // FIX: Changed to Filled
                            unselectedIcon = Icons.Outlined.Person,
                            isSelected = selectedItemIndex == 3,
                            onClick = {
                                selectedItemIndex = 3
                                onProfileClick()
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // FIX: Apply innerPadding to the parent Box so all tabs respect system bounds properly.
        // Scaffold will automatically adjust the bottom padding to 0 when the bottomBar is hidden.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItemIndex) {
                0 -> {
                    HomeScreen(
                        onDonorClick = {},
                        onHistoryClick = {},
                        onHomeClick = onHomeClick,
                        onAnalyticsClick = onAnalyticsClick,
                        onNotificationClick = onNotificationClick,
                        onProfileClick = onProfileClick,
                        onTransferClick = {},
                        onNomineeClick = {},
                        onAddDonationClick = {},
                        onSettingsClick = {},
                        onQRCodeScannerClick = {},
                        onGenerateQRCodeClick = {},
                        onGovernmentFundraisersClick = {}
                    )
                }
                1 -> {
                    when (fundraiserFlowState) {
                        "select_category" -> SelectFundraiserCategoryScreen(
                            onBackClick = { selectedItemIndex = 0 },
                            onNextClick = { _, _ -> fundraiserFlowState = "create" }
                        )
                        "create" -> CreateFundraiserScreen(
                            onBackClick = { fundraiserFlowState = "select_category" },
                            onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                            onFundraiserCreated = { },
                            onNavigateToTargetPayments = { fundraiserFlowState = "target_payments" }
                        )
                        "target_payments" -> TargetAndPaymentsScreen(
                            onBackClick = { fundraiserFlowState = "create" },
                            onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                            onFundraiserPublished = { _ -> fundraiserFlowState = "preview_submit" }
                        )
                        "preview_submit" -> PreviewAndSubmitScreen(
                            onBackClick = { fundraiserFlowState = "target_payments" },
                            onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                            onSubmitSuccess = { _ -> fundraiserFlowState = null; selectedItemIndex = 0 }
                        )
                        else -> SelectFundraiserCategoryScreen(
                            onBackClick = { selectedItemIndex = 0 },
                            onNextClick = { _, _ -> fundraiserFlowState = "create" }
                        )
                    }
                }
                2 -> {
                    // FIX: Added a temporary placeholder so the screen isn't completely blank
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Notifications Screen Coming Soon")
                    }
                }
                3 -> {
                    ProfileSettingsScreen(
                        onBackClick = { selectedItemIndex = 0 },
                        onContactClick = {},
                        onFamilyClick = {},
                        onBankClick = {},
                        onInsuranceClick = {},
                        onMedicalClick = {},
                        onLogoutClick = { selectedItemIndex = 0 }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationItemDashboard(
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedColor = Color(0xFFA600FF)
    val unselectedColor = Color(0xFFBDBDBD)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = if (isSelected) selectedColor else unselectedColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}