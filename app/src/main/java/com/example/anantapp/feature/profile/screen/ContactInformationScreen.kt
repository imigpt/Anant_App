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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.profile.viewmodel.ContactInformationViewModel

@Composable
fun ContactInformationScreen(
    userName: String = "Mahendra",
    anantId: String = "#9121038605",
    onBackClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {},
    viewModel: ContactInformationViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    
    // Light pink header gradient
    val pinkGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFFFD4E5),
            Color(0xFFFFC0D9)
        )
    )
    
    // Purple-pink gradient for update button
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
        // Pink Curved Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = pinkGradient)
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
                        tint = Color.Black,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { onBackClick() }
                    )
                    
                    Text(
                        text = "Contact Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.Black,
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
                                color = Color.Black
                            )
                            Text(
                                text = userName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "ID: $anantId",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                    
                    // Profile Picture Placeholder
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color(0xFFFF5B91).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFFFF5B91)
                        )
                    }
                }
            }
        }
        
        // Pink Section Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF5B91))
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = "CONTACT INFORMATION",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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
                label = "Blood Group",
                value = uiState.bloodGroup,
                onValueChange = { viewModel.updateBloodGroup(it) }
            )
            
            CustomFormField(
                label = "Email",
                value = uiState.email,
                onValueChange = { viewModel.updateEmail(it) }
            )
            
            CustomFormField(
                label = "Category",
                value = uiState.category,
                onValueChange = { viewModel.updateCategory(it) }
            )
            
            CustomFormField(
                label = "Address",
                value = uiState.address,
                onValueChange = { viewModel.updateAddress(it) }
            )
            
            CustomFormField(
                label = "Aadhar Card",
                value = uiState.aadharCard,
                onValueChange = { viewModel.updateAadharCard(it) }
            )
            
            CustomFormField(
                label = "PAN Card",
                value = uiState.panCard,
                onValueChange = { viewModel.updatePanCard(it) }
            )
            
            CustomFormField(
                label = "Gender",
                value = uiState.gender,
                onValueChange = { viewModel.updateGender(it) }
            )
            
            CustomFormField(
                label = "Age",
                value = uiState.age,
                onValueChange = { viewModel.updateAge(it) }
            )
            
            CustomPhoneField(
                label = "Phone No.",
                value = uiState.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) }
            )
            
            CustomPhoneField(
                label = "Alternate Phone No.",
                value = uiState.alternatePhoneNumber,
                onValueChange = { viewModel.updateAlternatePhoneNumber(it) }
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
                    viewModel.updateContactInformation()
                    onUpdateClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Update",
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
                .border(width = 1.dp, color = Color(0xFFFF5B91), shape = RoundedCornerShape(8.dp))
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

@Composable
private fun CustomPhoneField(
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
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .border(width = 1.dp, color = Color(0xFFFF5B91), shape = RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indian flag placeholder
            Column(
                modifier = Modifier
                    .width(20.dp)
                    .height(12.dp)
                    .background(Color(0xFFF57C00))
            ) {}
            
            Text(
                text = " +91 ",
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
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
fun ContactInformationScreenPreview() {
    ContactInformationScreen()
}
