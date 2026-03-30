package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Family Information Screen
 * Displays form for family information updates
 */
@Composable
fun FamilyInformationScreen(
    userName: String = "Mahendra",
    anantId: String = "#9121038605",
    onBackClick: () -> Unit = {},
    onUpdateClick: () -> Unit = {}
) {
    // Form state
    val familyHeadName = remember { mutableStateOf("Mahendra") }
    val spouseName = remember { mutableStateOf("Tusharika") }
    val spouseAge = remember { mutableStateOf("23") }
    val nominee1 = remember { mutableStateOf("Yashu") }
    val nominee2 = remember { mutableStateOf("Raju") }
    val maritalStatus = remember { mutableStateOf("Married") }
    val familyInsuranceStatus = remember { mutableStateOf("Active") }

    val density = LocalDensity.current
    val curveDepth = remember(density) { with(density) { 48.dp.toPx() } }

    // Custom shape for the soft curved bottom of the header
    val curvedShape = remember(curveDepth) {
        GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - curveDepth)
            quadraticBezierTo(
                size.width / 2f, size.height + curveDepth,
                0f, size.height - curveDepth
            )
            close()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // ==================== Orange Curved Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(curvedShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFF6300), // Vibrant Orange
                            Color(0xFFFFCF11)  // Yellow-Orange
                        )
                    )
                )
        ) {
            // Decorative overlapping circle on the top left
            Canvas(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = (-40).dp, y = (-20).dp)
            ) {
                drawCircle(color = Color(0xFFFF8C00).copy(alpha = 0.4f)) // Slightly darker orange for contrast
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
                    // Back Button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Text(
                        text = "Profile Settings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
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
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "Hello ",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9C27B0) // Purple
                            )
                            Text(
                                text = "$userName,",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFFE91E63) // Pink
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Anant Id: $anantId",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "badge icon or image here",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    // Right Side: Profile Picture
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264))
                                ),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color(0xFFF5F5F5), CircleShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Profile Picture",
                                tint = Color(0xFFCCCCCC),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }

        // ==================== Purple/Pink Section Header ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF6264))
                    )
                )
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            Text(
                text = "Update Family Information",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Start, // Left aligned per the design
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==================== Form Fields ====================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomFormField(label = "Family Head Name", value = familyHeadName.value, onValueChange = { familyHeadName.value = it })
            CustomFormField(label = "Spouse Name", value = spouseName.value, onValueChange = { spouseName.value = it })
            CustomFormField(label = "Spouse Age", value = spouseAge.value, onValueChange = { spouseAge.value = it })
            CustomFormField(label = "Nominee 1", value = nominee1.value, onValueChange = { nominee1.value = it })
            CustomFormField(label = "Nominee 2", value = nominee2.value, onValueChange = { nominee2.value = it })
            CustomFormField(label = "Martial Status", value = maritalStatus.value, onValueChange = { maritalStatus.value = it })
            CustomFormField(label = "Family Insurance Status", value = familyInsuranceStatus.value, onValueChange = { familyInsuranceStatus.value = it })
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ==================== Update Button ====================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(52.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(99.dp), spotColor = Color(0xFFE91E63))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF9500FF), Color(0xFFFF1493))
                    ),
                    shape = RoundedCornerShape(99.dp)
                )
                .clickable { onUpdateClick() },
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

/**
 * Custom clean form field to replicate the design exactly
 */
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
private fun PreviewFamilyInformationScreen() {
    FamilyInformationScreen()
}
