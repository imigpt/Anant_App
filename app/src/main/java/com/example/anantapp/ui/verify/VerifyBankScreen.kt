package com.example.anantapp.ui.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Exact Color definitions
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val PurpleAccent = Color(0xFFC026D3)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)
private val FieldBackground = Color(0xFFF9F9F9)

@Composable
fun VerifyBankScreen(
    viewModel: VerifyBankViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            onSuccess()
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage!!)
            viewModel.clearMessages()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        // Blurred gradient blobs in the background
        BackgroundDecorationBank()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            BankVerificationCard(
                uiState = uiState,
                onValueChange = viewModel::updateField,
                onSkipClick = onSkipClick,
                onSubmitClick = viewModel::submitBankVerification,
                onAlternateClick = viewModel::enableAlternateBankMode
            )

            Spacer(modifier = Modifier.weight(1f))

            // Privacy Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Secure",
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Your data stays private & encrypted.",
                    fontSize = 12.sp,
                    color = Color(0xFFB0B0B0)
                )
            }
        }

        if (uiState.isLoading) {
            LoadingOverlay()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
private fun BankVerificationCard(
    uiState: com.example.anantapp.data.model.VerifyBankState,
    onValueChange: (String, String) -> Unit,
    onSkipClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onAlternateClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(32.dp),
                spotColor = Color.Black.copy(alpha = 0.15f),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .border(
                width = 1.5.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(32.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    listOf(Color.White.copy(0.7f), Color.White.copy(0.4f))
                ),
                shape = RoundedCornerShape(32.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Skip Button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .clickable { onSkipClick() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Skip >>",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            // Header Icon and Title
            Icon(
                imageVector = Icons.Outlined.AccountBalance,
                contentDescription = "Bank",
                modifier = Modifier.size(56.dp),
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Account Type Toggles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AccountTypeToggle(
                    label = "Current\nAccount",
                    isSelected = uiState.accountType == "Current",
                    onClick = { onValueChange("accountType", "Current") },
                    modifier = Modifier.weight(1f)
                )
                AccountTypeToggle(
                    label = "Saving\nAccount",
                    isSelected = uiState.accountType == "Saving",
                    onClick = { onValueChange("accountType", "Saving") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Input Fields
            CustomInputField(
                value = uiState.firstName,
                onValueChange = { onValueChange("firstName", it) },
                placeholder = "First name",
                icon = Icons.Filled.Person
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputField(
                value = uiState.lastName,
                onValueChange = { onValueChange("lastName", it) },
                placeholder = "Last name",
                icon = Icons.Filled.PersonOutline
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputField(
                value = uiState.bankName,
                onValueChange = { onValueChange("bankName", it) },
                placeholder = "Bank name",
                icon = Icons.Filled.AccountBalanceWallet
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputField(
                value = uiState.accountNumber,
                onValueChange = { onValueChange("accountNumber", it) },
                placeholder = "Account number",
                icon = Icons.Filled.Password
            )
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputField(
                value = uiState.ifscCode,
                // Automatically uppercase IFSC code for better UX
                onValueChange = { onValueChange("ifscCode", it.uppercase()) },
                placeholder = "IFSC code",
                icon = Icons.Filled.Pin
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Add Alternate Bank Button (Only show if NOT in alternate mode)
            if (!uiState.isAlternateMode) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(50))
                        .background(Color.White, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .clickable { onAlternateClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+ Add Alternate Bank Account",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Submit Button
            SubmitButton(
                isEnabled = uiState.isSubmitEnabled,
                onClick = onSubmitClick
            )
        }
    }
}

@Composable
private fun AccountTypeToggle(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // If selected, give it a subtle gradient border, else standard grey
    val borderBrush = if (isSelected) {
        Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd))
    } else {
        SolidColor(Color.Transparent)
    }

    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(
                width = if (isSelected) 1.5.dp else 0.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Switch(
                checked = isSelected,
                onCheckedChange = { onClick() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = TextPrimary,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE0E0E0),
                    uncheckedBorderColor = Color.Transparent
                ),
                modifier = Modifier.scale(0.75f) // Scale down switch to match design size
            )
        }
    }
}

@Composable
private fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    val isFocused = value.isNotEmpty()

    // Dynamic border color based on if the user has typed anything
    val borderBrush = if (isFocused) {
        Brush.horizontalGradient(listOf(OrangeGradientStart, OrangeGradientEnd))
    } else {
        SolidColor(Color(0xFFE0E0E0))
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        cursorBrush = SolidColor(TextPrimary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White, RoundedCornerShape(50))
                    .border(
                        width = 1.dp,
                        brush = borderBrush,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Left circular icon container
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(FieldBackground, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isFocused) OrangeGradientStart else TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = TextSecondary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        innerTextField()
                    }
                }
            }
        }
    )
}

@Composable
private fun SubmitButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                brush = Brush.linearGradient(listOf(PurpleAccent, OrangeGradientStart)),
                shape = RoundedCornerShape(50)
            )
            .padding(2.dp) // creates the border effect
            .background(
                color = if (isEnabled) Color.White else Color(0xFFF0F0F0),
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Submit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (isEnabled) TextPrimary else TextSecondary
        )
    }
}

@Composable
private fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = OrangeGradientStart,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
private fun BoxScope.BackgroundDecorationBank() {
    // Top right blob
    Box(
        modifier = Modifier
            .size(260.dp)
            .align(Alignment.TopEnd)
            .offset(x = 60.dp, y = (-60).dp)
            .blur(radius = 50.dp)
            .background(
                brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)),
                shape = CircleShape
            )
    )
    // Bottom left blob
    Box(
        modifier = Modifier
            .size(200.dp)
            .align(Alignment.BottomStart)
            .offset(x = (-60).dp, y = 60.dp)
            .blur(radius = 50.dp)
            .background(
                brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)),
                shape = CircleShape
            )
    )
}
