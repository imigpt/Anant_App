package com.example.anantapp.feature.wallet.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.wallet.presentation.viewmodel.DonorScreenViewModel

@Composable
fun DonorScreen(
    viewModel: DonorScreenViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val lightBorderColor = Color(0xFFE5E5E5)
    val textColor = Color(0xFF333333)
    val placeholderColor = Color(0xFFAAAAAA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Background Layer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(brush = gradientBrush)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 8.dp, end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Make a Donation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        // Overlapping White Content Area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                // Choose Donor Type Section
                Text(
                    text = "Choose Donor Type",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Government Employee Button
                DonorTypeButton(
                    text = "Government Employee",
                    isSelected = uiState.selectedDonorType == "government",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.selectDonorType("government") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Individual Donor Button
                DonorTypeButton(
                    text = "Individual Donor",
                    isSelected = uiState.selectedDonorType == "individual",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.selectDonorType("individual") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Corporate Button
                DonorTypeButton(
                    text = "Corporate/Company",
                    isSelected = uiState.selectedDonorType == "corporate",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.selectDonorType("corporate") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Form Fields based on selected type
                when (uiState.selectedDonorType) {
                    "government" -> {
                        GovernmentEmployeeForm(viewModel, lightBorderColor)
                    }
                    "individual" -> {
                        IndividualDonorForm(viewModel, lightBorderColor)
                    }
                    "corporate" -> {
                        CorporateForm(viewModel, lightBorderColor)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Donation Amount
                Text(
                    text = "Donation Amount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = placeholderColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickAmountButton("₹100", uiState.donationAmount == "100", viewModel, gradientBrush, lightBorderColor, Modifier.weight(1f))
                    QuickAmountButton("₹500", uiState.donationAmount == "500", viewModel, gradientBrush, lightBorderColor, Modifier.weight(1f))
                    QuickAmountButton("₹1000", uiState.donationAmount == "1000", viewModel, gradientBrush, lightBorderColor, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.donationAmount,
                    onValueChange = { viewModel.updateDonationAmount(it) },
                    placeholder = { Text("Enter custom amount", fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = lightBorderColor,
                        focusedBorderColor = Color(0xFF9500FF),
                        unfocusedTextColor = textColor,
                        focusedTextColor = textColor
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Proceed Button
                Button(
                    onClick = onPaymentClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9500FF))
                ) {
                    Text(
                        text = "Proceed to Payment",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun GovernmentEmployeeForm(
    viewModel: DonorScreenViewModel,
    borderColor: Color
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextField(
            value = uiState.employeeId,
            onValueChange = { viewModel.updateEmployeeId(it) },
            placeholder = "Employee ID",
            borderColor = borderColor
        )
        CustomTextField(
            value = uiState.panNumber,
            onValueChange = { viewModel.updatePanNumber(it) },
            placeholder = "PAN Number",
            borderColor = borderColor
        )
    }
}

@Composable
private fun IndividualDonorForm(
    viewModel: DonorScreenViewModel,
    borderColor: Color
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextField(
            value = uiState.panNumber,
            onValueChange = { viewModel.updatePanNumber(it) },
            placeholder = "PAN Number",
            borderColor = borderColor
        )
        CustomTextField(
            value = uiState.aadharNumber,
            onValueChange = { viewModel.updateAadharNumber(it) },
            placeholder = "Aadhar Number",
            borderColor = borderColor
        )
    }
}

@Composable
private fun CorporateForm(
    viewModel: DonorScreenViewModel,
    borderColor: Color
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomTextField(
            value = uiState.companyName,
            onValueChange = { viewModel.updateCompanyName(it) },
            placeholder = "Company Name",
            borderColor = borderColor
        )
        CustomTextField(
            value = uiState.companyRegistration,
            onValueChange = { viewModel.updateCompanyRegistration(it) },
            placeholder = "Company Registration",
            borderColor = borderColor
        )
        CustomTextField(
            value = uiState.taxId,
            onValueChange = { viewModel.updateTaxId(it) },
            placeholder = "Tax ID / TAN",
            borderColor = borderColor
        )
    }
}

@Composable
private fun DonorTypeButton(
    text: String,
    isSelected: Boolean,
    gradientBrush: Brush,
    borderColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = if (isSelected) {
            modifier
                .height(50.dp)
                .background(brush = gradientBrush, shape = RoundedCornerShape(26.dp))
        } else {
            modifier.height(50.dp)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Transparent else Color.White
        ),
        border = if (!isSelected) BorderStroke(1.dp, borderColor) else null,
        shape = RoundedCornerShape(26.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFFAAAAAA)
        )
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontSize = 13.sp, color = Color(0xFFAAAAAA)) },
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = borderColor,
            focusedBorderColor = Color(0xFF9500FF),
            unfocusedTextColor = Color(0xFF333333),
            focusedTextColor = Color(0xFF333333)
        ),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
    )
}

@Composable
private fun QuickAmountButton(
    amount: String,
    isSelected: Boolean,
    viewModel: DonorScreenViewModel,
    gradientBrush: Brush,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { viewModel.updateDonationAmount(amount.replace("₹", "")) },
        modifier = if (isSelected) {
            modifier
                .height(44.dp)
                .background(brush = gradientBrush, shape = RoundedCornerShape(10.dp))
        } else {
            modifier.height(44.dp)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Transparent else Color.White
        ),
        border = if (!isSelected) BorderStroke(1.dp, borderColor) else null,
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = amount,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF333333)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DonorScreenPreview() {
    AnantAppTheme {
        DonorScreen()
    }
}
