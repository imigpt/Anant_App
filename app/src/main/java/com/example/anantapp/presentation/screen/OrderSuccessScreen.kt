package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderSuccessScreen(
    deliveryAddress: String = "Horizon Towers Near World\nTrade Park Jaipur",
    estimatedDelivery: String = "5-7 days.",
    onDownloadPDFClick: () -> Unit = {},
    onOrderStatusClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Main background
    ) {
        // Solid blue circle top-left (Behind Card)
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = 60.dp)
                .size(180.dp)
                .background(Color(0xFF277BEE), CircleShape)
                .blur(30.dp)
        )

        // Solid blue circle bottom-right (Behind Card)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .size(180.dp)
                .background(Color(0xFF1E6FD9), CircleShape)
                .blur(30.dp)
        )

        Spacer(modifier = Modifier.height(60.dp))


        // Main Card Container with Glassmorphism
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 80.dp)
                .shadow(16.dp, RoundedCornerShape(24.dp))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.2f)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    // Custom Thin Outline Checkmark
                    Canvas(modifier = Modifier.size(90.dp)) {
                        drawCircle(
                            color = Color(0xFF16E030),
                            style = Stroke(width = 3.dp.toPx())
                        )
                        val path = Path().apply {
                            moveTo(size.width * 0.3f, size.height * 0.52f)
                            lineTo(size.width * 0.45f, size.height * 0.65f)
                            lineTo(size.width * 0.72f, size.height * 0.35f)
                        }
                        drawPath(
                            path = path,
                            color = Color(0xFF16E030),
                            style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Header
                    Text(
                        text = "QR Sticker Ordered\nSuccessfully!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Subtitle
                    Text(
                        text = "Thank you for verifying your details.\nYour QR sticker will reach you at:",
                        fontSize = 13.sp,
                        color = Color(0xFF4A4A4A),
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Location Info
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier.size(28.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = deliveryAddress,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            lineHeight = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Time Info
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.HourglassEmpty,
                            contentDescription = "Time",
                            modifier = Modifier.size(28.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Estimated Delivery: $estimatedDelivery",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Download PDF Button (Pill shape)
                    Surface(
                        onClick = onDownloadPDFClick,
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "Download QR PDF (Print Yourself)",
                                fontSize = 12.sp,
                                color = Color(0xFF4A4A4A),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Bottom Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Order Status
                        Button(
                            onClick = onOrderStatusClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .border(1.dp, Color(0xFF277BEE), RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Order Status",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF4A4A4A)
                            )
                        }

                        // Home
                        val homeGradient = Brush.verticalGradient(
                            colors = listOf(Color(0xFF006FEE), Color(0xFF7DBAFF))
                        )
                        Button(
                            onClick = onHomeClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .background(brush = homeGradient, shape = RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = "Home",
                                fontSize = 14.sp,
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

@Preview
@Composable
fun PreviewOrderSuccess() {
    OrderSuccessScreen()
}
