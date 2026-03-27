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
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Exact Color definitions for Background
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val MainBackground = Color(0xFFFAFAFA)

// Gradients matching the UI image for fields and buttons
private val FieldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
)
private val SubmitBorderGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFC026D3), Color(0xFFFF6A00)) // Purple to Orange/Red
)

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

        // Glassmorphism Card Container
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 56.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color.Black.copy(alpha = 0.1f)
                )
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.5f), // Soft white border for glass edge
                    shape = RoundedCornerShape(32.dp)
                )
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.8f), Color.White.copy(alpha = 0.4f)) // Translucent gradient
                    ),
                    shape = RoundedCornerShape(32.dp)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Skip Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onSkipClick() }
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Skip >>",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Central Large Bank Icon
                Icon(
                    imageVector = Icons.Outlined.AccountBalance,
                    contentDescription = "Bank Icon",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = uiState.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
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
                        activeTrackColor = Color(0xFF34C759), // Green
                        onClick = { viewModel.updateField("accountType", "Current") },
                        modifier = Modifier.weight(1f)
                    )
                    AccountTypeToggle(
                        label = "Saving\nAccount",
                        isSelected = uiState.accountType == "Saving",
                        activeTrackColor = Color.Black, // Black track when active
                        onClick = { viewModel.updateField("accountType", "Saving") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Input Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GradientInputField(
                        value = uiState.firstName,
                        onValueChange = { viewModel.updateField("firstName", it) },
                        placeholder = "First name",
                        icon = Icons.Outlined.Person
                    )
                    GradientInputField(
                        value = uiState.lastName,
                        onValueChange = { viewModel.updateField("lastName", it) },
                        placeholder = "Last name",
                        icon = Icons.Outlined.Person
                    )
                    GradientInputField(
                        value = uiState.bankName,
                        onValueChange = { viewModel.updateField("bankName", it) },
                        placeholder = "Bank name",
                        icon = Icons.Outlined.Lock // Closest match for locked document
                    )
                    GradientInputField(
                        value = uiState.accountNumber,
                        onValueChange = { viewModel.updateField("accountNumber", it) },
                        placeholder = "Account number",
                        icon = Icons.Outlined.Key
                    )
                    GradientInputField(
                        value = uiState.ifscCode,
                        onValueChange = { viewModel.updateField("ifscCode", it.uppercase()) },
                        placeholder = "IFSC code",
                        icon = Icons.Outlined.MoreHoriz
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Add Alternate Bank Account Button
                if (!uiState.isAlternateMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(24.dp),
                                spotColor = Color.Black.copy(alpha = 0.05f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .clickable { viewModel.enableAlternateBankMode() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+ Add Alternate Bank Account",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Submit Button with Gradient Border
                SubmitButton(
                    isEnabled = uiState.isSubmitEnabled,
                    onClick = { viewModel.submitBankVerification() }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Footer Text & Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Privacy Lock",
                        tint = Color.Black.copy(alpha = 0.6f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your data stays private & encrypted.",
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.6f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        if (uiState.isLoading) {
            LoadingOverlayBank()
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
private fun AccountTypeToggle(
    label: String,
    isSelected: Boolean,
    activeTrackColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(64.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .background(Color.White, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
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
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Switch(
                checked = isSelected,
                onCheckedChange = { onClick() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = activeTrackColor,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Black,
                    uncheckedBorderColor = Color.Transparent
                ),
                modifier = Modifier.scale(0.8f)
            )
        }
    }
}

@Composable
private fun GradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color(0xFFFF9800).copy(alpha = 0.5f)
            )
            .background(
                brush = FieldGradient,
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // White circular icon background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text input over the gradient
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Normal
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SubmitButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .background(
                brush = SubmitBorderGradient,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(1.5.dp) // Creates the gradient border effect
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White) // Inner white background
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Submit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun LoadingOverlayBank() {
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
                color = Color(0xFFFF6A00),
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
            .background(
                brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)),
                shape = CircleShape
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyBankScreenPreview() {
    VerifyBankScreen()
}