package com.example.anantapp.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// import com.example.anantapp.ui.theme.AnantAppTheme // Uncomment if you are using a custom theme

@Composable
fun AddBalanceScreen(
    onBackClick: () -> Unit = {}
) {
    // 1. Background & Header Colors
    val gradientPurple = Color(0xFF9500FF)
    val gradientPink = Color(0xFFFF6264)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(gradientPurple, gradientPink),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    val textColor = Color(0xFF1A1A2E)
    val subtitleColor = Color(0xFF888888)
    val borderColor = Color(0xFFE0E0E0)
    val inputBgColor = Color(0xFFF5F6F8)

    var amountText by remember { mutableStateOf("500") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- Header Gradient ---
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
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
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

        // --- 2. Main Content Container ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp) // Overlaps header by 30dp
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


                // --- 4. Amount Input Field ---
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
                        .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading Gradient Circle
                    Box(

                    ) {
                        Text(
                            text = "₹",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Text Input
                    BasicTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        ),
                        singleLine = true
                    )

                    // Trailing Clear Icon
                    if (amountText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { amountText = "" },
                            tint = Color.Black
                        )
                    }
                }

                // --- 5. Suggested Amounts Grid ---
                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Row 1
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestedAmountButton("500", Modifier.weight(1f)) { amountText = "500" }
                        SuggestedAmountButton("1,000", Modifier.weight(1f)) { amountText = "1000" }
                        SuggestedAmountButton("1,500", Modifier.weight(1f)) { amountText = "1500" }
                    }
                    // Row 2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SuggestedAmountButton("2,000", Modifier.weight(1f)) { amountText = "2000" }
                        SuggestedAmountButton("2,500", Modifier.weight(1f)) { amountText = "2500" }
                        SuggestedAmountButton("3,000", Modifier.weight(1f)) { amountText = "3000" }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // --- 6. Proceed Button ---
                Button(
                    onClick = { /* Handle Proceed */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = gradientPurple)
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
fun SuggestedAmountButton(amount: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp)
            .border(
                BorderStroke(1.dp, Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF888888)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "₹ $amount",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddBalanceScreenPreview() {
    // AnantAppTheme {
    AddBalanceScreen()
    // }
}