package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.EnableLocationViewModel
import kotlinx.coroutines.launch

@Composable
fun EnableLocationScreen(
    onSkip: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: EnableLocationViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            scope.launch { onSuccess() }
            viewModel.clearMessages()
        }
    }

    // Main background container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // Light gray background
    ) {
        // Decorative solid green circles in the background with linear gradient
        Canvas(modifier = Modifier.fillMaxSize()) {
            val blobGradient = Brush.linearGradient(
                colors = listOf(Color(0xFFBCFE37), Color(0xFF82B027)),
                start = Offset(0f, 0f),
                end = Offset(size.width, size.height)
            )

            // Top Right Blob
            drawCircle(
                brush = blobGradient,
                center = Offset(size.width - 50f, 150f),
                radius = 200f,
                alpha = 0.8f
            )
            // Bottom Left Blob
            drawCircle(
                brush = blobGradient,
                center = Offset(100f, size.height - 150f),
                radius = 150f,
                alpha = 0.8f
            )
        }

        // Main Card with Glassmorphism effect
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(32.dp)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f) // Semi-transparent for glassmorphism
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Skip Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { onSkip() }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Skip >>",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Location Icon
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Location Pin",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Enable Precise\nLocation",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = "This keeps your profile safe, ensures\nfaster help during emergencies.",
                    fontSize = 13.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Map Image Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.map_img),
                        contentDescription = "Map Preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Address Input Field with Gradient Outline
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(28.dp))
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF8A2BE2), // Purple
                                    Color(0xFFFF1493), // Deep Pink
                                    Color(0xFFFF8C00)  // Orange
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                ) {
                    OutlinedTextField(
                        value = state.address,
                        onValueChange = { viewModel.updateAddress(it) },
                        modifier = Modifier.fillMaxSize(),
                        placeholder = {
                            Text(
                                "Enter Address",
                                fontSize = 14.sp,
                                color = Color(0xFF888888)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(28.dp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Enable Location Button
                Button(
                    onClick = { 
                        viewModel.enableLocationServices()
                        onSuccess() // Navigate to FamilyDetailsScreen
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCF11) // Green
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Enable Location",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Privacy Footer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Privacy Lock",
                        tint = Color(0xFFB0B0B0),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your data stays private & encrypted.",
                        fontSize = 12.sp,
                        color = Color(0xFFB0B0B0),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EnableLocationScreenPreview() {
    EnableLocationScreen(onSkip = {}, onSuccess = {})
}
