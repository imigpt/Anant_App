package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.LiveLocationMapViewModel

@Composable
fun LiveLocationMapScreen(
    onBackClick: () -> Unit = {},
    onNavigateClick: () -> Unit = {},
    onShowMyLocationClick: () -> Unit = {},
    onShowAllMembersClick: () -> Unit = {},
    locationViewModel: LiveLocationMapViewModel = viewModel()
) {
    val uiState by locationViewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Blue Bubble Background Image
        Image(
            painter = painterResource(id = R.drawable.blue_background_with_bubbles),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Main Frosted Glass Card taking up most of the screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.6f))
                .border(
                    width = 1.dp,
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
                    imageVector = Icons.Default.LocationOn, // Or your custom key/pin icon
                    contentDescription = "Location Pin",
                    modifier = Modifier.size(72.dp),
                    tint = Color.Black
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
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = Color.Black.copy(alpha = 0.05f),
                            spotColor = Color.Black.copy(alpha = 0.05f)
                        )
                        .background(
                            color = Color.White,
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
                            // Person Icon from drawable
                            Image(
                                painter = painterResource(id = R.drawable.person_icon),
                                contentDescription = "Member Avatar",
                                modifier = Modifier.size(64.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            // Member Details
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = "Name Of Family Member",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = uiState.selectedMember.name, // Will be "Kamla" from state
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Current Address",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                                Text(
                                    text = uiState.selectedMember.currentAddress,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
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
                            // Green Navigate Button
                            Button(
                                onClick = {
                                    locationViewModel.navigateToLocation()
                                    onNavigateClick()
                                },
                                modifier = Modifier
                                    .height(44.dp)
                                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF5FFB5F) // Bright Green matching the image
                                ),
                                shape = RoundedCornerShape(16.dp),
                                enabled = !uiState.isLoading
                            ) {
                                Text(
                                    text = "Navigate",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "Navigate Pin",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.Black
                                )
                            }

                            // Last seen
                            Text(
                                text = "Last seen: ${uiState.selectedMember.lastSeen}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
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
                        .height(56.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp)),
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
                        color = Color.Black
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
                        .height(56.dp)
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp)),
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
                        color = Color.Black
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

@Preview(showBackground = true)
@Composable
fun PreviewLiveLocationMapScreen() {
    LiveLocationMapScreen()
}
