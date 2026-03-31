package com.example.anantapp.feature.qr.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.qr.presentation.viewmodel.GenerateQRCodeViewModel

@Composable
fun GenerateQRCodeScreen(
    viewModel: GenerateQRCodeViewModel = viewModel(),
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
                    text = "Generate QR Code",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Full Name
                QRFormLabel(text = "Full Name *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.fullName,
                    onValueChange = { viewModel.updateFullName(it) },
                    placeholder = "Enter full name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Vehicle Type
                QRFormLabel(text = "Vehicle Type *")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("2 Wheeler", "4 Wheeler", "Other").forEach { type ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.5.dp,
                                    brush = if (uiState.selectedVehicleType == type) mainGradient else SolidColor(Color.Gray),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    brush = if (uiState.selectedVehicleType == type) mainGradient else SolidColor(Color.White),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.updateVehicleType(type) }
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (uiState.selectedVehicleType == type) Color.White else Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Vehicle Number Plate
                QRFormLabel(text = "Vehicle Number Plate *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.vehicleNumberPlate,
                    onValueChange = { viewModel.updateVehicleNumberPlate(it) },
                    placeholder = "Enter number plate",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Insurance Policy Number
                QRFormLabel(text = "Insurance Policy Number *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.insurancePolicyNumber,
                    onValueChange = { viewModel.updateInsurancePolicyNumber(it) },
                    placeholder = "Enter policy number",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Insurance Valid Till
                QRFormLabel(text = "Insurance Valid Till *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.insuranceValidTill,
                    onValueChange = { viewModel.updateInsuranceValidTill(it) },
                    placeholder = "MM/DD/YYYY",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Contact Name
                QRFormLabel(text = "Emergency Contact Name *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.emergencyContactName,
                    onValueChange = { viewModel.updateEmergencyContactName(it) },
                    placeholder = "Enter name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Contact Phone
                QRFormLabel(text = "Emergency Contact Phone *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.emergencyContactPhone,
                    onValueChange = { viewModel.updateEmergencyContactPhone(it) },
                    placeholder = "Enter phone number",
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
                    text = "Generate QR Code",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun QRFormLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF333333)
    )
}

@Composable
fun QRInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    mainGradient: Brush,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.5.dp, mainGradient, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenerateQRCodeScreenPreview() {
    AnantAppTheme {
        GenerateQRCodeScreen()
    }
}
