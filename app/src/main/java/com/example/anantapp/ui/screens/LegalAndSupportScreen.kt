package com.example.anantapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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

@Composable
fun LegalAndSupportScreen(
    onReadFullTermsClick: () -> Unit = {},
    onViewPrivacyPolicyClick: () -> Unit = {},
    onContactSupportClick: () -> Unit = {},
    onBrowseFAQsClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAcceptTermsClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.legal_screen_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Main content card
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 80.dp, start = 16.dp, end = 16.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color(0xFF000000).copy(alpha = 0.15f),
                    spotColor = Color(0xFF000000).copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFB8E0F6).copy(alpha = 0.8f),
                            Color(0xFF87CEEB).copy(alpha = 0.9f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        ) {
            // Main layout inside the card
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                // Scrollable Content Area (Takes up remaining space)
                Column(
                    modifier = Modifier
                        .weight(1f) // Fills available space above the buttons
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Support Icon
                    Image(
                        painter = painterResource(id = R.drawable.user_profile_group),
                        contentDescription = "Support Icon",
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title
                    Text(
                        text = "Legal & Support",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1a1a1a),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section A: Terms & Conditions
                    SupportCard(
                        letter = "A",
                        title = "Terms & Conditions",
                        description = "Read the complete terms of use before using the app.",
                        actionText = "Read Full Terms",
                        onActionClick = onReadFullTermsClick
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Section B: Privacy Policy
                    SupportCard(
                        letter = "B",
                        title = "Privacy Policy",
                        description = "Know how your data is collected, stored & protected.",
                        actionText = "View Privacy Policy",
                        onActionClick = onViewPrivacyPolicyClick
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Section C: Contact / Support
                    SupportCard(
                        letter = "C",
                        title = "Contact / Support",
                        description = "Facing issues? We're here to help 24x7",
                        actionText = "Contact Support",
                        onActionClick = onContactSupportClick
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Section D: FAQs
                    SupportCard(
                        letter = "D",
                        title = "FAQs",
                        description = "Get answers to common questions instantly",
                        actionText = "Browse FAQs",
                        onActionClick = onBrowseFAQsClick
                    )

                    // Add padding to bottom of scroll so items don't hug the buttons perfectly
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Fixed Bottom Buttons Row (No longer inside the scrollable view)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Home Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.2f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White.copy(alpha = 0.4f))
                            .border(
                                width = 2.dp,
                                color = Color(0xFF4A7BA7),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable { onHomeClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Home",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1a1a1a),
                        )
                    }

                    // We removed the Spacer here because spacedBy(12.dp) handles it!

                    // Accept Terms Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.2f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White.copy(alpha = 0.8f))
                            .clickable { onAcceptTermsClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Accept Terms",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black.copy(alpha = 0.8f) // Fixed Color conversion
                        )
                    }
                }
            }
        }

        // Footer Text
        Text(
            text = "Your trust matters. All communications\nare secure & monitored for quality.",
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

@Composable
fun SupportCard(
    letter: String,
    title: String,
    description: String,
    actionText: String,
    onActionClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Letter and Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = letter,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1a1a1a),
                    )
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1a1a1a)
                    )
                }
            }

            // Description
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                lineHeight = 18.sp
            )

            // Action Link
            Text(
                text = actionText,
                fontSize = 12.sp,
                color = Color(0xFF1a1a1a),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { onActionClick() }
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LegalAndSupportScreenPreview() {
    LegalAndSupportScreen()
}