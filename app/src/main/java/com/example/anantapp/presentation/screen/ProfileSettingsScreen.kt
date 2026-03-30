package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileSettingsScreen(
    userName: String = "Mahendra",
    anantId: String = "#9121038605",
    onBackClick: () -> Unit = {},
    onContactClick: () -> Unit = {},
    onFamilyClick: () -> Unit = {},
    onBankClick: () -> Unit = {},
    onInsuranceClick: () -> Unit = {},
    onMedicalClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val curveDepth = remember(density) { with(density) { 60.dp.toPx() } }

    // Custom shape to create the deeply curved bottom for the header
    val bottomCurvedShape = remember(curveDepth) {
        GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - curveDepth)
            // Draw a bezier curve to the other side
            quadraticBezierTo(
                size.width / 2f, size.height + (curveDepth / 2),
                0f, size.height - curveDepth
            )
            close()
        }
    }

    val textGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // ==================== Orange Gradient Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(bottomCurvedShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF6D00), // Deep Orange
                            Color(0xFFFFAC00)  // Yellow-Orange
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        ) {
            // Decorative large circle behind the back button
            Canvas(
                modifier = Modifier
                    .size(140.dp)
                    .offset(x = (-40).dp, y = (-20).dp)
            ) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.15f),
                    radius = size.width / 2
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                // ========== Top Bar ==========
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
                            .clickable { onBackClick() }
                    )

                    Text(
                        text = "Profile Settings",
                        fontSize = 20.sp,
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

                // ========== User Info Row ==========
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left Side: Name and ID
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // "Hello Mahendra," styling
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Hello ",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                style = TextStyle(
                                    brush = textGradient,
                                    drawStyle = Stroke(width = 3f)
                                )
                            )
                            Text(
                                text = "$userName,",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.ExtraBold,
                                style = TextStyle(
                                    brush = textGradient
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Anant Id: $anantId",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    // Right Side: Profile Picture
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63))
                                ),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .background(Color(0xFFF0F0F0), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Avatar",
                                tint = Color.Gray,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Bottom badge text
                Text(
                    text = "badge icon or image here",
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
            }
        }

        // ==================== Pink Section Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
                    )
                )
                .padding(vertical = 12.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Profile Updating Settings",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ==================== Settings Cards ====================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsCard(title = "CONTACT INFORMATION", onClick = onContactClick)
            SettingsCard(title = "FAMILY INFORMATION", onClick = onFamilyClick)
            SettingsCard(title = "Update Bank Accounts", onClick = onBankClick)
            SettingsCard(title = "Update Insurance Policies", onClick = onInsuranceClick)
            SettingsCard(title = "Update Medical Conditions", onClick = onMedicalClick)
        }

        Spacer(modifier = Modifier.height(48.dp))

        // ==================== Logout Button ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(38.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
                    ),
                    shape = RoundedCornerShape(35.dp)
                )
                .clickable { onLogoutClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Logout",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SettingsCard(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(99.dp),
                spotColor = Color(0xFFFF1493).copy(alpha = 0.1f), // Soft pinkish shadow
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .height(38.dp)
            .background(Color.White, RoundedCornerShape(99.dp))
            // Subtle pink border matching the design
            .border(1.dp, Color(0xFFFF1493).copy(alpha = 0.08f), RoundedCornerShape(99.dp))
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileSettingsScreenPreview() {
    ProfileSettingsScreen()
}
