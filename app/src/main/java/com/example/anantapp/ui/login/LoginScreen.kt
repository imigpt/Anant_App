package com.example.anantapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.ui.theme.AnantAppTheme

// Gradient Colors for Icons and Login Button
private val PinkGradientStart = Color(0xFFE233FF)
private val PinkGradientEnd = Color(0xFFFF4848)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            snackbarHostState.showSnackbar(uiState.successMessage)
            viewModel.clearMessages()
            onLoginSuccess()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Clean white background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // 1. Logo at the top center
            Image(
                painter = painterResource(id = R.drawable.anant_logo), // Replace with your logo's file name
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Left-Aligned Header Text
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Login Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Hello, welcome back to our account!",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Phone & OTP Input Fields
            LoginForm(
                uiState = uiState,
                onPhoneNumberChange = viewModel::onPhoneNumberChange,
                onOtpChange = viewModel::onOtpChange,
                onRequestOtpClick = viewModel::requestOtp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Full-Width Gradient Login Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Brush.linearGradient(listOf(PinkGradientStart, PinkGradientEnd)))
                    .clickable { viewModel.verifyOtp() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 5. Divider
            DividerWithText(text = "Or login with")

            Spacer(modifier = Modifier.height(32.dp))

            // 6. Social Media Login Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Ensure you have these icons in your res/drawable folder
                SocialLoginButton(iconResId = R.drawable.ic_google, contentDescription = "Google")
                Spacer(modifier = Modifier.width(20.dp))
                SocialLoginButton(iconResId = R.drawable.ic_apple, contentDescription = "Apple")
                Spacer(modifier = Modifier.width(20.dp))
                SocialLoginButton(iconResId = R.drawable.ic_facebook, contentDescription = "Facebook")
            }

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(48.dp))

            // 7. Bottom Registration Link
            RegisterLink(onNavigate = onNavigateToRegister)
        }

        // Snackbar host for error/success messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) { data ->
            Snackbar(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(data.visuals.message)
            }
        }
    }
}

@Composable
private fun LoginForm(
    uiState: com.example.anantapp.data.model.LoginUiState,
    onPhoneNumberChange: (String) -> Unit,
    onOtpChange: (String) -> Unit,
    onRequestOtpClick: () -> Unit
) {
    Column {
        // Custom Pill-shaped Mobile Input
        CustomInputField(
            value = uiState.phoneNumber,
            onValueChange = onPhoneNumberChange,
            hint = "Mobile Number",
            icon = {
                IconGradientCircle {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            },
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Custom Pill-shaped OTP Input with inline button
        CustomInputField(
            value = uiState.otp,
            onValueChange = onOtpChange,
            hint = "OTP",
            icon = {
                IconGradientCircle {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "OTP Key",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            },
            keyboardType = KeyboardType.NumberPassword,
            trailingContent = {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .clickable { onRequestOtpClick() }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "request otp",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }
}

@Composable
private fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: @Composable () -> Unit,
    keyboardType: KeyboardType,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(50)) // Light gray border
            .background(Color.White, RoundedCornerShape(50))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(12.dp))
        
        Box(modifier = Modifier.weight(1f)) {
            if (value.isEmpty()) {
                Text(text = hint, color = Color.Gray, fontSize = 14.sp)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingContent()
        }
    }
}

@Composable
private fun IconGradientCircle(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(
                Brush.linearGradient(listOf(PinkGradientStart, PinkGradientEnd)),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun SocialLoginButton(iconResId: Int, contentDescription: String) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, Color(0xFFE0E0E0), CircleShape) // Light gray circle border
            .clip(CircleShape)
            .clickable { /* Handle click */ },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun DividerWithText(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color(0xFFE0E0E0)
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Color(0xFFE0E0E0)
        )
    }
}

@Composable
private fun RegisterLink(onNavigate: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Not register yet?",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Create Account",
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold, // Made bold to match the image perfectly
            textDecoration = TextDecoration.Underline, // Underlined to match the image
            modifier = Modifier.clickable { onNavigate() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    AnantAppTheme {
        LoginScreen()
    }
}
