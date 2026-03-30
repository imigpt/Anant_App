package com.example.anantapp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.ManageFamilyMembersViewModel
import com.example.anantapp.ui.components.customShadow

@Composable
fun ManageFamilyMembersScreen(
    onBackClick: () -> Unit = {},
    onAddMemberClick: () -> Unit = {},
    onEditMemberClick: (memberId: String, memberName: String) -> Unit = { _, _ -> },
    viewModel: ManageFamilyMembersViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.red_dark_background),
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
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Person Icon (formerly Sad Face)
                    Image(
                        painter = painterResource(id = R.drawable.person_icon),
                        contentDescription = "Person Icon",
                        modifier = Modifier
                            .size(56.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )

                    // Title
                    Text(
                        text = "Manage Family\nMembers",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(bottom = 28.dp)
                    )

                    // Family Members List
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        uiState.familyMembers.forEach { member ->
                            FamilyMemberListItem(
                                name = member.name,
                                onEditClick = {
                                    onEditMemberClick(member.id, member.name)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Add Another Family Member Button
                    Button(
                        onClick = onAddMemberClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .customShadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                borderRadius = 24.dp,
                                blurRadius = 4.dp,
                                offsetY = 4.dp,
                                offsetX = 0.dp,
                                spread = 0.dp
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.8f)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "Add Another Family Member",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
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
                    tint = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Go Back",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF222222),
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
fun FamilyMemberListItem(
    name: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .customShadow(
                color = Color.Black.copy(alpha = 0.25f),
                borderRadius = 16.dp,
                blurRadius = 4.dp,
                offsetY = 4.dp,
                offsetX = 0.dp,
                spread = 0.dp
            )
            .background(
                color = Color.White.copy(alpha = 0.7f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            modifier = Modifier
                .size(20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onEditClick() },
            tint = Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageFamilyMembersScreenPreview() {
    ManageFamilyMembersScreen()
}
