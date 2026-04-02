package com.example.anantapp.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CarCrash
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SOSScreen(
    modifier: Modifier = Modifier,
    onSOSClick: () -> Unit = {},
    onEmergencyTypeClick: (String) -> Unit = {},
    onCheckHistoryClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    currentLocation: String = "Horizon Towers Jaipur"
) {
    BackHandler(onBack = onBackClick)

    var selectedEmergencyType by remember { mutableStateOf<String?>(null) }

    // Colors matched from the screenshot
    val textPrimary = Color(0xFF1F2937)
    val textSecondary = Color(0xFF6B7280)
    val appBg = Color(0xFFF9FAFB)
    val purpleTheme = Color(0xFF8B21FF)
    val redButton = Color(0xFFFF2400)
    val redButtonGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFFF4B2B), Color(0xFFFF416C))
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Header Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(appBg)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Logo
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = "App Logo",
                    tint = purpleTheme,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Location Text
            Column {
                Text(
                    text = "Current location",
                    fontSize = 12.sp,
                    color = textSecondary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Pin",
                        tint = textPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = currentLocation,
                        fontSize = 14.sp,
                        color = textPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // --- Hero Text & Illustration Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(0.55f)) {
                Text(
                    text = "Are you in an\nemergency?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary,
                    lineHeight = 34.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Press the SOS button, your live location will be shared with your Nominated Family Members and Single tap sends your real time location.",
                    fontSize = 13.sp,
                    color = textSecondary,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Illustration Placeholder (mimicking the purple arch shape)
            Box(
                modifier = Modifier
                    .weight(0.45f)
                    .height(160.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    // Purple arch background
                    drawRoundRect(
                        color = purpleTheme,
                        size = Size(width, height),
                        cornerRadius = CornerRadius(100f, 20f)
                    )

                    // Abstract figures mimicking the people in the graphic
                    drawCircle(color = Color(0xFFB678FF), radius = 30f, center = Offset(width * 0.4f, 60f))
                    drawCircle(color = Color(0xFF2C1B4D), radius = 40f, center = Offset(width * 0.7f, 40f))

                    // Body paths
                    drawRoundRect(
                        color = Color(0xFFB678FF),
                        topLeft = Offset(width * 0.1f, 80f),
                        size = Size(width * 0.6f, height - 80f),
                        cornerRadius = CornerRadius(20f, 20f)
                    )
                }
            }
        }

        // --- SOS Button Card Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(24.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(24.dp), clip = false)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Soft grey drop shadow ring around the button
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(Color(0xFFE5E7EB), Color.Transparent),
                            radius = 400f
                        )
                    )
            )

            // Outer white ring
            Box(
                modifier = Modifier
                    .size(190.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .shadow(10.dp, CircleShape)
            )

            // Inner Red Gradient Button
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(redButtonGradient)
                    .clickable {
                        selectedEmergencyType = null
                        onSOSClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SOS",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Emergency Grid Section ---
        Text(
            text = "Whats your emergency?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = textPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val purpleIconBg = Color(0xFF9300FF)

            EmergencyChip("Medical", Icons.Default.MedicalServices, purpleIconBg)
            EmergencyChip("fire", Icons.Default.LocalFireDepartment, purpleIconBg)
            EmergencyChip("Natural disaster", Icons.Default.Domain, purpleIconBg)
            EmergencyChip("Accident", Icons.Default.CarCrash, purpleIconBg)
            EmergencyChip("Violence", Icons.Default.Warning, purpleIconBg)
            EmergencyChip("Rescue", Icons.Default.PanTool, purpleIconBg)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- Bottom Button Section ---
        Button(
            onClick = onCheckHistoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = redButton),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Check SOS History",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun EmergencyChip(
    title: String,
    icon: ImageVector,
    iconBgColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(30.dp))
            .background(Color.White, RoundedCornerShape(30.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { /* Handle specific emergency click here */ }
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            fontSize = 13.sp,
            color = Color(0xFF374151),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SOSScreenPreview() {
    SOSScreen()
}