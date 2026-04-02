package com.example.anantapp.ui.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Original Color definitions for the Background
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val MainBackground = Color(0xFFFAFAFA)

// Reusable gradients matching the UI image for fields and buttons
private val FieldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
)
private val SubmitButtonGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFC6FF00), Color(0xFF7CB342)) // Vibrant lime green
)

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

    VerifyAddressContent(
        homeAddress = uiState.homeAddress,
        houseFlatNumber = uiState.houseFlatNumber,
        address = uiState.address,
        city = uiState.city,
        state = uiState.state,
        pincode = uiState.pincode,
        isLoading = uiState.isLoading,
        onValueChange = viewModel::updateField,
        onSkipClick = onSkipClick,
        onSubmitClick = viewModel::submitAddressVerification,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun VerifyAddressContent(
    homeAddress: String,
    houseFlatNumber: String,
    address: String,
    city: String,
    state: String,
    pincode: String,
    isLoading: Boolean,
    onValueChange: (String, String) -> Unit,
    onSkipClick: () -> Unit,
    onSubmitClick: () -> Unit,
    snackbarHostState: SnackbarHostState? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        // Restored your original background blobs
        BackgroundDecorationAddress()

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
            colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Transparent to show background
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

                // Central Large Home Icon
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Address Icon",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Verify Address",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Input Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GradientInputField(
                        value = homeAddress,
                        onValueChange = { onValueChange("homeAddress", it) },
                        placeholder = "Home address",
                        icon = Icons.Outlined.Home
                    )
                    GradientInputField(
                        value = houseFlatNumber,
                        onValueChange = { onValueChange("houseFlatNumber", it) },
                        placeholder = "House flat number",
                        icon = Icons.Outlined.Home
                    )
                    GradientInputField(
                        value = address,
                        onValueChange = { onValueChange("address", it) },
                        placeholder = "Address",
                        icon = Icons.Outlined.Home
                    )
                    GradientInputField(
                        value = city,
                        onValueChange = { onValueChange("city", it) },
                        placeholder = "City",
                        icon = Icons.Outlined.LocationOn
                    )
                    GradientInputField(
                        value = state,
                        onValueChange = { onValueChange("state", it) },
                        placeholder = "State",
                        icon = Icons.Outlined.Map
                    )
                    GradientInputField(
                        value = pincode,
                        onValueChange = { onValueChange("pincode", it) },
                        placeholder = "Pincode",
                        icon = Icons.Outlined.LocalShipping,
                        maxLength = 6,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Submit Button
                AddressSubmitButton(
                    onClick = onSubmitClick
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

        if (isLoading) {
            LoadingOverlayAddress()
        }

        if (snackbarHostState != null) {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

// Your Original Background Function Restored
@Composable
private fun BoxScope.BackgroundDecorationAddress() {
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

@Composable
private fun GradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
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
                    onValueChange = { newValue ->
                        if (newValue.length <= maxLength) {
                            onValueChange(newValue)
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    ),
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun AddressSubmitButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color(0xFF7CB342).copy(alpha = 0.6f)
            )
            .clip(RoundedCornerShape(28.dp)) // Clip comes before background and clickable
            .background(brush = SubmitButtonGradient)
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f), // Soft border to match aesthetic
                shape = RoundedCornerShape(28.dp)
            ),
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
                color = Color(0xFFFF6A00),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyAddressScreenPreview() {
    VerifyAddressContent(
        homeAddress = "",
        houseFlatNumber = "",
        address = "",
        city = "",
        state = "",
        pincode = "",
        isLoading = false,
        onValueChange = { _, _ -> },
        onSkipClick = {},
        onSubmitClick = {}
    )
}