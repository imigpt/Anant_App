package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.BalanceScreenViewModel
import com.example.anantapp.ui.components.BottomNavigationBar
import com.example.anantapp.ui.components.DecorativeCircle
import com.example.anantapp.ui.components.GradientSurface
import com.example.anantapp.ui.theme.BalanceTheme
import com.example.anantapp.ui.theme.BankingTheme

/**
 * Production-level Balance Screen
 * Shows wallet balance with simple gradient
 * Follows MVVM architecture with proper state management
 */
@Composable
fun BalanceScreen(
    onHomeClick: () -> Unit = {},
    onAnalyticsClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    viewModel: BalanceScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Main gradient background with circular element
        GradientSurface(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            colors = listOf(
                BalanceTheme.Colors.GradientPink,
                BalanceTheme.Colors.GradientMagenta,
                BalanceTheme.Colors.GradientPurple,
                BalanceTheme.Colors.GradientDarkPurple,
                BalanceTheme.Colors.GradientBlack
            )
        ) {
            // Decorative circles
            DecorativeCircle(
                size = 250.dp,
                color = BalanceTheme.Colors.GradientPink.copy(alpha = 0.2f),
                offsetX = (-60).dp,
                offsetY = (-60).dp,
                modifier = Modifier.align(Alignment.TopStart)
            )

            DecorativeCircle(
                size = 150.dp,
                color = Color.Black.copy(alpha = 0.15f),
                offsetX = 30.dp,
                offsetY = 30.dp,
                modifier = Modifier.align(Alignment.BottomEnd)
            )

            // Content area with balance info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BankingTheme.Spacing.Large)
                    .padding(top = BankingTheme.Spacing.Huge, bottom = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large circular indicator (top decoration)
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.Center)
                            .background(
                                color = Color.Black,
                                shape = CircleShape
                            )
                    )
                }

                Spacer(modifier = Modifier.height(BankingTheme.Spacing.ExtraLarge))

                // Greeting text
                Text(
                    text = "Hello Mahendra,",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = BalanceTheme.Colors.TextOnGradient,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(BankingTheme.Spacing.Small))

                // Subtitle
                Text(
                    text = "Your available balance",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = BalanceTheme.Colors.TextOnGradient.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(BankingTheme.Spacing.Medium))

                // Balance amount
                Text(
                    text = "₹ 10,000",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = BalanceTheme.Colors.TextOnGradient,
                    textAlign = TextAlign.Center
                )
            }
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
