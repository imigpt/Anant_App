package com.example.anantapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.ui.theme.BankingTheme

/**
 * Form Label Component
 */
@Composable
fun FormLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF333333),
        modifier = modifier.padding(bottom = 8.dp)
    )
}

/**
 * Required Form Label Component
 */
@Composable
fun RequiredFormLabel(text: String, isRequired: Boolean = true, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(bottom = 8.dp)) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333)
        )
        if (isRequired) {
            Text(
                text = " *",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

/**
 * Unified Screen Header with Back Button
 */
@Composable
fun ScreenHeader(
    title: String,
    onBackClick: () -> Unit,
    centeredTitle: Boolean = false,
    titleColor: Color = Color(0xFF333333),
    backIconTint: Color = Color(0xFF333333),
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBackClick() },
            tint = backIconTint
        )
        
        if (centeredTitle) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).offset(x = (-12).dp)
            )
        } else {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }
    }
}

/**
 * Unified Custom Outlined Input
 */
@Composable
fun CustomOutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: Dp = 52.dp,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    backgroundColor: Color = Color(0xFFF5F5F5),
    borderColor: Color = Color(0xFFE0E0E0),
    borderWidth: Dp = 1.dp,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = if (singleLine) 0.dp else 16.dp),
        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                readOnly = readOnly,
                textStyle = TextStyle(color = Color(0xFF333333), fontSize = 16.sp),
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) Text(placeholder, color = Color.Gray, fontSize = 16.sp)
                    innerTextField()
                }
            )
            trailingIcon?.invoke()
        }
    }
}

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

/**
 * Custom Shadow Extension for Modifiers
 */
fun Modifier.customShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0.dp,
) = drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx()
        val rightPixel = (size.width + spreadPixel) + offsetX.toPx()
        val bottomPixel = (size.height + spreadPixel) + offsetY.toPx()

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = (android.graphics.BlurMaskFilter(blurRadius.toPx(), android.graphics.BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        canvas.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint = paint
        )
    }
}
