package com.example.anantapp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.LocationSharedSuccessViewModel
import com.example.anantapp.ui.components.customShadow

@Composable
fun LocationSharedSuccessScreen(
    onBackClick: () -> Unit = {},
    onManageAccessClick: () -> Unit = {},
    onViewOnMapClick: () -> Unit = {},
    onDoneClick: () -> Unit = {},
    viewModel: LocationSharedSuccessViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Blue Bubble Background Image
        Image(
            painter = painterResource(id = R.drawable.blue_background_with_bubbles),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

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
                    .customShadow(
                        color = Color.Black.copy(alpha = 0.25f),
                        borderRadius = 32.dp,
                        blurRadius = 4.dp,
                        offsetY = 4.dp,
                        offsetX = 0.dp,
                        spread = 0.dp
                    )
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        color = Color.White.copy(alpha = 0.45f)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.6f),
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
                            modifier = Modifier.size(70.dp),
                            onDraw = {
                                // Draw checkmark circle outline
                                drawCircle(
                                    color = Color.Black,
                                    radius = size.minDimension / 2,
                                    style = Stroke(width = 0.3.dp.toPx(), cap = StrokeCap.Butt)
                                )

                                // Draw checkmark
                                val centerX = size.width / 2
                                val centerY = size.height / 2
                                val checkWidth = size.width * 0.6f
                                val checkHeight = size.height * 0.5f

                                // Left part of checkmark
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(centerX - checkWidth / 3, centerY + checkHeight / 4),
                                    end = Offset(centerX - checkWidth / 6, centerY + checkHeight / 2),
                                    strokeWidth = 0.3.dp.toPx(),
                                    cap = StrokeCap.Butt
                                )

                                // Right part of checkmark
                                drawLine(
                                    color = Color.Black,
                                    start = Offset(centerX - checkWidth / 6, centerY + checkHeight / 2),
                                    end = Offset(centerX + checkWidth / 2.5f, centerY - checkHeight / 3),
                                    strokeWidth = 0.3.dp.toPx(),
                                    cap = StrokeCap.Butt
                                )
                            }
                        )

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
                                name = uiState.sharedWith.getOrNull(0)?.name ?: "My Son",
                                modifier = Modifier.weight(1f)
                            )

                            SharedMemberButton(
                                name = uiState.sharedWith.getOrNull(1)?.name ?: "My Wife",
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Second row: My Wife and My Mother
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SharedMemberButton(
                                name = uiState.sharedWith.getOrNull(2)?.name ?: "My Wife",
                                modifier = Modifier.weight(1f)
                            )

                            SharedMemberButton(
                                name = uiState.sharedWith.getOrNull(3)?.name ?: "My Mother",
                                modifier = Modifier.weight(1f)
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
                            .height(47.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = Color.Black.copy(alpha = 0.25f),
                                spotColor = Color.Black.copy(alpha = 0.25f)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.8f)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = "Manage Access",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
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
                                .height(47.dp)
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.25f),
                                    spotColor = Color.Black.copy(alpha = 0.25f)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.8f)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            enabled = !uiState.isLoading
                        ) {
                            Text(
                                text = "View On Map",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.finishSharing()
                                onDoneClick()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(47.dp)
                                .shadow(
                                    elevation = 10.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    ambientColor = Color.Black.copy(alpha = 0.25f),
                                    spotColor = Color.Black.copy(alpha = 0.25f)
                                ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.8f)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            enabled = !uiState.isLoading
                        ) {
                            Text(
                                text = "Done",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
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

@Composable
fun SharedMemberButton(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(47.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Black.copy(alpha = 0.25f),
                spotColor = Color.Black.copy(alpha = 0.25f)
            )
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
