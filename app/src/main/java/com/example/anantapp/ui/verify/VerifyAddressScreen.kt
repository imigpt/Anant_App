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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Color definitions
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val PurpleAccent = Color(0xFFC026D3)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)
private val FieldBackground = Color(0xFFF9F9F9)

@Composable
fun VerifyAddressScreen(
    viewModel: VerifyAddressViewModel = viewModel(),
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
        BackgroundDecorationAddress()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            AddressVerificationCard(
                uiState = uiState,
                onValueChange = viewModel::updateField,
                onSkipClick = onSkipClick,
                onSubmitClick = viewModel::submitAddressVerification
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
            LoadingOverlayAddress()
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
private fun AddressVerificationCard(
    uiState: com.example.anantapp.data.model.VerifyAddressState,
    onValueChange: (String, String) -> Unit,
    onSkipClick: () -> Unit,
    onSubmitClick: () -> Unit
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
                imageVector = Icons.Outlined.Home,
                contentDescription = "Address",
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

            // Input Fields
            AddressInputField(
                value = uiState.homeAddress,
                onValueChange = { onValueChange("homeAddress", it) },
                placeholder = "Home address",
                icon = Icons.Filled.LocationOn
            )
            Spacer(modifier = Modifier.height(12.dp))

            AddressInputField(
                value = uiState.houseFlatNumber,
                onValueChange = { onValueChange("houseFlatNumber", it) },
                placeholder = "House flat number",
                icon = Icons.Filled.Apartment
            )
            Spacer(modifier = Modifier.height(12.dp))

            AddressInputField(
                value = uiState.address,
                onValueChange = { onValueChange("address", it) },
                placeholder = "Address",
                icon = Icons.Filled.AddLocation
            )
            Spacer(modifier = Modifier.height(12.dp))

            AddressInputField(
                value = uiState.city,
                onValueChange = { onValueChange("city", it) },
                placeholder = "City",
                icon = Icons.Filled.LocationCity
            )
            Spacer(modifier = Modifier.height(12.dp))

            AddressInputField(
                value = uiState.state,
                onValueChange = { onValueChange("state", it) },
                placeholder = "State",
                icon = Icons.Filled.Public
            )
            Spacer(modifier = Modifier.height(12.dp))

            AddressInputField(
                value = uiState.pincode,
                onValueChange = { onValueChange("pincode", it) },
                placeholder = "Pincode",
                icon = Icons.Filled.Pin,
                maxLength = 6
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            AddressSubmitButton(
                isEnabled = uiState.isSubmitEnabled,
                onClick = onSubmitClick
            )
        }
    }
}

@Composable
private fun AddressInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    maxLength: Int = Int.MAX_VALUE
) {
    val isFocused = value.isNotEmpty()

    // Orange gradient border when focused
    val borderBrush = if (isFocused) {
        Brush.horizontalGradient(listOf(OrangeGradientStart, OrangeGradientEnd))
    } else {
        SolidColor(Color(0xFFE0E0E0))
    }

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                onValueChange(newValue)
            }
        },
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
                    // Left circular icon container with gradient background
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(OrangeGradientStart, OrangeGradientEnd)
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
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
private fun AddressSubmitButton(
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
            .padding(2.dp)
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
private fun LoadingOverlayAddress() {
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
private fun BoxScope.BackgroundDecorationAddress() {
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
