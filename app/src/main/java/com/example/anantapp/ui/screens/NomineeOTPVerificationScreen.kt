package com.example.anantapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R

@Composable
fun NomineeOTPVerificationScreen(
    screenType: String = "nominee", // "nominee" or "family_member"
    onAddNomineeClick: () -> Unit = {},
    onSendOTPClick: () -> Unit = {},
    onVerifyOTPClick: () -> Unit = {},
    onGoBackClick: () -> Unit = {}
) {
    val phoneNumber = remember { mutableStateOf("") }
    val otpCode = remember { mutableStateOf("") }
    val isOTPVerified = remember { mutableStateOf(false) }

    val screenTitle = if (screenType == "family_member") "Add Family Member" else "Nominee Details"
    val addButtonText = if (screenType == "family_member") "Add Another Family Member" else "Add Nominee"

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Shared Bubble Background Image
        Image(
            painter = painterResource(id = R.drawable.blue_background_with_bubbles),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Main Frosted Glass Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.88f)
                .padding(vertical = 48.dp)
                // 1. SHADOW FIRST
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color.Black.copy(alpha = 0.15f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                // 2. CLIP & BACKGROUND SECOND
                .clip(RoundedCornerShape(32.dp))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.15f),
                            Color(0xFF4A9EFF).copy(alpha = 0.08f)
                        ),
                        radius = 400f
                    )
                )
                // 3. BORDER THIRD
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header: Minimalist Icon and Title
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    MinimalistProfileIcon()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = screenTitle,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Form Elements
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Add Nominee/Family Member Button
                    PillButtonOTP(
                        icon = Icons.Default.Add,
                        text = addButtonText,
                        onClick = onAddNomineeClick
                    )

                    // Verification Method Toggle
                    VerificationMethodToggle(
                        isOTPVerified = isOTPVerified.value
                    )

                    // OTP Verification Section
                    // Mobile Number Input Field with Send OTP
                    MobileNumberInputFieldOTP(
                        value = phoneNumber.value,
                        onValueChange = { phoneNumber.value = it },
                        onSendOTPClick = onSendOTPClick
                    )

                    // OTP Input Field
                    OTPInputFieldOTP(
                        value = otpCode.value,
                        onValueChange = { otpCode.value = it }
                    )

                    // Verify OTP Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            // 1. SHADOW FIRST
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.15f)
                            )
                            // 2. CLIP & BACKGROUND SECOND
                            .clip(RoundedCornerShape(28.dp))
                            .background(
                                if (isOTPVerified.value) Color.Green.copy(alpha = 0.65f)
                                else Color.White.copy(alpha = 0.65f)
                            )
                            .clickable(enabled = !isOTPVerified.value) {
                                isOTPVerified.value = true
                                onVerifyOTPClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isOTPVerified.value) "✓ OTP Verified" else "Verify OTP",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Footer: Go Back
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { onGoBackClick() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF222222)
                    )
                    Text(
                        text = "Go Back",
                        fontSize = 14.sp,
                        color = Color(0xFF222222),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun VerificationMethodToggle(
    isOTPVerified: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.7f))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isOTPVerified) "✓ OTP Verification" else "OTP Verification",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}


@Composable
private fun MobileNumberInputFieldOTP(
    value: String,
    onValueChange: (String) -> Unit,
    onSendOTPClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            // 1. SHADOW FIRST
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            // 2. CLIP & BACKGROUND SECOND
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(start = 24.dp, end = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = "Enter Mobile Number",
                        fontSize = 14.sp,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Normal
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 10) {
                            onValueChange(newValue)
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            // Black Send OTP Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.Black)
                    .clickable { onSendOTPClick() }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Send OTP",
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun OTPInputFieldOTP(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            // 1. SHADOW FIRST
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            // 2. CLIP & BACKGROUND SECOND
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = "Enter OTP",
                fontSize = 14.sp,
                color = Color(0xFFAAAAAA),
                fontWeight = FontWeight.Normal
            )
        }
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= 6) {
                    onValueChange(newValue)
                }
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
private fun PillButtonOTP(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            // 1. SHADOW FIRST
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            // 2. CLIP & BACKGROUND SECOND
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(3f)
            )

            // Empty spacer for balance
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// Recreating the exact thin-stroke icon from the design
@Composable
private fun MinimalistProfileIcon() {
    Canvas(modifier = Modifier.size(64.dp)) {
        val strokeWidth = 0.3f

        // Head circle
        drawCircle(
            color = Color.Black,
            radius = size.width * 0.20f,
            center = Offset(size.width / 2, size.height * 0.30f),
            style = Stroke(width = strokeWidth)
        )

        // Shoulders arc
        drawArc(
            color = Color.Black,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.15f, size.height * 0.45f),
            size = Size(size.width * 0.70f, size.height * 0.60f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun NomineeOTPVerificationScreenPreview() {
    androidx.compose.material3.MaterialTheme {
        NomineeOTPVerificationScreen()
    }
}