package com.example.anantapp.feature.fundraiser.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.core.presentation.theme.AnantAppTheme
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.CreateFundraiserUiState
import com.example.anantapp.feature.fundraiser.presentation.viewmodel.CreateFundraiserViewModel

@Composable
fun CreateFundraiserScreen(
    viewModel: CreateFundraiserViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    CreateFundraiserContent(
        uiState = uiState,
        onTitleChange = { viewModel.updateTitle(it) },
        onDescriptionChange = { viewModel.updateDescription(it) },
        onCategoryChange = { viewModel.updateCategory(it) },
        onBeneficiaryNameChange = { viewModel.updateBeneficiaryName(it) },
        onBeneficiaryRelationChange = { viewModel.updateBeneficiaryRelation(it) },
        onBackClick = onBackClick,
        onDraftSaved = onDraftSaved,
        onNextClick = onNextClick
    )
}

@Composable
fun CreateFundraiserContent(
    uiState: CreateFundraiserUiState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onBeneficiaryNameChange: (String) -> Unit,
    onBeneficiaryRelationChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onDraftSaved: () -> Unit,
    onNextClick: () -> Unit
) {
    val mainGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF9500FF), Color(0xFFFF2F4B)),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(28.dp)
                        .clickable { onBackClick() }
                )

                Text(
                    text = "Create Fundraiser",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Fields
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                // Title Field
                FormLabel(text = "Fundraiser Title *")
                Spacer(modifier = Modifier.height(8.dp))
                FundraiserInputField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    placeholder = "Enter fundraiser title",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Description Field
                FormLabel(text = "Description *")
                Spacer(modifier = Modifier.height(8.dp))
                FundraiserInputField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    placeholder = "Enter description",
                    mainGradient = mainGradient,
                    singleLine = false,
                    minHeight = 100.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Category Field
                FormLabel(text = "Category *")
                Spacer(modifier = Modifier.height(8.dp))
                FundraiserInputField(
                    value = uiState.category,
                    onValueChange = onCategoryChange,
                    placeholder = "Select category",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Beneficiary Name
                FormLabel(text = "Beneficiary Name *")
                Spacer(modifier = Modifier.height(8.dp))
                FundraiserInputField(
                    value = uiState.beneficiaryName,
                    onValueChange = onBeneficiaryNameChange,
                    placeholder = "Enter beneficiary name",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Beneficiary Relation
                FormLabel(text = "Relation *")
                Spacer(modifier = Modifier.height(8.dp))
                FundraiserInputField(
                    value = uiState.beneficiaryRelation,
                    onValueChange = onBeneficiaryRelationChange,
                    placeholder = "Enter relation",
                    mainGradient = mainGradient
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Draft Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .border(1.5.dp, mainGradient, CircleShape)
                        .clip(CircleShape)
                        .clickable { onDraftSaved() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Draft",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9500FF)
                    )
                }

                // Next Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .background(mainGradient, CircleShape)
                        .clickable { onNextClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )
}

@Composable
fun FundraiserInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    mainGradient: Brush,
    singleLine: Boolean = true,
    minHeight: androidx.compose.ui.unit.Dp = 52.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .border(1.5.dp, mainGradient, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateFundraiserScreenPreview() {
    AnantAppTheme {
        CreateFundraiserContent(
            uiState = CreateFundraiserUiState(),
            onTitleChange = {},
            onDescriptionChange = {},
            onCategoryChange = {},
            onBeneficiaryNameChange = {},
            onBeneficiaryRelationChange = {},
            onBackClick = {},
            onDraftSaved = {},
            onNextClick = {}
        )
    }
}
