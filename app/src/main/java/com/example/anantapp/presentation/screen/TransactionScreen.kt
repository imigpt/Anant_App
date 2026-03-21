package com.example.anantapp.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.TransactionViewModel

/**
 * Production-level Transaction Screen
 * Displays user's transaction history with search and filter options
 */
@Composable
fun TransactionScreen(
    onBackClick: () -> Unit = {},
    viewModel: TransactionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val gradientBlue = Color(0xFF8D14FF)
    val gradientPink = Color(0xFFFF1E4F)
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
        // Gradient Header (Consistent with DonorScreen and DonationHistoryScreen)
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
                    text = "Transaction",
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
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = Color(0xFF8D14FF),
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedContainerColor = Color(0xFFF5F6F8),
                            focusedContainerColor = Color(0xFFF5F6F8)
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFFCCCCCC),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )

                    // Filter Button with Gradient background
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(brush = gradientBrush),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { /* Handle filter */ }) {
                            Icon(
                                imageVector = Icons.Filled.Tune,
                                contentDescription = "Filter",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Group transactions by month
                val groupedTransactions = uiState.transactions.groupBy { transaction ->
                    val parts = transaction.date.split(" ")
                    if (parts.size >= 3) {
                        "${parts[1]} ${parts[2]}"
                    } else {
                        "Unknown"
                    }
                }

                // Get current month and other months
                val currentMonth = "Apr 2025"
                val otherMonths = groupedTransactions.keys.filter { it != currentMonth }.distinct()

                // "This month" label
                Text(
                    text = "This month",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF999999),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Display current month transactions
                groupedTransactions[currentMonth]?.forEach { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        gradientBrush = gradientBrush
                    )
                }

                // Display other months
                otherMonths.forEach { month ->
                    Text(
                        text = month,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF999999),
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )

                    groupedTransactions[month]?.forEach { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            gradientBrush = gradientBrush
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: com.example.anantapp.presentation.viewmodel.Transaction,
    gradientBrush: Brush,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(brush = gradientBrush, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (transaction.isCredit) "+" else "-",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Transaction details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.type,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = transaction.date,
                fontSize = 12.sp,
                color = Color(0xFF888888)
            )
        }

        // Amount
        Text(
            text = transaction.amount,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.isCredit) Color(0xFF4CAF50) else Color(0xFFE53935)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    TransactionScreen()
}
