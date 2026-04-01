package com.example.anantapp.feature.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AttachMoney
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
import com.example.anantapp.presentation.screen.BalanceScreen
import com.example.anantapp.feature.fundraiser.presentation.screens.SelectFundraiserCategoryScreen

@Composable
fun DashboardScreen(
    onHomeClick: () -> Unit = {},
    onAnalyticsClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    // State to track which tab is currently selected
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
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

                    // Fundraiser
                    NavigationItemDashboard(
                        selectedIcon = Icons.Outlined.AttachMoney,
                        unselectedIcon = Icons.Outlined.AttachMoney,
                        isSelected = selectedItemIndex == 1,
                        onClick = {
                            selectedItemIndex = 1
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
    ) { innerPadding ->
        // Main content area that switches based on the selected tab
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedItemIndex) {
                0 -> BalanceScreen(
                    onHomeClick = onHomeClick,
                    onAnalyticsClick = onAnalyticsClick,
                    onNotificationClick = onNotificationClick,
                    onProfileClick = onProfileClick
                )
                1 -> SelectFundraiserCategoryScreen(
                    onBackClick = { selectedItemIndex = 0 },
                    onNextClick = { selectedCategory, customTitle ->
                        // Handle next action for fundraiser
                    }
                )
                2 -> {}
                3 -> {}
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
    val selectedColor = Color(0xFFA600FF) // Vibrant purple
    val unselectedColor = Color(0xFFBDBDBD) // Light gray

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
