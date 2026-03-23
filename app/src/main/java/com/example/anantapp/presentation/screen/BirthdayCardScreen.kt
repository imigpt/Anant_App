package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Birthday Card Screen
 * Displays personalized birthday card with donation call-to-action
 */
@Composable
fun BirthdayCardScreen(
    personName: String = "Mahendra",
    age: Int = 24,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDonateClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // ==================== Main Content ====================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF7C3AED),
                            Color(0xFFEC4899)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // ==================== Status Bar ====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "9:41",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "●●●",
                        fontSize = 8.sp,
                        color = Color.White
                    )
                    Text(
                        text = "●",
                        fontSize = 8.sp,
                        color = Color.White
                    )
                    Text(
                        text = "●●●",
                        fontSize = 8.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== Header with Back Button & Branding ====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Branding
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Shield Icon
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🛡️",
                            fontSize = 18.sp
                        )
                    }

                    Text(
                        text = "Anant",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(40.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== Profile Photo with Sparkles ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                // Circular Profile Picture
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.3f),
                                    Color.White.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        )
                        .padding(3.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(174.dp)
                            .background(Color(0xFFF5F5F5), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile Picture",
                            tint = Color(0xFFCCCCCC),
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }

                // Sparkles around photo
                SparkleIcon(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(20.dp, 10.dp)
                )
                SparkleIcon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset((-20).dp, 15.dp)
                )
                SparkleIcon(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(25.dp, (-10).dp)
                )
                SparkleIcon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset((-25).dp, (-5).dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== Birthday Greeting ====================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // "Happy Birthday" with striped effect
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Shadow/Base layer
                    Text(
                        text = "Happy Birthday",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White.copy(alpha = 0.3f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.offset(2.dp, 2.dp)
                    )

                    // Main text with gradient
                    Text(
                        text = "Happy Birthday",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Person's Name with underline
                Text(
                    text = "$personName!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Birthday Message
                Text(
                    text = "Wishing you true success and healthy life on your ${age}th birthday. Make this birthday more happy by donating on anant platform Donate now and be a Superhero and bring a new life to others.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.95f),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Share Button
                Row(
                    modifier = Modifier
                        .clickable { onShareClick() }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Share",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ==================== Donate Button ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(56.dp)
                    .background(Color.White, shape = RoundedCornerShape(28.dp))
                    .clickable { onDonateClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Donate to Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEC4899),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Sparkle Icon Component
 * Four-pointed star icon
 */
@Composable
private fun SparkleIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "✦",
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBirthdayCardScreen() {
    BirthdayCardScreen()
}
