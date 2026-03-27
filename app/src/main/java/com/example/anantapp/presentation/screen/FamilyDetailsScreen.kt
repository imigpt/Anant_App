package com.example.anantapp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FamilyDetailsScreen(
    onSkip: () -> Unit,
    onSubmit: () -> Unit
) {
    // States for toggles
    var isMarried by remember { mutableStateOf(false) }
    var hasChildren by remember { mutableStateOf(false) }
    var isSingleParent by remember { mutableStateOf(false) }

    // States for Spouse Inputs
    var spouseName by remember { mutableStateOf("") }
    var spouseAge by remember { mutableStateOf("") }
    var spouseMobile by remember { mutableStateOf("") }

    // States for Kids Inputs
    var kidAge by remember { mutableStateOf("") }
    var kidName by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    // Main background container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // Light gray background
    ) {
        // Decorative solid orange circles in the background (not blur)
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top Right Blob
            drawCircle(
                color = Color(0xFFFF8C00).copy(alpha = 0.6f),
                center = Offset(size.width - 50f, 150f),
                radius = 200f
            )
            // Bottom Left Blob
            drawCircle(
                color = Color(0xFFFF8C00).copy(alpha = 0.6f),
                center = Offset(100f, size.height - 150f),
                radius = 150f
            )
        }

        // Main White Card Container with Glassmorphism
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                )
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(32.dp)
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f) // Semi-transparent for glassmorphism
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Skip Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { onSkip() }
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Skip >>",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                FamilyLineArtIcon()

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Family Details",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Add Family Member Pill Button
                AddPillButton(
                    text = "Add Your Family Member",
                    onClick = { /* Handle add family member */ }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Are you married Toggle
                ToggleOptionRow(
                    label = "Are you married",
                    isChecked = isMarried,
                    onCheckedChange = { isMarried = it }
                )

                AnimatedVisibility(
                    visible = isMarried,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        GradientInputField(
                            icon = Icons.Outlined.Person,
                            hint = "Spouse name",
                            value = spouseName,
                            onValueChange = { spouseName = it }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        GradientInputField(
                            icon = Icons.Outlined.FamilyRestroom,
                            hint = "Spouse age",
                            value = spouseAge,
                            onValueChange = { spouseAge = it }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        GradientInputField(
                            icon = Icons.Outlined.PhoneAndroid,
                            hint = "Spouse's mobile number",
                            value = spouseMobile,
                            onValueChange = { spouseMobile = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Do you have children Toggle
                ToggleOptionRow(
                    label = "Do you have children",
                    isChecked = hasChildren,
                    onCheckedChange = { hasChildren = it }
                )

                // Expanded Section for Children
                AnimatedVisibility(
                    visible = hasChildren,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        GradientInputField(
                            icon = Icons.Outlined.SentimentSatisfied, // Smiley Face Icon
                            hint = "Enter Your Kid's Age",
                            value = kidAge,
                            onValueChange = { kidAge = it }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        GradientInputField(
                            icon = Icons.Outlined.SentimentSatisfied, // Smiley Face Icon
                            hint = "Enter Your Kid's Name",
                            value = kidName,
                            onValueChange = { kidName = it }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Add Your Kid Button
                        AddPillButton(
                            text = "Add Your Kid",
                            onClick = { /* Handle add kid */ }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ToggleOptionRow(
                    label = "Are you a Single Parent",
                    isChecked = isSingleParent,
                    onCheckedChange = { isSingleParent = it }
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Submit Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63))
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(1.dp) // Border thickness
                        .background(Color.White, RoundedCornerShape(28.dp))
                        .clickable { onSubmit() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bottom Privacy Footer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Privacy Lock",
                        tint = Color(0xFFD0D0D0),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your data stays private & encrypted.",
                        fontSize = 12.sp,
                        color = Color(0xFFD0D0D0),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// Extracted Reusable Pill Button for Add Actions
@Composable
fun AddPillButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
            .background(Color(0xFFF5F5F5).copy(alpha = 0.6f), RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFE91E63), Color(0xFF9C27B0))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF888888)
            )
        }
    }
}

@Composable
fun GradientInputField(
    icon: ImageVector,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(2.dp, RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF8C00), // Orange
                        Color(0xFFFFB300)  // Yellow-Orange
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape)
                    .border(1.dp, Color(0xFFFF8C00), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = if (value.isEmpty()) hint else value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (value.isEmpty()) Color.Black.copy(alpha = 0.5f) else Color.Black
            )
        }
    }
}

@Composable
fun ToggleOptionRow(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(2.dp, RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF8C00),  // Orange
                        Color(0xFFFFB300)   // Yellow-Orange
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            CustomSwitch(checked = isChecked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val thumbOffset by animateDpAsState(targetValue = if (checked) 24.dp else 4.dp, label = "switch_offset")

    Box(
        modifier = Modifier
            .width(52.dp)
            .height(28.dp)
            .background(if (checked) Color.White else Color.Black, RoundedCornerShape(14.dp))
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .offset(x = thumbOffset)
                .background(if (checked) Color.Black else Color.White, CircleShape)
        )
    }
}

@Composable
fun FamilyLineArtIcon() {
    Canvas(modifier = Modifier.size(80.dp)) {
        val strokeWidth = 5f
        val color = Color.Black

        // Back Person (Right/Top)
        drawCircle(
            color = color,
            radius = 12.dp.toPx(),
            center = Offset(size.width * 0.65f, size.height * 0.25f),
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.45f, size.height * 0.45f),
            size = Size(size.width * 0.4f, size.height * 0.4f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Front Person (Left/Bottom)
        drawCircle(
            color = Color.White,
            radius = 16.dp.toPx(),
            center = Offset(size.width * 0.35f, size.height * 0.45f)
        )
        drawCircle(
            color = color,
            radius = 14.dp.toPx(),
            center = Offset(size.width * 0.35f, size.height * 0.45f),
            style = Stroke(width = strokeWidth)
        )
        drawArc(
            color = Color.White,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.1f, size.height * 0.7f),
            size = Size(size.width * 0.5f, size.height * 0.5f)
        )
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.1f, size.height * 0.7f),
            size = Size(size.width * 0.5f, size.height * 0.5f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FamilyDetailsScreenPreview() {
    FamilyDetailsScreen(onSkip = {}, onSubmit = {})
}
