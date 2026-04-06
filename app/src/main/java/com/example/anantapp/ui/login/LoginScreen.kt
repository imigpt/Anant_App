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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.VpnKey
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
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

// Gradient Colors for Icons matching the design
private val PinkGradientStart = Color(0xFF8D14FF)
private val PinkGradientEnd = Color(0xFFFF1E4F)

// Background Gradient Colors
private val BgGradientStart = Color(0xFFFBF1B6)
private val BgGradientEnd = Color(0xFF958F6C)

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

    // Outer Background with soft yellowish/gray gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BgGradientStart, BgGradientEnd)))
    ) {
        // Inner White Card with heavily rounded top corners and shadow
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 300.dp) // Space for the top background to show
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                    clip = false
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp)
                )
                .padding(horizontal = 24.dp)
                .imePadding()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // 1. Logo
            Image(
                painter = painterResource(id = R.drawable.anant_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                // Adjusted to fit horizontal logo layout
                contentScale = ContentScale.Crop
            )

//            Spacer(modifier = Modifier.height(48.dp))

            // 2. Left-Aligned Title "Login"
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Login",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Inputs (Mobile & OTP)
            LoginForm(
                uiState = uiState,
                onPhoneNumberChange = viewModel::onPhoneNumberChange,
                onOtpChange = viewModel::onOtpChange,
                onRequestOtpClick = viewModel::requestOtp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 4. Divider
//            DividerWithText(text = "Or continue with")

//            Spacer(modifier = Modifier.height(32.dp))

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(48.dp))

            // 6. Footer Registration Link
            RegisterLink(onNavigate = onNavigateToRegister)
        }

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
        // Mobile Number Input
        CustomInputField(
            value = uiState.phoneNumber,
            onValueChange = { input -> onPhoneNumberChange(input.filter { it.isDigit() }.take(10)) },
            hint = "Enter Mobile Number",
            icon = {
                IconGradientCircle {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid, // Looks closer to a mobile phone
                        contentDescription = "Phone",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        // OTP Input
        CustomInputField(
            value = uiState.otp,
            onValueChange = { input -> onOtpChange(input.filter { it.isDigit() }.take(6)) },
            hint = "OTP",
            icon = {
                IconGradientCircle {
                    Icon(
                        imageVector = Icons.Default.VpnKey, // Looks closer to a key
                        contentDescription = "OTP Key",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            },
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
            trailingContent = {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .clickable { onRequestOtpClick() }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
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

// Helper: restrict input to digits and max length when calling from UI
private fun String.digitsMax(maxLength: Int): String = this.filter { it.isDigit() }.take(maxLength)

@Composable
private fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: @Composable () -> Unit,
    keyboardType: KeyboardType,
    imeAction: ImeAction = ImeAction.Default,
    trailingContent: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, Color(0xFF6C6C6C), RoundedCornerShape(50)) // Thin dark gray border
            .background(Color.White, RoundedCornerShape(50))
            .padding(horizontal = 8.dp), // Inner padding for the circle icon
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(12.dp))

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                cursorBrush = SolidColor(Color.Black),
                modifier = Modifier.fillMaxWidth().imePadding()
            )
        }

        if (trailingContent != null) {
            trailingContent()
        }
    }
}

@Composable
private fun IconGradientCircle(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                Brush.linearGradient(
                    colors = listOf(PinkGradientStart, PinkGradientEnd)
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

//@Composable
//private fun DividerWithText(text: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        HorizontalDivider(
//            modifier = Modifier.weight(1f),
//            color = Color(0xFFE0E0E0),
//            thickness = 1.dp
//        )
//        Text(
//            text = text,
//            modifier = Modifier.padding(horizontal = 16.dp),
//            fontSize = 14.sp,
//            color = Color.Gray
//        )
//        HorizontalDivider(
//            modifier = Modifier.weight(1f),
//            color = Color(0xFFE0E0E0),
//            thickness = 1.dp
//        )
//    }
//}

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
            text = "No account yet?",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Register now",
            fontSize = 14.sp,
            color = Color.Gray, // Keeping it gray to match the image closely
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
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
