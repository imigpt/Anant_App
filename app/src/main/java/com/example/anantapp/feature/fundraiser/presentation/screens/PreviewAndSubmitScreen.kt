package com.example.anantapp.feature.fundraiser.presentation.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.PreviewAndSubmitViewModel

@Composable
fun PreviewAndSubmitScreen(
    viewModel: PreviewAndSubmitViewModel = viewModel(),
    fundraiserTitle: String = "Help Mohan fight Cancer",
    fundraiserStory: String = "Mohan needs urgent medical help",
    goalAmount: String = "₹ 1,00,000",
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onSubmitSuccess: (fundraiserId: String) -> Unit = {}
) {
    var showSuccessScreen by remember { mutableStateOf(false) }
    
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF2F4B)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    if (showSuccessScreen) {
        SuccessScreen(
            fundraiserId = "fundraiser_123",
            onHomeClick = { onSubmitSuccess("fundraiser_123") }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(mainGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(28.dp)
                            .clickable { onBackClick() }
                    )

                    Text(
                        text = "Preview & Submit",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Content Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(20.dp)
                ) {
                    // Title
                    Text(
                        text = "Fundraiser Summary",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Title Field
                    PreviewField(label = "Title", value = fundraiserTitle)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Story Field
                    PreviewField(label = "Story", value = fundraiserStory)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Goal Amount
                    PreviewField(label = "Goal Amount", value = goalAmount)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Please review your fundraiser details. Once submitted, you will not be able to edit certain information.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Spacer(modifier = Modifier.weight(1f))

                // Bottom Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Draft Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .border(1.5.dp, Color.White, CircleShape)
                            .clip(CircleShape)
                            .clickable { onDraftSaved() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save Draft",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Submit Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .background(Color.White, CircleShape)
                            .clickable { showSuccessScreen = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Submit",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9500FF)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun PreviewField(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Composable
fun SuccessScreen(
    fundraiserId: String,
    onHomeClick: () -> Unit
) {
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF2F4B)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Success Icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Fundraiser Created!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your fundraiser has been successfully created.\nID: $fundraiserId",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Go Home Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 32.dp)
                    .background(Color.White, CircleShape)
                    .clickable { onHomeClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Go to Home",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9500FF)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAndSubmitScreenPreview() {
    AnantAppTheme {
        PreviewAndSubmitScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    AnantAppTheme {
        SuccessScreen(fundraiserId = "12345", onHomeClick = {})
    }
}
