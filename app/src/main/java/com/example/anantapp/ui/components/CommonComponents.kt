package com.example.anantapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.ui.theme.BankingTheme

/**
 * Reusable Navigation Item Component
 * Used in bottom navigation bar across multiple screens
 */
@Composable
fun NavigationItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color = BankingTheme.Colors.PrimaryAccent,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = if (isSelected) selectedColor else BankingTheme.Colors.TextSecondary
        )

        Spacer(modifier = Modifier.height(BankingTheme.Spacing.ExtraSmall))

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) selectedColor else BankingTheme.Colors.TextSecondary
        )
    }
}

/**
 * Bottom Navigation Bar Component
 * Reusable across all screens
 */
@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onHomeClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(BankingTheme.Colors.CardBackground)
    ) {
        Column {
            // Divider line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.Black)
            )

            // Navigation items
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = BankingTheme.Spacing.Medium),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationItem(
                    icon = Icons.Filled.Home,
                    label = "Home",
                    isSelected = selectedItem == "home",
                    onClick = onHomeClick
                )

                NavigationItem(
                    icon = Icons.Outlined.BarChart,
                    label = "Analytics",
                    isSelected = selectedItem == "analytics",
                    onClick = onAnalyticsClick
                )

                NavigationItem(
                    icon = Icons.Filled.Notifications,
                    label = "Alerts",
                    isSelected = selectedItem == "alerts",
                    onClick = onNotificationClick
                )

                NavigationItem(
                    icon = Icons.Filled.Person,
                    label = "Profile",
                    isSelected = selectedItem == "profile",
                    onClick = onProfileClick
                )
            }
        }
    }
}

/**
 * Action Button for quick actions
 */
@Composable
fun ActionButton(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(BankingTheme.Spacing.Small)
    ) {
        Text(
            text = icon,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(BankingTheme.Spacing.ExtraSmall))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = BankingTheme.Colors.TextPrimary,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

/**
 * Circular Quick Action Component
 */
@Composable
fun QuickActionCircle(
    icon: String,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = BankingTheme.Colors.Border,
    backgroundColor: Color = BankingTheme.Colors.CardBackground
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(BankingTheme.Spacing.Small))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = BankingTheme.Colors.TextPrimary,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

/**
 * Decorative Circle Component
 */
@Composable
fun DecorativeCircle(
    size: Dp,
    color: Color,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .offset(x = offsetX, y = offsetY)
            .background(
                color = color,
                shape = CircleShape
            )
    )
}

/**
 * Gradient Surface Component
 */
@Composable
fun GradientSurface(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    colors = colors,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(1000f, 600f)
                )
            ),
        content = content
    )
}
