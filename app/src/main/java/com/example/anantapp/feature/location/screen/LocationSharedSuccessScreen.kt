package com.example.anantapp.feature.location.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.location.viewmodel.LocationSharedSuccessViewModel

@Composable
fun LocationSharedSuccessScreen(
    onBackClick: () -> Unit = {},
    onManageAccessClick: () -> Unit = {},
    onViewOnMapClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    viewModel: LocationSharedSuccessViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    
    // Blue gradient background
    val blueGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6B9ECE),
            Color(0xFF3F51B5)
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = blueGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Frosted Glass Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        color = Color.White.copy(alpha = 0.95f)
                    )
                    .border(
                        width = 1.5.dp,
                        color = Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(32.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    // Checkmark Icon in Circle
                    Canvas(
                        modifier = Modifier.size(80.dp)
                    ) {
                        // Draw circle
                        drawCircle(
                            color = Color(0xFF4CAF50),
                            radius = 40.dp.toPx(),
                            center = Offset(this.size.width / 2, this.size.height / 2)
                        )
                        
                        // Draw checkmark
                        val startX = this.size.width * 0.35f
                        val midX = this.size.width * 0.45f
                        val midY = this.size.height * 0.6f
                        val endX = this.size.width * 0.7f
                        val startY = this.size.height * 0.4f
                        val endY = this.size.height * 0.3f
                        
                        drawLine(
                            color = Color.White,
                            start = Offset(startX, startY),
                            end = Offset(midX, midY),
                            strokeWidth = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        
                        drawLine(
                            color = Color.White,
                            start = Offset(midX, midY),
                            end = Offset(endX, endY),
                            strokeWidth = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Title
                    Text(
                        text = "Location Shared\nSuccessfully",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Description
                    Text(
                        text = "Your real-time location is now visible to:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Family Members Grid (2x2)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // First row: My Son and My Wife
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SharedMemberButton(
                                name = "My Son",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(47.dp)
                            )
                            SharedMemberButton(
                                name = "My Wife",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(47.dp)
                            )
                        }
                        
                        // Second row: My Mother and My Brother
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SharedMemberButton(
                                name = "My Mother",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(47.dp)
                            )
                            SharedMemberButton(
                                name = "My Brother",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(47.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Info Text
                    Text(
                        text = "You can stop sharing anytime from the settings.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Manage Access Button
                    Button(
                        onClick = {
                            viewModel.manageAccess()
                            onManageAccessClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.8f)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = "Manage Access",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF6B9ECE)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // View On Map and Done Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.viewOnMap()
                                onViewOnMapClick()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.8f)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            enabled = !uiState.isLoading
                        ) {
                            Text(
                                text = "View on Map",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF6B9ECE)
                            )
                        }
                        
                        Button(
                            onClick = {
                                viewModel.done()
                                onDoneClick()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .border(
                                    width = 2.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF6B9ECE),
                                            Color(0xFF3F51B5)
                                        )
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE8F0FF)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            enabled = !uiState.isLoading
                        ) {
                            Text(
                                text = "Done",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF6B9ECE)
                            )
                        }
                    }
                }
            }
            
            // Error/Success Message
            AnimatedVisibility(
                visible = uiState.errorMessage != null || uiState.successMessage != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .background(
                            color = if (uiState.errorMessage != null) Color(0xFFFF6B6B) else Color(0xFF4CAF50),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage ?: uiState.successMessage ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Footer with Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() },
                    tint = Color.White
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Go Back",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onBackClick() }
                )
            }
        }
    }
}

@Composable
fun SharedMemberButton(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LocationSharedSuccessScreenPreview() {
    LocationSharedSuccessScreen()
}
