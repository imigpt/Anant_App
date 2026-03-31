package com.example.anantapp.feature.verify.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.verify.presentation.viewmodel.VerifyAddressViewModel

private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val PurpleAccent = Color(0xFF9500FF)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)

private val FieldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
)
private val SubmitButtonGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFC6FF00), Color(0xFF7CB342))
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        BackgroundDecorationAddress()

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Skip Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Skip",
                        fontSize = 14.sp,
                        color = PurpleAccent,
                        modifier = Modifier.clickable { onSkipClick() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = PurpleAccent
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Address Verification",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Verify your residential address",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Address Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GradientInputField(
                        value = uiState.homeAddress,
                        onValueChange = { viewModel.updateField("homeAddress", it) },
                        placeholder = "Home Address Type",
                        icon = Icons.Outlined.Home
                    )

                    GradientInputField(
                        value = uiState.houseFlatNumber,
                        onValueChange = { viewModel.updateField("houseFlatNumber", it) },
                        placeholder = "House/Flat Number",
                        icon = Icons.Outlined.Home
                    )

                    GradientInputField(
                        value = uiState.address,
                        onValueChange = { viewModel.updateField("address", it) },
                        placeholder = "Street Address",
                        icon = Icons.Outlined.Map
                    )

                    GradientInputField(
                        value = uiState.city,
                        onValueChange = { viewModel.updateField("city", it) },
                        placeholder = "City",
                        icon = Icons.Outlined.LocationOn
                    )

                    GradientInputField(
                        value = uiState.state,
                        onValueChange = { viewModel.updateField("state", it) },
                        placeholder = "State",
                        icon = Icons.Outlined.Map
                    )

                    GradientInputField(
                        value = uiState.pincode,
                        onValueChange = { viewModel.updateField("pincode", it) },
                        placeholder = "Pincode",
                        icon = Icons.Outlined.LocationOn,
                        maxLength = 6
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                AddressSubmitButton(onClick = { viewModel.submitAddressVerification() })

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (uiState.isLoading) {
            LoadingOverlayAddress()
        }

        SnackbarHost(snackbarHostState)
    }
}

@Composable
private fun GradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector,
    maxLength: Int = Int.MAX_VALUE
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

            BasicTextField(
                value = value,
                onValueChange = { if (it.length <= maxLength) onValueChange(it) },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
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
private fun AddressSubmitButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = SubmitButtonGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Verify Address",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
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
            CircularProgressIndicator(color = PurpleAccent)
        }
    }
}

@Composable
private fun BackgroundDecorationAddress() {
    Box(
        modifier = Modifier
            .size(250.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(OrangeGradientStart.copy(alpha = 0.1f), Color.Transparent)
                ),
                shape = RoundedCornerShape(100.dp)
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyAddressScreenPreview() {
    VerifyAddressScreen()
}
