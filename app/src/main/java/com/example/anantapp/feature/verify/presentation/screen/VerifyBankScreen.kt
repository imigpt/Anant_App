package com.example.anantapp.feature.verify.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.verify.presentation.viewmodel.VerifyBankViewModel

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
                            .imePadding()
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
                        activeTrackColor = Color(0xFF34C759), // Green
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
                    BankGradientInputField(
                        value = uiState.firstName,
                        onValueChange = { viewModel.updateField("firstName", it) },
                        placeholder = "First name",
                        icon = Icons.Outlined.Person,
                    )
                    BankGradientInputField(
                        value = uiState.lastName,
                        onValueChange = { viewModel.updateField("lastName", it) },
                        placeholder = "Last name",
                        icon = Icons.Outlined.Person,
                    )
                    BankGradientInputField(
                        value = uiState.bankName,
                        onValueChange = { viewModel.updateField("bankName", it) },
                        placeholder = "Bank name",
                        icon = Icons.Outlined.Lock,
                        // Closest match for locked document
                    )
                    BankGradientInputField(
                        value = uiState.accountNumber,
                        onValueChange = { viewModel.updateField("accountNumber", it) },
                        placeholder = "Account number",
                        icon = Icons.Outlined.Key,
                        keyboardType = KeyboardType.Number
                    )
                    BankGradientInputField(
                        value = uiState.ifscCode,
                        onValueChange = { viewModel.updateField("ifscCode", it.uppercase()) },
                        placeholder = "IFSC code",
                        icon = Icons.Outlined.MoreHoriz,
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = OrangeGradientStart)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BankGradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                brush = FieldGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            val bringRequester = remember { BringIntoViewRequester() }
            val scope = rememberCoroutineScope()

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f)
                    .bringIntoViewRequester(bringRequester)
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            scope.launch { bringRequester.bringIntoView() }
                        }
                    },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
fun AccountTypeToggle(
    label: String,
    isSelected: Boolean,
    activeTrackColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) activeTrackColor else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                lineHeight = 16.sp
            )
            // Custom Switch-like indicator
            Box(
                modifier = Modifier
                    .size(width = 34.dp, height = 20.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) activeTrackColor else Color(0xFFE0E0E0))
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(if (isSelected) Alignment.CenterEnd else Alignment.CenterStart)
                )
            }
        }
    }
}

@Composable
fun SubmitButton(
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .then(
                if (isEnabled) {
                    Modifier
                        .background(SubmitBorderGradient)
                        .padding(1.5.dp) // Border thickness
                } else {
                    Modifier.background(Color.LightGray)
                }
            )
            .clip(RoundedCornerShape(28.dp))
            .background(if (isEnabled) MainBackground else Color.LightGray)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Submit",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (isEnabled) Color.Black else Color.White
        )
    }
}

@Composable
fun BackgroundDecorationBank() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Top right orange blob (linear gradient)
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 150.dp, y = (-50).dp)
                .scale(1.5f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            OrangeGradientStart.copy(alpha = 0.45f),
                            OrangeGradientEnd.copy(alpha = 0.15f)
                        )
                    ),
                    shape = CircleShape
                )
        )
        // Bottom left yellow blob (linear gradient)
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150).dp, y = 450.dp)
                .scale(1.2f)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            OrangeGradientStart.copy(alpha = 0.25f),
                            OrangeGradientEnd.copy(alpha = 0.35f)
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}
