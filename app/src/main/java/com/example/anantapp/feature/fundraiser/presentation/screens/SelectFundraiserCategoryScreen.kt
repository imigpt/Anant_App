package com.example.anantapp.feature.fundraiser.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.SelectFundraiserCategoryViewModel
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.FundraiserCategory

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectFundraiserCategoryScreen(
    onBackClick: () -> Unit = {},
    onNextClick: (selectedCategory: String, customTitle: String?) -> Unit = { _, _ -> },
    viewModel: SelectFundraiserCategoryViewModel = viewModel()
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Local state for the custom title input
    var customTitleText by remember { mutableStateOf("") }

    // Vibrant diagonal gradient (Deep Purple to Bright Pink/Coral)
    val mainGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF9500FF), // Deep Purple
            Color(0xFFFF2F4B)  // Bright Pinkish-Red
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGradient)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Header Area (Back Arrow & Title)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 4.dp)
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() }
                )

                Text(
                    text = "Select Fundraiser\nCategory",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // 2. Main White Card Container
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes remaining space
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 3. Hero Image
                Image(
                    painter = painterResource(id = R.drawable.people_volunteer_img),
                    contentDescription = "Volunteers putting hands together",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 4. Selection Chips (FlowRow for staggered wrap effect)
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.categories.forEach { category ->
                        CategoryChip(
                            text = category.name,
                            isSelected = uiState.selectedCategory == category.id,
                            gradientBrush = mainGradient,
                            onClick = {
                                viewModel.selectCategory(category.id)
                            }
                        )
                    }
                }

                // 5. Conditional Custom Title Input for "Other" Category
                AnimatedVisibility(
                    visible = uiState.selectedCategory == "other",
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .height(52.dp)
                                .border(width = 1.dp, brush = mainGradient, shape = CircleShape)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value = customTitleText,
                                onValueChange = { customTitleText = it },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                decorationBox = { innerTextField ->
                                    Box(contentAlignment = Alignment.Center) {
                                        if (customTitleText.isEmpty()) {
                                            Text(
                                                text = "Enter Title",
                                                color = Color.Gray,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // 6. Primary Action (Next Button)
                Button(
                    onClick = {
                        uiState.selectedCategory?.let {
                            onNextClick(it, customTitleText.takeIf { uiState.selectedCategory == "other" })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.5.dp, Color.Black),
                    shape = CircleShape // Pill shape
                ) {
                    Text(
                        text = "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean,
    gradientBrush: Brush,
    onClick: () -> Unit
) {
    val modifier = if (isSelected) {
        Modifier
            .background(brush = gradientBrush, shape = CircleShape)
    } else {
        Modifier
            .background(color = Color.White, shape = CircleShape)
            .border(width = 1.dp, brush = gradientBrush, shape = CircleShape)
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectFundraiserCategoryScreenPreview() {
    AnantAppTheme {
        SelectFundraiserCategoryScreen()
    }
}
