package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.presentation.viewmodel.HomeScreenViewModel
import com.example.anantapp.ui.components.BottomNavigationBar

@Composable
fun HomeScreen(
    onDonorClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAnalyticsClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    viewModel: HomeScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Section with Curved Gradient and Circles
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                // Curved Gradient Background
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            GenericShape { size, _ ->
                                with(density) {
                                    moveTo(0f, 0f)
                                    lineTo(size.width, 0f)
                                    lineTo(size.width, size.height - 60.dp.toPx())
                                    quadraticTo(
                                        size.width / 2f, size.height + 40.dp.toPx(),
                                        0f, size.height - 60.dp.toPx()
                                    )
                                    close()
                                }
                            }
                        )
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFF6A00), // Orange
                                    Color(0xFFFF007A), // Pink
                                    Color(0xFF7000FF)  // Purple
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                            )
                        )
                )

                // Background Decorative Circles
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Top Center Concentric Circles
                    val topCenter = Offset(size.width / 2, -20.dp.toPx())
                    drawCircle(
                        color = Color.White,
                        radius = 90.dp.toPx(),
                        center = topCenter,
                        style = Stroke(width = 16.dp.toPx())
                    )
                    drawCircle(
                        color = Color(0xFF4A00E0), // Deep blue/purple
                        radius = 74.dp.toPx(),
                        center = topCenter,
                        style = Stroke(width = 24.dp.toPx())
                    )
                    drawCircle(
                        color = Color(0xFFFF007A), // Pink
                        radius = 50.dp.toPx(),
                        center = topCenter
                    )

                    // Left Faint Orange Circle
                    drawCircle(
                        color = Color(0xFFFF8C00).copy(alpha = 0.4f),
                        radius = 60.dp.toPx(),
                        center = Offset(-10.dp.toPx(), 80.dp.toPx())
                    )

                    // Bottom Right Faint Purple Circle
                    drawCircle(
                        color = Color(0xFF9D00FF).copy(alpha = 0.3f),
                        radius = 50.dp.toPx(),
                        center = Offset(size.width + 10.dp.toPx(), size.height - 30.dp.toPx())
                    )
                }

                // Header Content (Text and Profile Image)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp, start = 24.dp, end = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = buildAnnotatedString {
                                append("Hello ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Mahendra,")
                                }
                            },
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("Wallet balance : ")
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append("₹10,456.05")
                                }
                            },
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    // Profile Picture Placeholder
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color.White, CircleShape)
                    )
                }
            }

            // Action Card (Overlapping the gradient curve)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-150).dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(40.dp)),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionItem(icon = Icons.Default.SyncAlt, label = "Transfer")
                    DividerLine()
                    ActionItem(icon = Icons.Default.CreditCard, label = "Add or Withdraw")
                    DividerLine()
                    ActionItem(icon = Icons.Default.History, label = "History", onClick = onHistoryClick)
                }
            }

            // Quick Actions Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-60).dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionItem(
                    icon = Icons.Default.Bolt,
                    label = "My Donations",
                    onClick = onDonorClick
                )
                QuickActionItem(icon = Icons.Default.ShoppingBag, label = "Nomin Wallet")
                QuickActionItem(icon = Icons.Default.Receipt, label = "Auto-Debit")
                QuickActionItem(icon = Icons.Default.Apps, label = "Withdraw Rules")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recommendations Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recommendation for you",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "See More",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF6200EA) // Purple
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Recommendation Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Black Friday Deal Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF003D33)) // Dark Green
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "30% OFF",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Black Friday deal",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        // Decorative light green curve
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = 20.dp, y = 20.dp)
                                .size(80.dp)
                                .background(Color(0xFF4CAF50), RoundedCornerShape(40.dp))
                        )
                    }
                }

                // Special Today Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD1A4)) // Light Peach/Orange
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column {
                            Text(
                                text = "Special",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = "Today's",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pagination Dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.size(8.dp).background(Color(0xFF7000FF), CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(8.dp).background(Color(0xFFB39DDB), CircleShape))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(8.dp).background(Color(0xFFB39DDB), CircleShape))
            }

            Spacer(modifier = Modifier.height(50.dp))
        }

        // Bottom Navigation Bar
        BottomNavigationBar(
            selectedItem = uiState.selectedNavItem,
            onHomeClick = {
                onHomeClick()
                viewModel.onNavigationItemClick("home")
            },
            onAnalyticsClick = {
                onAnalyticsClick()
                viewModel.onNavigationItemClick("analytics")
            },
            onNotificationClick = {
                onNotificationClick()
                viewModel.onNavigationItemClick("alerts")
            },
            onProfileClick = {
                onProfileClick()
                viewModel.onNavigationItemClick("profile")
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun ActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Black,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun DividerLine() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Color.LightGray)
    )
}

@Composable
fun QuickActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(70.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .border(1.dp, Color(0xFF6200EA), CircleShape) // Purple border
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF6200EA),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
