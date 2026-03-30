package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.DonationHistoryViewModel

/**
 * Production-level Donation History Screen
 * Displays user's donation history with search and filter options
 */
@Composable
fun DonationHistoryScreen(
    onBackClick: () -> Unit = {},
    viewModel: DonationHistoryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val gradientBlue = Color(0xFF9500FF)
    val gradientPink = Color(0xFFFF6264)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(gradientBlue, gradientPink),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Gradient Header (Fixed height like DonorScreen)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(brush = gradientBrush)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = "Donation History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Overlapping White Content Area with Rounded Corners
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp) // Starts before the gradient ends
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                // Search and Filter Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = { viewModel.updateSearchQuery(it) },
                        placeholder = {
                            Text(
                                text = "Search transaction",
                                fontSize = 14.sp,
                                color = Color(0xFFCCCCCC)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF9500FF),
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color(0xFFF5F6F8),
                            focusedContainerColor = Color(0xFFF5F6F8)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search, // Changed to search icon
                                contentDescription = "Search",
                                tint = Color(0xFFCCCCCC),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )

                    // Filter Button with Gradient background like DonorScreen buttons

                        IconButton(onClick = { /* Handle filter */ }) {
                            Icon(
                                imageVector = Icons.Filled.Tune,
                                contentDescription = "Filter",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                }

                // "This month" label
                Text(
                    text = "This month",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF999999),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Donation items
                uiState.donations.forEach { donation ->
                    DonationHistoryCard(
                        donation = donation,
                        onDownloadReceipt = { viewModel.downloadReceipt(donation.id) },
                        gradientBrush = gradientBrush
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun DonationHistoryCard(
    donation: com.example.anantapp.presentation.viewmodel.DonationRecord,
    onDownloadReceipt: () -> Unit,
    gradientBrush: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.25f),
                spotColor = Color.Black.copy(alpha = 0.25f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "📅", fontSize = 18.sp)
                    Text(
                        text = "Date",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = donation.date,
                    fontSize = 13.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.End
                )
            }

            // Fundraiser Name row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "👤", fontSize = 18.sp)
                    Text(
                        text = "Fundraiser Name",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = donation.fundraiserName,
                    fontSize = 13.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                )
            }

            // Amount Donated row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "₹", fontSize = 18.sp)
                    Text(
                        text = "Amount Donated",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = donation.amount,
                    fontSize = 13.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.End
                )
            }

            // Status row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "✓", fontSize = 18.sp)
                    Text(
                        text = "Status",
                        fontSize = 13.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Text(
                    text = donation.status,
                    fontSize = 13.sp,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.End
                )
            }

            // Download Receipt Button
            Button(
                onClick = onDownloadReceipt,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(18.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush = gradientBrush, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Download Receipt",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DonationHistoryScreenPreview() {
    DonationHistoryScreen()
}
