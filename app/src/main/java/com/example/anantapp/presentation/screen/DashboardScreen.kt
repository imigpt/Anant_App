package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
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
    // State to track which tab is currently selected
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    // State to track fundraiser flow (none = not in fundraiser flow, select_category = selecting category, create = creating)
    var fundraiserFlowState by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color.White, // Makes the whole background blank white
        bottomBar = {
            // FIX: Only show the bottom navigation bar if we are NOT on the Analytics tab (index 1)
            if (selectedItemIndex != 1) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    // Navigation items container
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
                            selectedIcon = Icons.Outlined.BarChart,
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
                            selectedIcon = Icons.Outlined.Notifications,
                            unselectedIcon = Icons.Outlined.Notifications,
                            isSelected = selectedItemIndex == 2,
                            onClick = {
                                selectedItemIndex = 2
                                onNotificationClick()
                            }
                        )

                        // Profile
                        NavigationItemDashboard(
                            selectedIcon = Icons.Outlined.Person,
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
        // Main content area that switches based on the selected tab
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (selectedItemIndex) {
                0 -> Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
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
                        onGovernmentFundraisersClick = {}
                    )
                }
                1 -> {
                    when (fundraiserFlowState) {
                        "select_category" -> Box(modifier = Modifier.fillMaxSize()) {
                            SelectFundraiserCategoryScreen(
                                onBackClick = { selectedItemIndex = 0 },
                                onNextClick = { _, _ -> fundraiserFlowState = "create" }
                            )
                        }
                        "create" -> Box(modifier = Modifier.fillMaxSize()) {
                            CreateFundraiserScreen(
                                onBackClick = { fundraiserFlowState = "select_category" },
                                onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                                onFundraiserCreated = { },
                                onNavigateToTargetPayments = { fundraiserFlowState = "target_payments" }
                            )
                        }
                        "target_payments" -> Box(modifier = Modifier.fillMaxSize()) {
                            TargetAndPaymentsScreen(
                                onBackClick = { fundraiserFlowState = "create" },
                                onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                                onFundraiserPublished = { _ -> fundraiserFlowState = "preview_submit" }
                            )
                        }
                        "preview_submit" -> Box(modifier = Modifier.fillMaxSize()) {
                            PreviewAndSubmitScreen(
                                onBackClick = { fundraiserFlowState = "target_payments" },
                                onDraftSaved = { fundraiserFlowState = null; selectedItemIndex = 0 },
                                onSubmitSuccess = { _ -> fundraiserFlowState = null; selectedItemIndex = 0 }
                            )
                        }
                        else -> Box(modifier = Modifier.fillMaxSize()) {
                            SelectFundraiserCategoryScreen(
                                onBackClick = { selectedItemIndex = 0 },
                                onNextClick = { _, _ -> fundraiserFlowState = "create" }
                            )
                        }
                    }
                }
                2 -> {
                    // Placeholder for Notifications Screen
                }
                3 -> Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
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
    val selectedColor = Color(0xFFA600FF) // The exact vibrant purple
    val unselectedColor = Color(0xFFBDBDBD) // Light outline gray

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(
                indication = null, // Removes the ripple effect for a cleaner look
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(16.dp) // Creates a comfortable touch target area around the icon
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = null,
            modifier = Modifier.size(28.dp), // Slightly larger to match the image
            tint = if (isSelected) selectedColor else unselectedColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}