package com.example.anantapp.feature.verify.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.anantapp.feature.verify.presentation.viewmodel.FamilyDetailsViewModel

private val OrangeGradientStart = Color(0xFFFF8C00)
private val OrangeTransparent = Color(0xFFFF8C00).copy(alpha = 0.6f)
private val MainBackground = Color(0xFFF7F7F7)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF666666)

@Composable
fun FamilyDetailsScreen(
    viewModel: FamilyDetailsViewModel = viewModel(),
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
                color = OrangeTransparent,
                center = Offset(size.width - 50f, 150f),
                radius = 200f
            )
            drawCircle(
                color = OrangeTransparent,
                center = Offset(100f, size.height - 150f),
                radius = 150f
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.7f)
            ),
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
                    Text(
                        text = "Skip",
                        fontSize = 14.sp,
                        color = OrangeGradientStart,
                        modifier = Modifier.clickable { onSkipClick() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = Icons.Outlined.FamilyRestroom,
                    contentDescription = "Family",
                    modifier = Modifier.size(64.dp),
                    tint = TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Family Details",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Married Toggle
                FamilyToggleRow(
                    label = "Are you married?",
                    isChecked = uiState.isMarried,
                    onCheckedChange = { viewModel.toggleMarried(it) }
                )

                AnimatedVisibility(
                    visible = uiState.isMarried,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FamilyInputField(
                            icon = Icons.Outlined.Person,
                            hint = "Spouse Name",
                            value = uiState.spouseName,
                            onValueChange = { viewModel.updateSpouseField("name", it) }
                        )
                        FamilyInputField(
                            icon = Icons.Outlined.Person,
                            hint = "Spouse Age",
                            value = uiState.spouseAge,
                            onValueChange = { viewModel.updateSpouseField("age", it) }
                        )
                        FamilyInputField(
                            icon = Icons.Outlined.PhoneAndroid,
                            hint = "Spouse Mobile",
                            value = uiState.spouseMobile,
                            onValueChange = { viewModel.updateSpouseField("mobile", it) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Children Toggle
                FamilyToggleRow(
                    label = "Do you have children?",
                    isChecked = uiState.hasChildren,
                    onCheckedChange = { viewModel.toggleHasChildren(it) }
                )

                AnimatedVisibility(
                    visible = uiState.hasChildren,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(uiState.numChildren) { index ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Child ${index + 1}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextSecondary
                                )
                                FamilyInputField(
                                    icon = Icons.Outlined.Person,
                                    hint = "Child Name",
                                    value = if (index < uiState.childrenNames.size) uiState.childrenNames[index] else "",
                                    onValueChange = {}
                                )
                                FamilyInputField(
                                    icon = Icons.Outlined.Person,
                                    hint = "Child Age",
                                    value = if (index < uiState.childrenAges.size) uiState.childrenAges[index] else "",
                                    onValueChange = {}
                                )
                            }
                        }

                        FamilyAddButton(
                            text = "Add Child",
                            onClick = { viewModel.addChild("", "") }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Single Parent Toggle
                FamilyToggleRow(
                    label = "Single Parent?",
                    isChecked = uiState.isSingleParent,
                    onCheckedChange = { viewModel.toggleSingleParent(it) }
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Submit Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(Color(0xFF9500FF), Color(0xFFFF6264))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(enabled = !uiState.isLoading) {
                            viewModel.submitFamilyDetails()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            text = "Save Family Details",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        SnackbarHost(snackbarHostState)
    }
}

@Composable
private fun FamilyToggleRow(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(2.dp, RoundedCornerShape(28.dp))
            .background(Color(0xFFF5F5F5).copy(alpha = 0.6f), RoundedCornerShape(28.dp))
            .clickable { onCheckedChange(!isChecked) }
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
                color = TextPrimary
            )

            FamilySwitch(checked = isChecked, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun FamilySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 52.dp, height = 28.dp)
            .background(if (checked) Color.Green else Color.Black, RoundedCornerShape(14.dp))
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.White, CircleShape)
                .align(if (checked) Alignment.CenterEnd else Alignment.CenterStart)
        )
    }
}

@Composable
private fun FamilyInputField(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(2.dp, RoundedCornerShape(28.dp))
            .background(Color(0xFFF5F5F5), RoundedCornerShape(28.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.padding(8.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                ),
                cursorBrush = SolidColor(OrangeGradientStart),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            fontSize = 14.sp,
                            color = TextSecondary
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun FamilyAddButton(
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
                    .size(20.dp)
                    .background(OrangeGradientStart, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = OrangeGradientStart
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FamilyDetailsScreenPreview() {
    FamilyDetailsScreen()
}
