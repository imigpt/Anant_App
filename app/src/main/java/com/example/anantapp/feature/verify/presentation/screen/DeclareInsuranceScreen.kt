package com.example.anantapp.feature.verify.presentation.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.verify.presentation.viewmodel.DeclareInsuranceViewModel

private val OrangeGradient = Color(0xFFFF7A00)
private val MainBackground = Color(0xFFF7F7F7)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF333333)

@Composable
fun DeclareInsuranceScreen(
    viewModel: DeclareInsuranceViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onSubmitClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            onSubmitClick()
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            snackbarHostState.showSnackbar(uiState.error!!)
            viewModel.clearMessages()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = OrangeGradient,
                center = Offset(size.width, 100f),
                radius = 350f
            )
            drawCircle(
                color = OrangeGradient,
                center = Offset(0f, size.height),
                radius = 300f
            )
        }

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
                // Skip Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { onSkipClick() }
                            .border(1.dp, Color(0xFF333333), RoundedCornerShape(16.dp))
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

                Spacer(modifier = Modifier.height(24.dp))

                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = "Insurance",
                    modifier = Modifier.size(64.dp),
                    tint = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Declare Insurance\nDetails",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Policies List
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    repeat(uiState.policies.size) { policyIndex ->
                        if (policyIndex > 0) {
                            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Policy Number
                        InsuranceInputField(
                            hint = "Policy Number",
                            value = uiState.policies[policyIndex].policyNumber,
                            onValueChange = {
                                viewModel.updatePolicyField(policyIndex, "policyNumber", it)
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Sum insured in case of :",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextSecondary,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InsuranceInputField(
                            hint = "Accident",
                            value = uiState.policies[policyIndex].accidentAmount,
                            onValueChange = {
                                viewModel.updatePolicyField(policyIndex, "accidentAmount", it)
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InsuranceInputField(
                            hint = "Death",
                            value = uiState.policies[policyIndex].deathAmount,
                            onValueChange = {
                                viewModel.updatePolicyField(policyIndex, "deathAmount", it)
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InsuranceInputField(
                            hint = "Permanent Disability",
                            value = uiState.policies[policyIndex].disabilityAmount,
                            onValueChange = {
                                viewModel.updatePolicyField(policyIndex, "disabilityAmount", it)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp, modifier = Modifier.padding(horizontal = 8.dp))

                        Spacer(modifier = Modifier.height(16.dp))

                        InsuranceInputField(
                            hint = "Insurer Details",
                            value = uiState.policies[policyIndex].insurerDetails,
                            onValueChange = {
                                viewModel.updatePolicyField(policyIndex, "insurerDetails", it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Add Another Policy Button
                Box(
                    modifier = Modifier
                        .border(2.dp, OrangeGradient, RoundedCornerShape(12.dp))
                        .clickable { viewModel.addPolicy() }
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "+ Add Another Policy",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OrangeGradient
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Submit Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFF9000FF), Color(0xFFFF007A), Color(0xFFFF8C00))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .clickable(enabled = !uiState.isLoading) {
                            viewModel.submitInsuranceDetails()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = OrangeGradient, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Submit Insurance Details",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9500FF)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        SnackbarHost(snackbarHostState)
    }
}

@Composable
private fun InsuranceInputField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
                ),
                shape = RoundedCornerShape(28.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textStyle = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            cursorBrush = SolidColor(Color.Black),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        fontSize = 15.sp,
                        color = Color.Black.copy(alpha = 0.7f)
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DeclareInsuranceScreenPreview() {
    DeclareInsuranceScreen()
}
