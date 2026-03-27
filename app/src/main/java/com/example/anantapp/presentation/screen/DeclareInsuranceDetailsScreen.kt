package com.example.anantapp.presentation.screen

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
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeclareInsuranceDetailsScreen(
    onSkip: () -> Unit,
    onSubmit: () -> Unit
) {
    var policyNumber by remember { mutableStateOf("") }
    var accidentAmount by remember { mutableStateOf("") }
    var deathAmount by remember { mutableStateOf("") }
    var disabilityAmount by remember { mutableStateOf("") }
    var policyNum by remember { mutableStateOf("") }
    var insurerDetails by remember { mutableStateOf("") }
    var policies by remember { mutableStateOf(1) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Decorative orange circles in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top Right Large Circle
            drawCircle(
                color = Color(0xFFFF7A00),
                center = Offset(size.width, 100f),
                radius = 350f
            )

            // Bottom Left Circle
            drawCircle(
                color = Color(0xFFFF7A00),
                center = Offset(0f, size.height),
                radius = 300f
            )
        }

        // Main White Card Container (slight transparency to allow glows from the background)
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.92f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                            .background(Color.White, RoundedCornerShape(16.dp))
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

                Spacer(modifier = Modifier.height(24.dp))

                // Insurance Document Icon
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = "Insurance",
                    modifier = Modifier.size(64.dp),
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Declare Insurance\nDetails",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Policy Fields
                repeat(policies) { policyIndex ->
                    if (policyIndex > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Number Of Policy Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Number Of Policy",
                        value = if (policyIndex == 0) policyNumber else "",
                        onValueChange = { if (policyIndex == 0) policyNumber = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sum insured in case of label
                    Text(
                        text = "Sum insured in case of :",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Accident Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Accident",
                        value = if (policyIndex == 0) accidentAmount else "",
                        onValueChange = { if (policyIndex == 0) accidentAmount = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Death Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Death",
                        value = if (policyIndex == 0) deathAmount else "",
                        onValueChange = { if (policyIndex == 0) deathAmount = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Permanent Disability Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Permanent disability",
                        value = if (policyIndex == 0) disabilityAmount else "",
                        onValueChange = { if (policyIndex == 0) disabilityAmount = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Separator specific to the design
                    Divider(color = Color(0xFFE0E0E0), thickness = 1.dp, modifier = Modifier.padding(horizontal = 8.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    // Policy Number Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Policy number",
                        value = if (policyIndex == 0) policyNum else "",
                        onValueChange = { if (policyIndex == 0) policyNum = it }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Insurer Details Input
                    GradientPolicyInputField(
                        icon = "ooo",
                        hint = "Insurer details",
                        value = if (policyIndex == 0) insurerDetails else "",
                        onValueChange = { if (policyIndex == 0) insurerDetails = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Add Another Policy Button
                Box(
                    modifier = Modifier
                        .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp))
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(24.dp))
                        .clickable { policies += 1 }
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFE91E63),
                                            Color(0xFF9C27B0)
                                        )
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
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Add Another Policy",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF888888)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Submit Button with Gradient Border
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(4.dp, RoundedCornerShape(28.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF9C27B0),
                                    Color(0xFFE91E63),
                                    Color(0xFFFF8C00)
                                )
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(2.dp) // creates the border effect
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

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun GradientPolicyInputField(
    icon: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF5E00), // Dark Orange
                        Color(0xFFFFC107)  // Yellow-Gold
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            ),
        placeholder = {
            Text(
                text = hint,
                fontSize = 15.sp,
                color = Color.Black.copy(alpha = 0.8f) // Black text on gradient
            )
        },
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent, // Makes the OutlinedTextField see-through
            unfocusedContainerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(28.dp),
        singleLine = true,
        leadingIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(36.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DeclareInsuranceDetailsScreenPreview() {
    DeclareInsuranceDetailsScreen(
        onSkip = {},
        onSubmit = {}
    )
}
