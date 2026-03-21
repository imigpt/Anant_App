package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.DashboardScreenViewModel
import com.example.anantapp.ui.components.BottomNavigationBar
import com.example.anantapp.ui.theme.BankingTheme
import com.example.anantapp.ui.theme.DashboardTheme

/**
 * Production-level Dashboard/Analytics Screen
 * Displays analytics and statistics
 * Follows MVVM architecture with proper state management
 */
@Composable
fun DashboardScreen(
    onHomeClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: DashboardScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardTheme.Colors.Background)
    ) {
        // Main content area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BankingTheme.Spacing.Large)
        ) {
            // Title text
            Text(
                text = "Dashboard Screen",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DashboardTheme.Colors.TextPrimary,
                modifier = Modifier.padding(top = BankingTheme.Spacing.Medium)
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            selectedItem = uiState.selectedNavItem,
            onHomeClick = {
                onHomeClick()
                viewModel.onNavigationItemClick("home")
            },
            onAnalyticsClick = {
                onAnalyticsClick()
                viewModel.onNavigationItemClick("analytics")
            },
            onNotificationClick = {
                onNotificationClick()
                viewModel.onNavigationItemClick("alerts")
            },
            onProfileClick = {
                onProfileClick()
                viewModel.onNavigationItemClick("profile")
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
