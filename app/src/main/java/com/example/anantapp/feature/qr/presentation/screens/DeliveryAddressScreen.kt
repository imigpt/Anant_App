package com.example.anantapp.feature.qr.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.qr.presentation.viewmodel.DeliveryAddressViewModel

@Composable
fun DeliveryAddressScreen(
    viewModel: DeliveryAddressViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF2F4B)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(28.dp)
                        .clickable { onBackClick() }
                )

                Text(
                    text = "Delivery Address",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Delivery Name
                QRFormLabel(text = "Full Name *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.deliveryName,
                    onValueChange = { viewModel.updateDeliveryName(it) },
                    placeholder = "Enter recipient name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // House Number
                QRFormLabel(text = "House Number / Building Name *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.houseNumber,
                    onValueChange = { viewModel.updateHouseNumber(it) },
                    placeholder = "Enter building name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Street/Locality
                QRFormLabel(text = "Street / Locality *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.streetLocality,
                    onValueChange = { viewModel.updateStreetLocality(it) },
                    placeholder = "Enter street name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // City
                QRFormLabel(text = "City *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.city,
                    onValueChange = { viewModel.updateCity(it) },
                    placeholder = "Enter city",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // State
                QRFormLabel(text = "State *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.state,
                    onValueChange = { viewModel.updateState(it) },
                    placeholder = "Enter state",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Pincode
                QRFormLabel(text = "Pincode *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.pincode,
                    onValueChange = { viewModel.updatePincode(it) },
                    placeholder = "Enter pincode",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Mobile Number
                QRFormLabel(text = "Mobile Number *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.mobileNumber,
                    onValueChange = { viewModel.updateMobileNumber(it) },
                    placeholder = "Enter mobile number",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Next Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .background(mainGradient, RoundedCornerShape(12.dp))
                    .clickable { onNextClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeliveryAddressScreenPreview() {
    AnantAppTheme {
        DeliveryAddressScreen()
    }
}
