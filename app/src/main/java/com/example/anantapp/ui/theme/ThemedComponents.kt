package com.example.anantapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.ui.components.customShadow

/**
 * Themed Components Examples
 * Shows how to use theme colors in reusable components
 */

@Composable
fun ThemedCard(
    modifier: Modifier = Modifier,
    theme: CurrentTheme = LocalCurrentTheme.current,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = theme.elevation,
                shape = RoundedCornerShape(theme.cornerRadius),
                ambientColor = theme.shadowColor,
                spotColor = theme.shadowColor
            )
            .clip(RoundedCornerShape(theme.cornerRadius))
            .background(theme.cardBackground)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.6f),
                shape = RoundedCornerShape(theme.cornerRadius)
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun ThemedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    theme: CurrentTheme = LocalCurrentTheme.current
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp)
            .shadow(
                elevation = theme.elevation,
                shape = RoundedCornerShape(theme.cornerRadius),
                ambientColor = theme.shadowColor,
                spotColor = theme.shadowColor
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = theme.accentColor.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(theme.cornerRadius)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ThemedGradientBackground(
    modifier: Modifier = Modifier,
    theme: CurrentTheme = LocalCurrentTheme.current,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = theme.primaryGradient
                )
            )
    ) {
        content()
    }
}

@Composable
fun ThemedTitle(
    text: String,
    modifier: Modifier = Modifier,
    theme: CurrentTheme = LocalCurrentTheme.current
) {
    Text(
        text = text,
        color = theme.textPrimary,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun ThemedSubtitle(
    text: String,
    modifier: Modifier = Modifier,
    theme: CurrentTheme = LocalCurrentTheme.current
) {
    Text(
        text = text,
        color = theme.textSecondary,
        fontSize = 14.sp,
        modifier = modifier
    )
}
