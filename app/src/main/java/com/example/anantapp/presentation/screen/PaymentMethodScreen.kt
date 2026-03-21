package com.example.anantapp.presentation.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.PaymentMethodViewModel
import java.util.Calendar

/**
 * Production-level Payment Method Selection Screen
 * Updated to expand Manual Bank Deposit options dynamically and include custom pickers.
 */
@Composable
fun PaymentMethodScreen(
    onBackClick: () -> Unit = {},
    onPaymentComplete: () -> Unit = {},
    viewModel: PaymentMethodViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val gradientBlue = Color(0xFF8D14FF)
    val gradientPink = Color(0xFFFF4B6A)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(gradientBlue, gradientPink),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    // Light gray color for borders and placeholders matching the design
    val lightBorderColor = Color(0xFFE5E5E5)
    val placeholderColor = Color(0xFFAAAAAA)
    val textColor = Color(0xFF333333)

    // Local state for the new manual deposit fields
    var bankName by remember { mutableStateOf("") }
    var accountHolder by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var depositDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    
    // State for Expiry Picker (Month/Year)
    var showExpiryPicker by remember { mutableStateOf(false) }

    // Date Picker Logic for Deposit Date
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            depositDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    // Custom Month/Year Picker Dialog for Card Expiry
    if (showExpiryPicker) {
        MonthYearPickerDialog(
            onDismiss = { showExpiryPicker = false },
            onConfirm = { selectedMonth, selectedYear ->
                val formattedExpiry = String.format("%02d/%02d", selectedMonth, selectedYear % 100)
                viewModel.updateCardExpiry(formattedExpiry)
                showExpiryPicker = false
            }
        )
    }

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
                    text = "Choose Payment Method",
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
                // Select Payment Method Title
                Text(
                    text = "Select Payment Method",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Google Pay
                PaymentMethodOption(
                    icon = "💳",
                    label = "Google Pay",
                    isSelected = uiState.selectedPaymentMethod == "googleplay",
                    gradientBrush = gradientBrush,
                    onClick = { viewModel.updatePaymentMethod("googleplay") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Phone Pay
                PaymentMethodOption(
                    icon = "📱",
                    label = "Phone Pay",
                    isSelected = uiState.selectedPaymentMethod == "phonepay",
                    gradientBrush = gradientBrush,
                    onClick = { viewModel.updatePaymentMethod("phonepay") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Amazon Pay
                PaymentMethodOption(
                    icon = "🛒",
                    label = "Amazon Pay",
                    isSelected = uiState.selectedPaymentMethod == "amazonpay",
                    gradientBrush = gradientBrush,
                    onClick = { viewModel.updatePaymentMethod("amazonpay") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // PayPal
                PaymentMethodOption(
                    icon = "🅿️",
                    label = "PayPal",
                    isSelected = uiState.selectedPaymentMethod == "paypal",
                    gradientBrush = gradientBrush,
                    onClick = { viewModel.updatePaymentMethod("paypal") }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Manual Bank Deposit
                val isBankDepositSelected = uiState.selectedPaymentMethod == "bankdeposit"
                PaymentMethodOption(
                    icon = "🏦",
                    label = "Manual Bank Deposit",
                    isSelected = isBankDepositSelected,
                    showRadio = false,
                    isExpanded = isBankDepositSelected,
                    gradientBrush = gradientBrush,
                    onClick = { viewModel.updatePaymentMethod("bankdeposit") }
                )

                Spacer(modifier = Modifier.height(28.dp))

                if (isBankDepositSelected) {
                    // --- Bank Details Expanded Form ---
                    Text(
                        text = "Bank Details",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    val fieldModifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                    val fieldShape = RoundedCornerShape(12.dp)
                    val fieldColors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = lightBorderColor,
                        focusedBorderColor = gradientBlue,
                        unfocusedTextColor = textColor,
                        focusedTextColor = textColor
                    )
                    val textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)

                    // Bank Name
                    OutlinedTextField(
                        value = bankName,
                        onValueChange = { bankName = it },
                        placeholder = { Text("Bank Name", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Account Holder
                    OutlinedTextField(
                        value = accountHolder,
                        onValueChange = { accountHolder = it },
                        placeholder = { Text("Account Holder", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Account Number
                    OutlinedTextField(
                        value = accountNumber,
                        onValueChange = { accountNumber = it },
                        placeholder = { Text("Account Number", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // IFSC Code
                    OutlinedTextField(
                        value = ifscCode,
                        onValueChange = { ifscCode = it },
                        placeholder = { Text("IFSC Code", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Branch
                    OutlinedTextField(
                        value = branch,
                        onValueChange = { branch = it },
                        placeholder = { Text("Branch", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Enter Amount
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("₹ Enter Amount", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier,
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = textStyle
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Upload Deposit Receipt Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(1.dp, lightBorderColor, RoundedCornerShape(12.dp))
                            .clickable { /* Handle file upload */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.Description,
                                contentDescription = "Upload Document",
                                modifier = Modifier.size(48.dp),
                                tint = textColor
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Upload Deposit Receipt",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = textColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "JPG, PNG, PDF (Max 5MB)",
                                fontSize = 12.sp,
                                color = placeholderColor
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))

                    // Date of Deposit with Required Asterisk
                    Text(
                        text = buildAnnotatedString {
                            append("Date of Deposit ")
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("*")
                            }
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = depositDate,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = { Text("Select Date", fontSize = 15.sp, color = placeholderColor) },
                        modifier = fieldModifier.clickable { datePickerDialog.show() },
                        shape = fieldShape,
                        colors = fieldColors,
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { datePickerDialog.show() }) {
                                Icon(
                                    imageVector = Icons.Outlined.DateRange,
                                    contentDescription = "Calendar",
                                    tint = textColor
                                )
                            }
                        },
                        textStyle = textStyle,
                        enabled = true
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Notes/Remarks
                    Text(
                        text = "Notes/Remarks (Optional)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = { Text("Any reference number or note", fontSize = 15.sp, color = placeholderColor) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = fieldShape,
                        colors = fieldColors,
                        textStyle = textStyle,
                        maxLines = 4
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Disclaimer Text
                    Text(
                        text = "Your deposit will be verified by admin within 24 hrs.\nYou'll receive an update once credited",
                        fontSize = 11.sp,
                        color = Color(0xFFBDBDBD),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Deposit Proof Button
                    OutlinedButton(
                        onClick = { /* Handle submit proof */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, gradientBrush)
                    ) {
                        Text(
                            text = "Submit Deposit Proof",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Donate Now Button
                    Button(
                        onClick = {
                            viewModel.processPayment()
                            onPaymentComplete()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = gradientBrush,
                                shape = RoundedCornerShape(16.dp)
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
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                } else {
                    // --- Credit / Debit Card Section (Original) ---
                    Text(
                        text = "Credit / Debit Card",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    OutlinedTextField(
                        value = uiState.cardHolderName,
                        onValueChange = { viewModel.updateCardHolderName(it) },
                        placeholder = { Text("Card Holder Name", fontSize = 15.sp, color = placeholderColor) },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = lightBorderColor,
                            focusedBorderColor = gradientBlue,
                            unfocusedTextColor = textColor,
                            focusedTextColor = textColor
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = uiState.cardNumber,
                        onValueChange = { viewModel.updateCardNumber(it) },
                        placeholder = { Text("Card Number", fontSize = 15.sp, color = placeholderColor) },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = lightBorderColor,
                            focusedBorderColor = gradientBlue,
                            unfocusedTextColor = textColor,
                            focusedTextColor = textColor
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Expires Field with Year and Month Picker
                        OutlinedTextField(
                            value = uiState.cardExpiry,
                            onValueChange = { },
                            readOnly = true,
                            placeholder = { Text("MM/YY", fontSize = 15.sp, color = placeholderColor) },
                            trailingIcon = {
                                IconButton(onClick = { showExpiryPicker = true }) {
                                    Icon(
                                        imageVector = Icons.Outlined.DateRange,
                                        contentDescription = "Calendar Icon",
                                        tint = placeholderColor
                                    )
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(54.dp)
                                .clickable { showExpiryPicker = true },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = lightBorderColor,
                                focusedBorderColor = gradientBlue,
                                unfocusedTextColor = textColor,
                                focusedTextColor = textColor
                            ),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
                            enabled = true
                        )

                        OutlinedTextField(
                            value = uiState.cardCVV,
                            onValueChange = { viewModel.updateCardCVV(it) },
                            placeholder = { Text("CVV", fontSize = 15.sp, color = placeholderColor) },
                            modifier = Modifier.weight(1f).height(54.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = lightBorderColor,
                                focusedBorderColor = gradientBlue,
                                unfocusedTextColor = textColor,
                                focusedTextColor = textColor
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp)
                        )
                    }

                    Spacer(modifier = Modifier.height(36.dp))

                    // Donate Now Button
                    Button(
                        onClick = {
                            viewModel.processPayment()
                            onPaymentComplete()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(
                                brush = gradientBrush,
                                shape = RoundedCornerShape(16.dp)
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
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * A custom dialog for selecting Month and Year (MM/YY format).
 */
@Composable
private fun MonthYearPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    val months = (1..12).toList()
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val years = (currentYear..currentYear + 20).toList()

    var selectedMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) }
    var selectedYear by remember { mutableStateOf(currentYear) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Expiry Date",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Month Picker Column
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(months) { month ->
                            val isSelected = selectedMonth == month
                            Text(
                                text = String.format("%02d", month),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedMonth = month }
                                    .background(if (isSelected) Color(0xFF8D14FF).copy(alpha = 0.1f) else Color.Transparent)
                                    .padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF8D14FF) else Color.Black
                            )
                        }
                    }
                    
                    // Year Picker Column
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(years) { year ->
                            val isSelected = selectedYear == year
                            Text(
                                text = year.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedYear = year }
                                    .background(if (isSelected) Color(0xFF8D14FF).copy(alpha = 0.1f) else Color.Transparent)
                                    .padding(vertical = 12.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF8D14FF) else Color.Black
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.Gray)
                    }
                    TextButton(
                        onClick = { onConfirm(selectedMonth, selectedYear) }
                    ) {
                        Text("OK", color = Color(0xFF8D14FF), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentMethodOption(
    icon: String,
    label: String,
    isSelected: Boolean,
    showRadio: Boolean = true,
    isExpanded: Boolean = false,
    gradientBrush: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF8D14FF) else Color(0xFFE5E5E5),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = icon,
                    fontSize = 24.sp
                )
                Text(
                    text = label,
                    fontSize = 16.sp,
                    color = Color(0xFF444444)
                )
            }

            if (showRadio) {
                CustomGradientRadioButton(
                    selected = isSelected,
                    brush = gradientBrush
                )
            } else {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
private fun CustomGradientRadioButton(
    selected: Boolean,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(22.dp)) {
        if (selected) {
            drawCircle(
                brush = brush,
                style = Stroke(width = 3.dp.toPx())
            )
            drawCircle(
                brush = brush,
                radius = size.minDimension / 2 - 6.dp.toPx()
            )
        } else {
            drawCircle(
                color = Color(0xFFE0E0E0),
                style = Stroke(width = 1.5.dp.toPx())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentMethodScreenPreview() {
    PaymentMethodScreen()
}