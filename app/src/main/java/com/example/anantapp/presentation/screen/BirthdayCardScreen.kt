package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R

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
    val birthdayGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF8D14FF), // Purple
            Color(0xFFFF1E4F)  // Pink/Red
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF8D14FF), // Purple
                        Color(0xFFFF1E4F)  // Pink/Red
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        // ==================== Top Left Background Circles ====================
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = 100.dp.toPx(),
                center = Offset(0f, 40.dp.toPx())
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = 130.dp.toPx(),
                center = Offset(0f, 40.dp.toPx())
            )
        }

        // ==================== Main Content ====================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            // ==================== Header with Back Button & Branding ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                // Back Button
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(28.dp)
                        .clickable { onBackClick() }
                )

                // Anant Logo (Centered)
                Image(
                    painter = painterResource(id = R.drawable.anant_logo),
                    contentDescription = "Anant Logo",
                    colorFilter = ColorFilter.tint(Color.White),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(150.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ==================== Profile Photo with Sparkles ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp) // Left aligned like the design
            ) {
                Box {
                    // Circular Profile Picture
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Color.White, shape = CircleShape)
                            .padding(3.dp)
                            .background(Color(0xFFF5F5F5), shape = CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Ideally an Image here, using Icon as placeholder
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile Picture",
                            tint = Color(0xFFCCCCCC),
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    // Sparkles around photo
                    SparkleIcon(
                        modifier = Modifier
                            .offset(x = 135.dp, y = 10.dp)
                            .size(32.dp)
                    )
                    SparkleIcon(
                        modifier = Modifier
                            .offset(x = 175.dp, y = 45.dp)
                            .size(16.dp)
                    )
                    SparkleIcon(
                        modifier = Modifier
                            .offset(x = (-10).dp, y = 120.dp)
                            .size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== Birthday Greeting (Left Aligned) ====================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // "Happy Birthday" outlined text effect
                Text(
                    text = "Happy Birthday",
                    style = TextStyle(
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        drawStyle = Stroke(width = 2f)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Person's Name
                Text(
                    text = "$personName!",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )

                // Very thin underline spanning just the text
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.6f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(0.55f) // Approximating width of the name
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Birthday Message
                Text(
                    text = "Wishing you true success and healthy life on your ${age}th birthday. make this birthday more happy by donating on ananta platform\nDonate now and be a Superhero and bring a new life to others.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.95f),
                    textAlign = TextAlign.Start,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(40.dp))

            // ==================== Share Button ====================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShareClick() }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Using standard compose share icon as an approximation of the image's specific node share icon
                Canvas(modifier = Modifier.size(16.dp)) {
                    drawCircle(color = Color.White, radius = 2.dp.toPx(), center = Offset(2.dp.toPx(), size.height / 2))
                    drawCircle(color = Color.White, radius = 2.dp.toPx(), center = Offset(size.width - 2.dp.toPx(), 2.dp.toPx()))
                    drawCircle(color = Color.White, radius = 2.dp.toPx(), center = Offset(size.width - 2.dp.toPx(), size.height - 2.dp.toPx()))
                    drawLine(color = Color.White, start = Offset(4.dp.toPx(), size.height / 2), end = Offset(size.width - 4.dp.toPx(), 4.dp.toPx()), strokeWidth = 1.dp.toPx())
                    drawLine(color = Color.White, start = Offset(4.dp.toPx(), size.height / 2), end = Offset(size.width - 4.dp.toPx(), size.height - 4.dp.toPx()), strokeWidth = 1.dp.toPx())
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==================== Donate Button ====================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(52.dp)
                    .background(Color.White, shape = RoundedCornerShape(35.dp))
                    .clickable { onDonateClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Donate to Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = birthdayGradient
                    )
                )
            }
        }
    }
}

/**
 * Helper component to draw the four-pointed diamond sparkle
 */
@Composable
private fun SparkleIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            quadraticBezierTo(size.width / 2f, size.height / 2f, size.width, size.height / 2f)
            quadraticBezierTo(size.width / 2f, size.height / 2f, size.width / 2f, size.height)
            quadraticBezierTo(size.width / 2f, size.height / 2f, 0f, size.height / 2f)
            quadraticBezierTo(size.width / 2f, size.height / 2f, size.width / 2f, 0f)
        }
        drawPath(path = path, color = Color.White)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BirthdayCardScreenPreview() {
    BirthdayCardScreen()
}
