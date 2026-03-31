package com.example.anantapp.feature.profile.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.profile.viewmodel.FamilyInformationViewModel

@Composable
fun FamilyInformationScreen(
    userName: String = "Mahendra",
    anantId: String = "#9121038605",
    onBackClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    viewModel: FamilyInformationViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    
    // Orange gradient for header
    val orangeGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFF8C00),
            Color(0xFFFFB347)
        )
    )
    
    // Purple-pink gradient for button
    val purplePinkGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF9500FF),
            Color(0xFFFF1493)
        )
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Orange Curved Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = orangeGradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onBackClick() }
                    )
                    
                    Text(
                        text = "Family Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // User Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Hello ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                            Text(
                                text = userName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "ID: $anantId",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                    
                    // Profile Picture Placeholder
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color.White.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
        
        // Section Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = purplePinkGradient
                )
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Update Family Information",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // Form Fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomFormField(
                label = "Family Head Name",
                value = uiState.familyHeadName,
                onValueChange = { viewModel.updateFamilyHeadName(it) }
            )
            
            CustomFormField(
                label = "Spouse Name",
                value = uiState.spouseName,
                onValueChange = { viewModel.updateSpouseName(it) }
            )
            
            CustomFormField(
                label = "Spouse Age",
                value = uiState.spouseAge,
                onValueChange = { viewModel.updateSpouseAge(it) }
            )
            
            CustomFormField(
                label = "Nominee 1",
                value = uiState.nominee1,
                onValueChange = { viewModel.updateNominee1(it) }
            )
            
            CustomFormField(
                label = "Nominee 2",
                value = uiState.nominee2,
                onValueChange = { viewModel.updateNominee2(it) }
            )
            
            CustomFormField(
                label = "Marital Status",
                value = uiState.maritalStatus,
                onValueChange = { viewModel.updateMaritalStatus(it) }
            )
            
            CustomFormField(
                label = "Family Insurance Status",
                value = uiState.familyInsuranceStatus,
                onValueChange = { viewModel.updateFamilyInsuranceStatus(it) }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Update Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp)
                .background(
                    brush = purplePinkGradient,
                    shape = RoundedCornerShape(26.dp)
                )
                .clickable {
                    viewModel.updateFamilyInformation()
                    onUpdateClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Request to Update Profile",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun CustomFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .border(width = 1.dp, color = Color(0xFFFF1493), shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FamilyInformationScreenPreview() {
    FamilyInformationScreen()
}
