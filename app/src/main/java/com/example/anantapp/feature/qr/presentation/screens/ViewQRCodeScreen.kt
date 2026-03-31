package com.example.anantapp.feature.qr.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.qr.presentation.viewmodel.ViewQRCodeViewModel

@Composable
fun ViewQRCodeScreen(
    viewModel: ViewQRCodeViewModel = viewModel(),
    onShareQRClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF2F4B))
    )
    
    val lightBlueGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF60A5FA), Color(0xFF3B82F6))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F5F9))
    ) {
        // Decorative circles
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-20).dp)
                .background(Color(0xFF1B74E4), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 40.dp)
                .background(Color(0xFF1B74E4), CircleShape)
        )

        // Main Card
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    text = "Your QR Code",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                // QR Code Placeholder
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.White)
                        .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "QR CODE",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Download or share your QR code",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.weight(1f))

                // Action Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Share Button
                    Button(
                        onClick = { onShareQRClick() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF9500FF)
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFF9500FF))
                    ) {
                        Text("Share", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    // Download Button
                    Button(
                        onClick = { onDownloadQRClick() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF9500FF)
                        )
                    ) {
                        Text("Download", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewQRCodeScreenPreview() {
    AnantAppTheme {
        ViewQRCodeScreen()
    }
}
