package com.example.anantapp.feature.wallet.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.wallet.presentation.viewmodel.PaymentMethodViewModel

@Composable
fun PaymentMethodScreen(
    viewModel: PaymentMethodViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onPaymentComplete: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Background
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
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(24.dp)
                        .clickable { onBackClick() },
                    tint = Color.White
                )

                Text(
                    text = "Select Payment Method",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        // White Content Area
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
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                // Credit/Debit Card Section
                Text(
                    text = "Credit / Debit Card",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                PaymentMethodOption(
                    label = "Add Debit Card",
                    isSelected = uiState.selectedPaymentMethod == "debit_card",
                    onClick = { viewModel.selectPaymentMethod("debit_card") },
                    gradientBrush = gradientBrush
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Bank Transfer Section
                Text(
                    text = "Bank Transfer / Manual UPI",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                PaymentMethodOption(
                    label = "Manual Bank Deposit",
                    isSelected = uiState.selectedPaymentMethod == "bank_transfer",
                    onClick = { viewModel.selectPaymentMethod("bank_transfer") },
                    gradientBrush = gradientBrush
                )

                Spacer(modifier = Modifier.height(12.dp))

                PaymentMethodOption(
                    label = "Add UPI ID",
                    isSelected = uiState.selectedPaymentMethod == "upi",
                    onClick = { viewModel.selectPaymentMethod("upi") },
                    gradientBrush = gradientBrush
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Proceed Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFF9500FF), RoundedCornerShape(12.dp))
                        .clickable { onPaymentComplete() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Continue",
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
private fun PaymentMethodOption(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    gradientBrush: Brush,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 1.5.dp,
                brush = if (isSelected) gradientBrush else SolidColor(Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = if (isSelected) Color(0xFFF5F3FF) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = 2.dp,
                        brush = gradientBrush,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(
                        brush = if (isSelected) gradientBrush else SolidColor(Color.White),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentMethodScreenPreview() {
    AnantAppTheme {
        PaymentMethodScreen()
    }
}
