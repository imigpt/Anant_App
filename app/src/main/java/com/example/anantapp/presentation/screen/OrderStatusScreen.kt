package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.OrderStatusViewModel

@Composable
fun OrderStatusScreen(
    viewModel: OrderStatusViewModel = viewModel(),
    onTrackOnMapClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {},
    onViewQRCodeClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    val formState by viewModel.formState.collectAsState()
    OrderStatusContent(
        formState = formState,
        onTrackOnMapClick = onTrackOnMapClick,
        onDownloadQRClick = onDownloadQRClick,
        onViewQRCodeClick = onViewQRCodeClick,
        onHomeClick = onHomeClick
    )
}

@Composable
fun OrderStatusContent(
    formState: com.example.anantapp.data.model.OrderStatusFormState,
    onTrackOnMapClick: () -> Unit = {},
    onDownloadQRClick: () -> Unit = {},
    onViewQRCodeClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    // Default to true as seen in the image
    val showMapView = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Glowing background effects
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = 60.dp)
                .size(180.dp)
                .background(Color(0xFF277BEE), CircleShape)
                .blur(30.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = (-20).dp)
                .size(180.dp)
                .background(Color(0xFF1E6FD9), CircleShape)
                .blur(30.dp)
        )

        // Main Glassmorphism Card
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp)
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
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.75f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Content area (Scrollable)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code Image
                    Image(
                        painter = painterResource(id = R.drawable.qr_code),
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your QR Order Status",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Order Details Section
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OrderDetailRow(Icons.Outlined.LocalShipping, "Order ID: ", "#123456")
                        OrderDetailRow(Icons.Outlined.CheckCircle, "Status : ", "Packed")
                        OrderDetailRow(Icons.Outlined.HourglassEmpty, "Expected Delivery: ", "8 July 2025")
                        OrderDetailRow(Icons.Outlined.ShoppingCart, "Order Date: ", "5 July 2025")
                        OrderDetailRow(Icons.Outlined.CurrencyRupee, "Payment: ", "Paid")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Action Buttons (Track on Map / Download QR)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            onClick = {
                                showMapView.value = !showMapView.value
                                onTrackOnMapClick()
                            },
                            shape = RoundedCornerShape(50.dp),
                            color = Color(0xFF3B82F6),
                            shadowElevation = 4.dp,
                            modifier = Modifier.weight(1f).height(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("Track on Map", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Surface(
                            onClick = onDownloadQRClick,
                            shape = RoundedCornerShape(50.dp),
                            color = Color.White,
                            shadowElevation = 2.dp,
                            modifier = Modifier.weight(1f).height(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("Download QR", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Map Section
                    if (showMapView.value) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .shadow(4.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = painterResource(id = R.drawable.map_img),
                                    contentDescription = "Map Location",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )

                                // Floating Label in Map
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(12.dp),
                                    shape = RoundedCornerShape(50.dp),
                                    color = Color.White.copy(alpha = 0.95f),
                                    shadowElevation = 2.dp
                                ) {
                                    Text(
                                        text = "Lucknow Uttar Pradesh",
                                        fontSize = 11.sp,
                                        color = Color(0xFF333333),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Support Text
                    Text(
                        text = "Need help? support@example.com",
                        fontSize = 11.sp,
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Fixed Bottom Buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // View QR Code Button - White/Grey Gradient Style
                    val viewQRGradient = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFEAEAEA))
                    )
                    Button(
                        onClick = onViewQRCodeClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .shadow(elevation = 2.dp, shape = RoundedCornerShape(50.dp))
                            .background(brush = viewQRGradient, shape = RoundedCornerShape(50.dp)),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("View QR Code", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF3B82F6))
                    }

                    // Home Button - Blue Gradient Style
                    val homeGradient = Brush.verticalGradient(
                        colors = listOf(Color(0xFF006FEE), Color(0xFF7DBAFF))
                    )
                    Button(
                        onClick = onHomeClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(50.dp))
                            .background(brush = homeGradient, shape = RoundedCornerShape(50.dp)),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Home", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderDetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Black)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOrderStatusScreen() {
    OrderStatusScreen()
}
