package com.example.anantapp.feature.wallet.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.wallet.presentation.viewmodel.AddBalanceViewModel

@Composable
fun AddBalanceScreen(
    viewModel: AddBalanceViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onProceedClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val textColor = Color(0xFF1A1A2E)
    val subtitleColor = Color(0xFF888888)
    val borderColor = Color(0xFFE0E0E0)
    val inputBgColor = Color(0xFFF5F6F8)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header Gradient
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
                        .padding(8.dp)
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onBackClick() },
                    tint = Color.White
                )

                Text(
                    text = "Top up Donation Balance",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        // Main Content Container
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
                    .padding(horizontal = 24.dp)
            ) {
                // Amount Input Field Label
                Text(
                    text = "Add",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = subtitleColor,
                    modifier = Modifier.padding(top = 32.dp, bottom = 12.dp)
                )

                // Custom Input Field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .background(color = inputBgColor, shape = RoundedCornerShape(12.dp))
                        .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rupee Symbol
                    Text(
                        text = "₹",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9500FF)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Text Input
                    BasicTextField(
                        value = uiState.amount,
                        onValueChange = { viewModel.updateAmount(it) },
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )

                    // Clear Icon
                    if (uiState.amount.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { viewModel.updateAmount("") },
                            tint = Color.Gray
                        )
                    }
                }

                // Suggested Amounts Grid
                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Row 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestedAmountButton("500", Modifier.weight(1f), viewModel, gradientBrush)
                        SuggestedAmountButton("1000", Modifier.weight(1f), viewModel, gradientBrush)
                        SuggestedAmountButton("2500", Modifier.weight(1f), viewModel, gradientBrush)
                    }
                    // Row 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestedAmountButton("5000", Modifier.weight(1f), viewModel, gradientBrush)
                        SuggestedAmountButton("10000", Modifier.weight(1f), viewModel, gradientBrush)
                        Box(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Proceed Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFF9500FF), RoundedCornerShape(16.dp))
                        .clickable { onProceedClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Proceed",
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
private fun SuggestedAmountButton(
    amount: String,
    modifier: Modifier = Modifier,
    viewModel: AddBalanceViewModel,
    gradientBrush: Brush
) {
    val isSelected = false
    
    Box(
        modifier = modifier
            .height(40.dp)
            .border(
                width = 1.dp,
                brush = if (isSelected) gradientBrush else SolidColor(Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                brush = if (isSelected) gradientBrush else SolidColor(Color.White),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { viewModel.selectQuickAmount(amount) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "₹ $amount",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF888888)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddBalanceScreenPreview() {
    AnantAppTheme {
        AddBalanceScreen()
    }
}
