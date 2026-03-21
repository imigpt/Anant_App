package com.example.anantapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.ui.theme.ErrorRed

private val PinkGradientStart = Color(0xFFE233FF)
private val PinkGradientEnd = Color(0xFFFF4848)

@Composable
fun OTPInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "OTP",
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    onRequestOtp: () -> Unit = {} // Added inline button action
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = if (isError) ErrorRed else Color.LightGray,
                    shape = RoundedCornerShape(50)
                )
                .background(Color.White, RoundedCornerShape(50))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading Icon inside a Gradient Circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        Brush.linearGradient(listOf(PinkGradientStart, PinkGradientEnd)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "OTP",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text Input Area
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(text = hint, color = Color.LightGray, fontSize = 14.sp)
                }
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 6 && newValue.all { it.isDigit() }) {
                            onValueChange(newValue)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Trailing "request otp" Button
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(50))
                    .clip(RoundedCornerShape(50))
                    .clickable { onRequestOtp() }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "request otp",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        // Error Message (if any)
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelSmall,
                color = ErrorRed,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}