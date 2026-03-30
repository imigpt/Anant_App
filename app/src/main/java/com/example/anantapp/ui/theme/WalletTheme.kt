package com.example.anantapp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Wallet Screen Theme - Purple & Pink gradient theme
 * Used for: BalanceScreen, AddBalanceScreen, TransactionScreen, DonationHistoryScreen
 */
object WalletTheme {
    // Primary Colors
    val primaryGradient = listOf(
        Color(0xFF9500FF),  // Purple
        Color(0xFFFF1E4F)   // Pink
    )
    
    val accentColor = Color(0xFFFF6264)  // Light Red
    val backgroundColor = Color(0xFFFAFAFA)
    val cardBackground = Color.White.copy(alpha = 0.45f)
    val textPrimary = Color(0xFF1a1a1a)
    val textSecondary = Color(0xFF666666)
    
    // Component Styles
    val cornerRadius: Dp = 24.dp
    val elevation: Dp = 10.dp
    val shadowColor = Color.Black.copy(alpha = 0.15f)
    
    // Specific to Wallet
    val incomeColor = Color(0xFF4CAF50)   // Green for incoming
    val expenseColor = Color(0xFFf44336)  // Red for outgoing
    val balanceColor = Color(0xFF9500FF)  // Purple for balance
}
