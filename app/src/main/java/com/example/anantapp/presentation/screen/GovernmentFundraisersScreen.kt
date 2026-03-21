package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GovernmentFundraisersScreen(
    onBackClick: () -> Unit,
    onFinish: () -> Unit = {}
) {
    val fundraisers = listOf(
        FundraiserItem(
            title = "PM CARES FUNDS",
            description = "Contribute to the Prime Minister's...",
            amount = "₹ 2,50,000.00",
            isVerified = true
        ),
        FundraiserItem(
            title = "NDRF Relief Fund",
            description = "Support disaster response and...",
            amount = "₹ 2,50,000.00",
            isVerified = true
        ),
        FundraiserItem(
            title = "Beti Bachao, Beti Padhao",
            description = "Donate for the empowerment of...",
            amount = "₹ 2,50,000.00",
            isVerified = false
        ),
        FundraiserItem(
            title = "Clean India Initiative",
            description = "Help in creating a cleaner and gr...",
            amount = "₹ 2,50,000.00",
            isVerified = false
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Decorative orange circles in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top-left orange circle
            drawCircle(
                color = Color(0xFFFF8C00),
                center = Offset(-100f, 250f),
                radius = 350f
            )

            // Bottom-right orange circle
            drawCircle(
                color = Color(0xFFFF8C00),
                center = Offset(size.width, size.height),
                radius = 300f
            )
        }

        // Main White Card Container
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Header Image Block
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF81C784), // Light Greenish to simulate nature bg
                                    Color(0xFF8D6E63)  // Brownish to simulate hands/jar
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Back Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, CircleShape)
                                .clickable { onBackClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Header Content Bottom Alignment
                        Column {
                            Text(
                                text = "Government Fundraisers",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            HeaderCheckmarkText(text = "Anyone can donate")
                            Spacer(modifier = Modifier.height(4.dp))
                            HeaderCheckmarkText(text = "Full transparency of funds")
                        }
                    }
                }

                // Fundraisers List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    fundraisers.forEachIndexed { index, fundraiser ->
                        FundraiserCard(fundraiser = fundraiser)

                        // Divider except for the last item
                        if (index < fundraisers.size - 1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF9C27B0).copy(alpha = 0.5f),
                                                Color(0xFFE91E63).copy(alpha = 0.5f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun HeaderCheckmarkText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(12.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FundraiserCard(fundraiser: FundraiserItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click */ }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder Image with Gradient Border
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF9C27B0),
                            Color(0xFFE91E63)
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
        )

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            // Title with Verified Badge (FIXED WIDTH ISSUES HERE)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fundraiser.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f) // Takes remaining space to avoid squishing badge
                )

                if (fundraiser.isVerified) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .shadow(2.dp, RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFFF9500), Color(0xFFFFC107))
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Verified",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,          // Forces text to stay on a single line
                            softWrap = false       // Disables text wrapping entirely
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = fundraiser.description,
                fontSize = 12.sp,
                color = Color(0xFF666666),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Amount Raised
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    ) {
                        append(fundraiser.amount)
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFE91E63)
                        )
                    ) {
                        append("Raised")
                    }
                }
            )
        }
    }
}

data class FundraiserItem(
    val title: String,
    val description: String,
    val amount: String,
    val isVerified: Boolean
)