package com.example.anantapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.ui.theme.BankingTheme

/**
 * Amount Input Field with Currency Symbol
 * Used for goal amount input with rupee symbol
 */
@Composable
fun AmountInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter Your Amount",
    currencySymbol: String = "₹",
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = if (isError) Color(0xFFFFEBEE) else Color.White,
                    shape = RoundedCornerShape(BankingTheme.Radius.Small)
                )
                .border(
                    width = 1.5.dp,
                    color = if (isError) Color(0xFFE91E8C) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(BankingTheme.Radius.Small)
                )
                .padding(horizontal = BankingTheme.Spacing.Medium),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Currency Symbol
                Text(
                    text = currencySymbol,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BankingTheme.Colors.PrimaryAccent,
                    modifier = Modifier.padding(end = BankingTheme.Spacing.Small)
                )
                
                // Input Field
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = BankingTheme.Colors.TextPrimary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }
        
        // Error Message
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color(0xFFE91E8C),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Date Input Field with Calendar Icon
 * Used for deadline input
 */
@Composable
fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    placeholder: String = "Select Date",
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = if (isError) Color(0xFFFFEBEE) else Color.White,
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .border(
                width = 1.5.dp,
                color = if (isError) Color(0xFFE91E8C) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .clickable { onCalendarClick() }
            .padding(horizontal = BankingTheme.Spacing.Medium),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (value.isEmpty()) Color.Gray else BankingTheme.Colors.TextPrimary
            )
            
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.size(20.dp),
                tint = BankingTheme.Colors.PrimaryAccent
            )
        }
    }
}

/**
 * Standard Text Input Field
 * Used for account details like name, account number, IFSC code
 */
@Composable
fun StandardTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Enter Text",
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (maxLines == 1) 56.dp else 100.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(BankingTheme.Radius.Small)
                )
                .border(
                    width = 1.5.dp,
                    color = if (isError) Color(0xFFE91E8C) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(BankingTheme.Radius.Small)
                )
                .padding(BankingTheme.Spacing.Medium),
            contentAlignment = if (maxLines == 1) Alignment.CenterStart else Alignment.TopStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = BankingTheme.Colors.TextPrimary
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                singleLine = maxLines == 1,
                maxLines = maxLines,
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    innerTextField()
                }
            )
        }
        
        // Error Message
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 12.sp,
                color = Color(0xFFE91E8C),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Informational Alert Box
 * Used for displaying bank account transfer information
 */
@Composable
fun InformationAlertBox(
    message: String,
    icon: String = "ⓘ",
    backgroundColor: Color = Color(0xFFFFF3E0),
    borderColor: Color = Color(0xFFFFB74D),
    iconColor: Color = Color(0xFFFF9800),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .padding(BankingTheme.Spacing.Medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = iconColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
            
            Spacer(modifier = Modifier.padding(horizontal = BankingTheme.Spacing.Small / 2))
            
            // Message
            Text(
                text = message,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Section Header Component
 * Used for organizing form sections
 */
@Composable
fun SectionHeader(
    title: String,
    isRequired: Boolean = false,
    modifier: Modifier = Modifier,
    helperText: String = ""
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = BankingTheme.Colors.TextPrimary
            )
            
            if (isRequired) {
                Text(
                    text = " *",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE91E8C)
                )
            }
        }
        
        // Helper Text
        if (helperText.isNotEmpty()) {
            Text(
                text = helperText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Toggle Switch Component (Android style)
 * Used for Auto-topup toggle
 */
@Composable
fun ToggleSwitch(
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(BankingTheme.Radius.Small)
            )
            .clickable { onToggle(!isEnabled) }
            .padding(BankingTheme.Spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = BankingTheme.Colors.TextPrimary
        )
        
        // Toggle Circle
        Box(
            modifier = Modifier
                .size(48.dp, 28.dp)
                .background(
                    color = if (isEnabled) BankingTheme.Colors.PrimaryAccent else Color(0xFFBDBDBD),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(2.dp),
            contentAlignment = if (isEnabled) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }
    }
}

/**
 * Pill-shaped Button Component (Outlined)
 * Used for Draft button
 */
@Composable
fun PillButtonOutlined(
    text: String,
    icon: String = "",
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    borderColor: Color = Color(0xFF9C27B0),
    textColor: Color = Color(0xFF9C27B0)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(26.dp)
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(26.dp)
            )
            .clickable(enabled = !isLoading) { onClick() }
            .padding(horizontal = BankingTheme.Spacing.Medium, vertical = BankingTheme.Spacing.Small),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon.isNotEmpty()) {
                Text(
                    text = icon,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = BankingTheme.Spacing.Small)
                )
            }
            
            Text(
                text = if (isLoading) "Loading..." else text,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}

/**
 * Pill-shaped Button Component (Gradient Filled)
 * Used for Publish button
 */
@Composable
fun PillButtonGradient(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(Color(0xFF9C27B0), Color(0xFFE91E8C))
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    colors = gradientColors,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(1000f, 0f)
                ),
                shape = RoundedCornerShape(26.dp)
            )
            .clickable(enabled = !isLoading) { onClick() }
            .padding(horizontal = BankingTheme.Spacing.Medium, vertical = BankingTheme.Spacing.Small),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLoading) "Publishing..." else text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}
