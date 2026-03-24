package com.example.anantapp.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R

// ==================== Main Screen ====================

@Composable
fun GenerateQRCodeInfoScreen(
    onShareQRClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {}
) {
    GenerateQRCodeInfoContent(
        onShareQRClick = onShareQRClick,
        onDownloadQRClick = onDownloadQRClick
    )
}

@Composable
fun GenerateQRCodeInfoContent(
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
        // 1. Top-left SOLID blue circle (Behind Card)
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopStart)
                .offset(x = (-30).dp, y = (-20).dp)
                .background(color = solidBlueAccent, shape = CircleShape)
        )

        // 2. Bottom-right SOLID blue circle (Behind Card)
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 20.dp)
                .background(color = solidBlueAccent, shape = CircleShape)
        )

        // Main content Card Container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = Color.Black.copy(alpha = 0.2f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                // Wrapper inside the card to hold the inner corner gradients
                Box(modifier = Modifier.fillMaxSize()) {

                    // Top-left inner gradient blur
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.TopStart)
                            .offset(x = (-80).dp, y = (-80).dp)
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

                    // Bottom-right inner gradient blur
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.BottomEnd)
                            .offset(x = 80.dp, y = 80.dp)
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(32.dp))

                        // QR Graphic Top Icon
                        Image(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = "QR Graphic",
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Title
                        Text(
                            text = "Generate QR Code",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Subtitle
                        Text(
                            text = "Print & stick this QR on your bike, car,\nor helmet for instant help.",
                            fontSize = 14.sp,
                            color = Color(0xFF444444),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        // Benefits List
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "🚨 People scan if you're in an accident.",
                                fontSize = 14.sp,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "📞 Emergency calls",
                                fontSize = 14.sp,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "🔔 Family & admin get instant alerts if scanned.",
                                fontSize = 14.sp,
                                color = Color(0xFF333333)
                            )
                        }

                        // Pushes buttons to the bottom
                        Spacer(modifier = Modifier.weight(1f))

                        // Buttons Section (Stacked vertically)
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Define the linear gradient
                            val buttonGradient = Brush.verticalGradient(
                                colors = listOf(Color.White, Color(0xFFEAEAEA))
                            )

                            // Share QR Button
                            Button(
                                onClick = onShareQRClick,
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(46.dp)
                                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(50.dp), spotColor = Color.Black.copy(0.1f))
                                    .background(brush = buttonGradient, shape = RoundedCornerShape(50.dp)),
                                shape = RoundedCornerShape(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Share QR Code",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }

                            // Download QR Button
                            Button(
                                onClick = onDownloadQRClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(50.dp), spotColor = Color.Black.copy(0.15f))
                                    .background(brush = buttonGradient, shape = RoundedCornerShape(50.dp)),
                                shape = RoundedCornerShape(50.dp),
                                border = BorderStroke(1.dp, Color.Black),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(
                                    text = "Download QR Code",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Security Footer
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🔒",
                                fontSize = 12.sp,
                                color = Color(0xFFB0B0B0)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "All scans are logged securely",
                                fontSize = 13.sp,
                                color = Color(0xFFB0B0B0)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenerateQRCodeInfoScreen() {
    GenerateQRCodeInfoContent()
}
