package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define the precise gradient colors from your image
val PurpleToPinkGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF9500FF), // Deep Purple (top)
        Color(0xFFFF6264)  // Vibrate Red-Pink (bottom)
    )
)

@Composable
fun WalletSettingsScreen(
    onBackClick: () -> Unit = {},
    onFAQsClick: () -> Unit = {}
) {
    // Standard full-screen background
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD)) // Light off-white
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        // Gradient Back Button (Uses image design language)
        Box(
            modifier = Modifier
                .size(40.dp)
                // 1. Create the border with the gradient brush
                .border(2.dp, PurpleToPinkGradient, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            // 2. Icon is drawn and then a gradient 'mask' is applied over it
            // We use Box instead of Canvas because Canvas doesn't allow Composable content (like Icon)
            Box(modifier = Modifier
                .size(24.dp)
                // This advanced technique applies the gradient directly to the icon shape
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent() // Draw the actual icon content
                        drawRect(
                            brush = PurpleToPinkGradient,
                            blendMode = BlendMode.SrcIn // Mask the gradient onto the icon shape
                        )
                    }
                }
            ) {
                // A very thin chevron like in your image
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = Color.White // Set to White, but the SrcIn blend mode makes it gradient
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Main Title
        Text(
            text = "Wallet Settings & Policy",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF130138),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Settings List (Stays full width)
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            SettingMenuItem(Icons.Default.NotificationsActive, "Auto-Debit Setup")
            SettingMenuItem(Icons.Default.AccountBalanceWallet, "NACH Top-Up Threshold")
            SettingMenuItem(Icons.Default.AccountBalanceWallet, "Withdrawal Rule")
            SettingMenuItem(Icons.Default.AccountCircle, "User Settings")
            SettingMenuItem(Icons.Default.PhoneInTalk, "Service Center")
            SettingMenuItem(Icons.AutoMirrored.Filled.HelpOutline, "FAQ's", onClick = onFAQsClick)
        }
    }
}

@Composable
fun SettingMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon with soft circular background
        Box(
            modifier = Modifier
                .size(48.dp)
                .shadow(4.dp, CircleShape, spotColor = Color(0x11000000))
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = Color(0xFF130138)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Title
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF130138),
            modifier = Modifier.weight(1f)
        )

        // Right Chevron
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color(0xFF130138)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WalletPreview() {
    WalletSettingsScreen()
}
