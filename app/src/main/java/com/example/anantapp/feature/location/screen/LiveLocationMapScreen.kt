package com.example.anantapp.feature.location.screen

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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.location.viewmodel.LiveLocationMapViewModel

@Composable
fun LiveLocationMapScreen(
    onBackClick: () -> Unit = {},
    onNavigateClick: () -> Unit = {},
    onShowMyLocationClick: () -> Unit = {},
    onShowAllMembersClick: () -> Unit = {},
    locationViewModel: LiveLocationMapViewModel = viewModel()
) {
    val uiState by locationViewModel.state.collectAsState()
    
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
        // Main Frosted Glass Card taking up most of the screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.95f))
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
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Huge Top Location Icon
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Pin",
                    modifier = Modifier.size(72.dp),
                    tint = Color(0xFF6B9ECE)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Title: Live Location Map
                Text(
                    text = "Live Location Map",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // White Family Member Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFFEEEEEE),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Top row of the card: Avatar and Details
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar placeholder
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(
                                        color = Color(0xFFE3F2FD),
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Member Avatar",
                                    modifier = Modifier.size(32.dp),
                                    tint = Color(0xFF6B9ECE)
                                )
                            }
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            // Member Details
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = uiState.familyMemberLocation.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = uiState.familyMemberLocation.relationship,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF999999)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = uiState.familyMemberLocation.location.address,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF666666)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(20.dp))
                        
                        // Bottom row of the card: Navigate Button & Last Seen
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Blue-Gradient Navigate Button
                            Button(
                                onClick = {
                                    locationViewModel.navigateToMember(uiState.familyMemberLocation.id)
                                    onNavigateClick()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFE8F0FF)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !uiState.isLoading
                            ) {
                                if (uiState.isLoading) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(16.dp),
                                        color = Color(0xFF6B9ECE),
                                        strokeWidth = 1.5.dp
                                    )
                                } else {
                                    Text(
                                        text = "Navigate",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF6B9ECE)
                                    )
                                }
                            }
                            
                            // Last seen
                            Text(
                                text = uiState.familyMemberLocation.lastSeenTime,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF999999),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Show My Location Button
                Button(
                    onClick = {
                        locationViewModel.showMyLocation()
                        onShowMyLocationClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    enabled = !uiState.isLoading
                ) {
                    Text(
                        text = "Show My Location",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6B9ECE)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Show All Family Members Button
                Button(
                    onClick = {
                        locationViewModel.showAllMembers()
                        onShowAllMembersClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    enabled = !uiState.isLoading
                ) {
                    Text(
                        text = "Show All Family Members",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6B9ECE)
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(40.dp))
                
                // Go Back Button (Bottom Left)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
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
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Go Back",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LiveLocationMapScreenPreview() {
    LiveLocationMapScreen()
}
