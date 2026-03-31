package com.example.anantapp.feature.profile.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.profile.viewmodel.BirthdayCardViewModel

@Composable
fun BirthdayCardScreen(
    personName: String = "Mahendra",
    age: Int = 24,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDonateClick: () -> Unit = {},
    viewModel: BirthdayCardViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()
    
    // Purple-pink gradient background
    val birthdayGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8D14FF),
            Color(0xFFFF1E4F)
        )
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = birthdayGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
        ) {
            // Header with Back Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() }
                )
                
                Text(
                    text = "Birthday",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Profile Photo with Sparkles
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.size(60.dp),
                        tint = Color.White
                    )
                }
                
                // Sparkle decorations
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 40.dp, top = 20.dp)
                        .size(20.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                )
                
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 20.dp, bottom = 20.dp)
                        .size(16.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Birthday Greeting
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // "Happy Birthday" text
                Text(
                    text = "Happy Birthday",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Person's Name
                Text(
                    text = "${uiState.personName}!",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                // Underline
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.6f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(150.dp)
                        .padding(vertical = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Birthday Message
                Text(
                    text = "Wishing you true success and healthy life on your ${uiState.age}th birthday. Make this birthday more happy by donating on ananta platform.\n\nDonate now and be a Superhero and bring a new life to others.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.95f),
                    textAlign = TextAlign.Start,
                    lineHeight = 22.sp
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(40.dp))
            
            // Share Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.shareGreeting()
                        onShareClick()
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.White, CircleShape)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Share",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Donate Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(52.dp)
                    .background(Color.White, shape = RoundedCornerShape(26.dp))
                    .clickable {
                        viewModel.donate(100) // Default donation amount
                        onDonateClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Donate to Community",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8D14FF)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BirthdayCardScreenPreview() {
    BirthdayCardScreen()
}
