package com.example.anantapp.ui.onboarding

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R
import com.example.anantapp.ui.theme.AnantAppTheme
import kotlinx.coroutines.launch

// Updated Data Class to include the dynamic background image
data class OnboardingPageData(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit = {}
) {
    // The text content and specific images matching the flow
    val onboardingPages = listOf(
        OnboardingPageData(
            title = "For Every Government\nHand that Helps Others.",
            description = "We built Anant to protect your family, your savings, and your service every step of the way.",
            imageRes = R.drawable.onboarding_bg_1
        ),
        OnboardingPageData(
            title = "We help you connect to the\ngovernment without any\nhurdle.",
            description = "Your data is encrypted and protected with industry-leading standards.",
            imageRes = R.drawable.onboarding_bg_2
        ),
       OnboardingPageData(
           title = "Get Started with\nAnant Today",
           description = "Create your account and experience seamless service at your fingertips.",
           imageRes = R.drawable.onboarding_bg_3
        )
    )

    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // Swipable Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true
        ) { page ->
            OnboardingPage(
                pageData = onboardingPages[page],
                currentPage = page,
                totalPages = onboardingPages.size,
                onNextClick = {
                    when {
                        page < onboardingPages.size - 1 -> {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page + 1)
                            }
                        }
                        else -> onOnboardingComplete()
                    }
                },
                onPrevClick = {
                    if (page > 0) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page - 1)
                        }
                    }
                }
            )
        }
    }
}

/**
 * Individual Page with its own Background Image and the Glass Card overlay
 */
@Composable
private fun OnboardingPage(
    pageData: OnboardingPageData,
    currentPage: Int,
    totalPages: Int,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Background - either image or solid color
        if (pageData.imageRes != 0) {
            Image(
                painter = painterResource(id = pageData.imageRes),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            // Solid color background when no image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5)) // Light gray background
            )
        }

        // 2. Frosted Glass Card positioned at the bottom of the screen
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 80.dp) 
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                // Translucent gradient for glass effect
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.2f)
                        )
                    )
                )
                // White border outline for the glass edge
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp) // Internal padding of the card
            ) {
                // Title (Left Aligned)
                Text(
                    text = pageData.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Faded Divider Line
                HorizontalDivider(
                    color = Color.Black.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Description (Left Aligned)
                Text(
                    text = pageData.description,
                    fontSize = 15.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Bottom Row for the floating Arrow Button aligned to the right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Back Button (shown on pages 1 and 2)
                    if (currentPage > 0) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .clickable { onPrevClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        // Invisible spacer to balance the layout
                        Box(modifier = Modifier.size(56.dp))
                    }

                    // Next Button (always visible)
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onNextClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    AnantAppTheme {
        OnboardingScreen()
    }
}
