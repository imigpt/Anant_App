package com.example.anantapp.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.anantapp.presentation.viewmodel.DonorScreenViewModel

/**
 * Production-level Donor Registration Screen
 * Updated to match the provided UI design layout and styling.
 */
@Composable
fun DonorScreen(
    onBackClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
    viewModel: DonorScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val gradientBlue = Color(0xFF9500FF)
    val gradientPink = Color(0xFFFF6264)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(gradientBlue, gradientPink),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    // Colors matching the UI design
    val lightBorderColor = Color(0xFFE5E5E5)
    val placeholderColor = Color(0xFFAAAAAA)
    val textColor = Color(0xFF333333)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Background Layer (Top Part)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(brush = gradientBrush)
        ) {
            // Header: Back button + Title
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
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Join as a Donor",
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
                .padding(top = 110.dp) // Starts before the gradient ends
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
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Government Employee Button
                DonorTypeButton(
                    text = "Government Employee",
                    isSelected = uiState.donorType == "government",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.updateDonorType("government") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Individual Donor Button
                DonorTypeButton(
                    text = "Individual Donor",
                    isSelected = uiState.donorType == "individual",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.updateDonorType("individual") }
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Corporate/Company Button
                DonorTypeButton(
                    text = "Corporate / Company Donor",
                    isSelected = uiState.donorType == "corporate",
                    gradientBrush = gradientBrush,
                    borderColor = lightBorderColor,
                    onClick = { viewModel.updateDonorType("corporate") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Choose your donor type to receive relevant\nreceipts & tax benefits.",
                    fontSize = 11.sp,
                    color = placeholderColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Donor Details Section
                Text(
                    text = "Donor Details",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = uiState.fullName,
                    onValueChange = { viewModel.updateFullName(it) },
                    placeholder = "Full Name",
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = "Email ID",
                    keyboardType = KeyboardType.Email,
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = uiState.mobileNumber,
                    onValueChange = { input ->
                        // Only allow digits and limit to 10 digits for Indian mobile number
                        val filtered = input.filter { it.isDigit() }.take(10)
                        viewModel.updateMobileNumber(filtered)
                    },
                    placeholder = "Mobile Number",
                    keyboardType = KeyboardType.Phone,
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = uiState.panNumber,
                    onValueChange = { input ->
                        // PAN format: 10 characters (letters and digits, uppercase)
                        val filtered = input.filter { it.isLetterOrDigit() }
                            .take(10)
                            .uppercase()
                        viewModel.updatePanNumber(filtered)
                    },
                    placeholder = "PAN Number",
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Address Section
                Text(
                    text = "Address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = uiState.houseNo,
                    onValueChange = { viewModel.updateHouseNo(it) },
                    placeholder = "House NO. / Flat No. Building Name",
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = uiState.street,
                    onValueChange = { viewModel.updateStreet(it) },
                    placeholder = "Area, Street, Landmark",
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomTextField(
                    value = uiState.state,
                    onValueChange = { viewModel.updateState(it) },
                    placeholder = "State / Union Territory",
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomTextField(
                        value = uiState.city,
                        onValueChange = { viewModel.updateCity(it) },
                        placeholder = "City",
                        modifier = Modifier.weight(1f),
                        borderColor = lightBorderColor
                    )
                    CustomTextField(
                        value = uiState.pincode,
                        onValueChange = { input ->
                            // Indian pincode: 6 digits only
                            val filtered = input.filter { it.isDigit() }.take(6)
                            viewModel.updatePincode(filtered)
                        },
                        placeholder = "Pin code",
                        modifier = Modifier.weight(1f),
                        keyboardType = KeyboardType.Number,
                        borderColor = lightBorderColor
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = lightBorderColor, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                // Show additional fields for Government Employee
                if (uiState.donorType == "government") {
                    CustomTextField(
                        value = uiState.department,
                        onValueChange = { viewModel.updateDepartment(it) },
                        placeholder = "Department",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = uiState.employeeId,
                        onValueChange = { viewModel.updateEmployeeId(it) },
                        placeholder = "Employee ID",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = uiState.designation,
                        onValueChange = { viewModel.updateDesignation(it) },
                        placeholder = "Designation",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = lightBorderColor, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Show additional fields for Corporate / Company Donor
                if (uiState.donorType == "corporate") {
                    CustomTextField(
                        value = uiState.companyName,
                        onValueChange = { viewModel.updateCompanyName(it) },
                        placeholder = "Company Name",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = uiState.gstNumber,
                        onValueChange = { viewModel.updateGstNumber(it) },
                        placeholder = "GST Number",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = uiState.contactPersonName,
                        onValueChange = { viewModel.updateContactPersonName(it) },
                        placeholder = "Contact Person Name",
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextField(
                        value = uiState.officialEmail,
                        onValueChange = { viewModel.updateOfficialEmail(it) },
                        placeholder = "Official Email",
                        keyboardType = KeyboardType.Email,
                        borderColor = lightBorderColor
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = lightBorderColor, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Enter Donation Amount Section
                Text(
                    text = "Enter Donation Amount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = uiState.donationAmount,
                    onValueChange = { input ->
                        // Only allow digits for donation amount
                        val filtered = input.filter { it.isDigit() }
                        viewModel.updateDonationAmount(filtered)
                    },
                    placeholder = "₹ Amount",
                    keyboardType = KeyboardType.Number,
                    borderColor = lightBorderColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Amount Buttons
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickAmountButton(
                            amount = "₹ 500",
                            isSelected = uiState.selectedQuickAmount == "500",
                            onClick = { viewModel.selectQuickAmount("500") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                        QuickAmountButton(
                            amount = "₹ 1,000",
                            isSelected = uiState.selectedQuickAmount == "1000",
                            onClick = { viewModel.selectQuickAmount("1000") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                        QuickAmountButton(
                            amount = "₹ 1,500",
                            isSelected = uiState.selectedQuickAmount == "1500",
                            onClick = { viewModel.selectQuickAmount("1500") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        QuickAmountButton(
                            amount = "₹ 2,000",
                            isSelected = uiState.selectedQuickAmount == "2000",
                            onClick = { viewModel.selectQuickAmount("2000") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                        QuickAmountButton(
                            amount = "₹ 2,500",
                            isSelected = uiState.selectedQuickAmount == "2500",
                            onClick = { viewModel.selectQuickAmount("2500") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                        QuickAmountButton(
                            amount = "₹ 3,000",
                            isSelected = uiState.selectedQuickAmount == "3000",
                            onClick = { viewModel.selectQuickAmount("3000") },
                            modifier = Modifier.weight(1f),
                            gradientBrush = gradientBrush,
                            borderColor = lightBorderColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Donate Now Button
                Button(
                    onClick = { onPaymentClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = gradientBrush,
                            shape = RoundedCornerShape(18.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues()
                ) {
                    Text(
                        text = "Donate Now",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
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
                .fillMaxWidth()
                .height(52.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(brush = gradientBrush, shape = RoundedCornerShape(26.dp))
        } else {
            modifier
                .fillMaxWidth()
                .height(52.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(26.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Transparent else Color.White
        ),
        border = if (!isSelected) BorderStroke(0.3.dp, borderColor) else null,
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
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 15.sp,
                color = Color(0xFFAAAAAA)
            )
        },
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
    )
}

@Composable
private fun QuickAmountButton(
    amount: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    gradientBrush: Brush,
    borderColor: Color
) {
    Button(
        onClick = onClick,
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
        border = if (!isSelected) BorderStroke(0.3.dp, borderColor) else null,
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
    DonorScreen()
}