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
import com.example.anantapp.feature.qr.presentation.viewmodel.UserDetailsViewModel
import com.example.anantapp.ui.components.QRFormLabel
import com.example.anantapp.ui.components.QRInputField

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel = viewModel(),
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
                    text = "Personal Details",
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
                    value = uiState.userName,
                    onValueChange = { viewModel.updateUserName(it) },
                    placeholder = "Enter full name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Email
                QRFormLabel(text = "Email Address *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.userEmail,
                    onValueChange = { viewModel.updateUserEmail(it) },
                    placeholder = "Enter email",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Phone
                QRFormLabel(text = "Phone Number *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.userPhone,
                    onValueChange = { viewModel.updateUserPhone(it) },
                    placeholder = "Enter phone number",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Blood Type
                QRFormLabel(text = "Blood Type")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-").forEach { type ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.5.dp,
                                    brush = if (uiState.bloodType == type) mainGradient else SolidColor(Color.Gray),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    brush = if (uiState.bloodType == type) mainGradient else SolidColor(Color.White),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.updateBloodType(type) }
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = type,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (uiState.bloodType == type) Color.White else Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Contact Name
                QRFormLabel(text = "Emergency Contact Name *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.emergencyContactName,
                    onValueChange = { viewModel.updateEmergencyContactName(it) },
                    placeholder = "Enter contact name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Contact Phone
                QRFormLabel(text = "Emergency Contact Phone *")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.emergencyContactPhone,
                    onValueChange = { viewModel.updateEmergencyContactPhone(it) },
                    placeholder = "Enter contact phone",
                    mainGradient = mainGradient,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Address
                QRFormLabel(text = "Address")
                Spacer(modifier = Modifier.height(8.dp))
                QRInputField(
                    value = uiState.address,
                    onValueChange = { viewModel.updateAddress(it) },
                    placeholder = "Enter address",
                    mainGradient = mainGradient
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
fun UserDetailsScreenPreview() {
    AnantAppTheme {
        UserDetailsScreen()
    }
}
