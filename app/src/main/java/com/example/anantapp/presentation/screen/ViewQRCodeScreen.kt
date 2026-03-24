package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R
import androidx.compose.foundation.Image

// ==================== Main Screen ====================

@Composable
fun ViewQRCodeScreen(
    onShareQRClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {}
) {
    ViewQRCodeContent(
        onShareQRClick = onShareQRClick,
        onDownloadQRClick = onDownloadQRClick
    )
}

@Composable
fun ViewQRCodeContent(
    onShareQRClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {}
) {
    // Colors matching the UI design
    val solidBlueAccent = Color(0xFF1B74E4)
    val lightGradientBlue = Color(0xFF60A5FA)
    val backgroundColor = Color(0xFFF4F5F9)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // 1. Top-right SOLID blue circle (Behind Card)
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-20).dp)
                .background(color = solidBlueAccent, shape = CircleShape)
        )

        // 2. Bottom-left SOLID blue circle (Behind Card)
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 40.dp)
                .background(color = solidBlueAccent, shape = CircleShape)
        )

        // Main content Card Container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = Color.Black.copy(alpha = 0.15f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                // Wrapper inside the card to hold the inner corner gradients
                Box(modifier = Modifier.fillMaxSize()) {

                    // Top-right inner gradient blur
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 90.dp, y = (-90).dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        lightGradientBlue.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    // Bottom-left inner gradient blur
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.BottomStart)
                            .offset(x = (-90).dp, y = 90.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        lightGradientBlue.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )

                    // Actual Content Layout
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp, vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Title
                        Text(
                            text = "Your QR Code",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        // QR Code Display
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(Color.White)
                                .border(2.dp, Color(0xFF000000)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.qr_code),
                                contentDescription = "QR Code",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                            )
                        }

                        // Brand Logo Section
                        Image(
                            painter = painterResource(id = R.drawable.anant_logo),
                            contentDescription = "Anant Logo",
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )

                        // Button Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Share QR Button - Gradient Style (#FFFFFF and #EAEAEA)
                            val shareGradient = Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFFFFF), Color(0xFFEAEAEA))
                            )
                            Button(
                                onClick = onShareQRClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                                    .background(brush = shareGradient, shape = RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Share QR",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF333333)
                                )
                            }

                            // Download QR Button - Gradient Style (#006FEE and #7DBAFF)
                            val downloadGradient = Brush.verticalGradient(
                                colors = listOf(Color(0xFF006FEE), Color(0xFF7DBAFF))
                            )
                            Button(
                                onClick = onDownloadQRClick,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                                    .background(brush = downloadGradient, shape = RoundedCornerShape(12.dp)),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Download QR",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewViewQRCodeScreen() {
    ViewQRCodeContent()
}
