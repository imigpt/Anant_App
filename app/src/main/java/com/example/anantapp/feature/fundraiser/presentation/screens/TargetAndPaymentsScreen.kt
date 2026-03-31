package com.example.anantapp.feature.fundraiser.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.TargetAndPaymentsViewModel
import com.example.anantapp.ui.components.FormLabel

@Composable
fun TargetAndPaymentsScreen(
    viewModel: TargetAndPaymentsViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
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
                    text = "Target & Payments",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Target Amount
                FormLabel(text = "Target Amount *")
                Spacer(modifier = Modifier.height(8.dp))
                TargetAmountInput(
                    amount = uiState.targetAmount,
                    currency = uiState.currency,
                    onAmountChange = { viewModel.updateTargetAmount(it) },
                    onCurrencyChange = { viewModel.updateCurrency(it) },
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Auto-topup Toggle
                FormLabel(text = "Auto-topup Settings")
                Spacer(modifier = Modifier.height(12.dp))
                AutoTopupToggle(
                    isEnabled = uiState.isAutoTopupEnabled,
                    onToggle = { viewModel.toggleAutoTopup() },
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Draft Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .border(1.5.dp, mainGradient, CircleShape)
                        .clip(CircleShape)
                        .clickable { onDraftSaved() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Draft",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9500FF)
                    )
                }

                // Next Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(mainGradient, CircleShape)
                        .clickable { onNextClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TargetAmountInput(
    amount: String,
    currency: String,
    onAmountChange: (String) -> Unit,
    onCurrencyChange: (String) -> Unit,
    mainGradient: Brush
) {
    var showDropdown by remember { mutableStateOf(false) }
    val currencies = listOf("INR", "USD", "EUR", "GBP")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(1.5.dp, mainGradient, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Currency Dropdown
        Box(
            modifier = Modifier
                .width(80.dp)
                .clickable { showDropdown = !showDropdown },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currency,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = { showDropdown = false },
                modifier = Modifier.width(80.dp)
            ) {
                currencies.forEach { curr ->
                    DropdownMenuItem(
                        text = { Text(curr) },
                        onClick = {
                            onCurrencyChange(curr)
                            showDropdown = false
                        }
                    )
                }
            }
        }

        // Divider
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(1.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Amount Input
        BasicTextField(
            value = amount,
            onValueChange = onAmountChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (amount.isEmpty()) {
                    Text("0", color = Color.Gray, fontSize = 14.sp)
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun AutoTopupToggle(
    isEnabled: Boolean,
    onToggle: () -> Unit,
    mainGradient: Brush
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onToggle() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Enable Auto-topup",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1976D2)
            )

            // Toggle Switch
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(28.dp)
                    .then(
                        if (isEnabled) Modifier.background(mainGradient, CircleShape)
                        else Modifier.background(Color.Gray, CircleShape)
                    ),
                contentAlignment = if (isEnabled) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TargetAndPaymentsScreenPreview() {
    AnantAppTheme {
        TargetAndPaymentsScreen()
    }
}
