package com.example.anantapp.feature.verify.presentation.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.verify.presentation.viewmodel.VerifyIncomeViewModel

private val FieldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
)
private val SubmitBorderGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF9000FF), Color(0xFFFF007A), Color(0xFFFF8C00))
)
private val PurpleAccent = Color(0xFF9500FF)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)

@Composable
fun VerifyIncomeScreen(
    viewModel: VerifyIncomeViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            onSuccess()
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage!!)
            viewModel.clearMessages()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        BackgroundDecorationIncome()

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (uiState.currentStep == 1) {
                    IncomeStepContent(
                        uiState = uiState,
                        viewModel = viewModel,
                        onSkipClick = onSkipClick,
                        onNextClick = { viewModel.proceedToNominee() }
                    )
                } else {
                    NomineeStepContent(
                        uiState = uiState,
                        viewModel = viewModel,
                        onSkipClick = onSkipClick,
                        onBackClick = { viewModel.backToIncome() },
                        onSubmitClick = { viewModel.submitIncomeVerification() }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (uiState.isLoading) {
            LoadingOverlayIncome()
        }

        SnackbarHost(snackbarHostState)
    }
}

@Composable
private fun IncomeStepContent(
    uiState: com.example.anantapp.feature.verify.presentation.viewmodel.VerifyIncomeUiState,
    viewModel: VerifyIncomeViewModel,
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit
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
            color = PurpleAccent,
            modifier = Modifier.clickable { onSkipClick() }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Icon(
        imageVector = Icons.Outlined.Calculate,
        contentDescription = null,
        modifier = Modifier.size(80.dp),
        tint = PurpleAccent
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Income Verification",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Verify your income details",
        fontSize = 14.sp,
        color = TextSecondary,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Income Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IncomeGradientInputField(
            value = uiState.grossSalary,
            onValueChange = { viewModel.updateIncomeField("grossSalary", it) },
            placeholder = "Gross Salary",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.netSalary,
            onValueChange = { viewModel.updateIncomeField("netSalary", it) },
            placeholder = "Net Salary",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.accountNumber,
            onValueChange = { viewModel.updateIncomeField("accountNumber", it) },
            placeholder = "Account Number",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.ifscCode,
            onValueChange = { viewModel.updateIncomeField("ifscCode", it) },
            placeholder = "IFSC Code",
            icon = Icons.Outlined.Calculate
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    // Next Button
    IncomeSubmitButton(
        text = "Add Nominee",
        onClick = { onNextClick() }
    )
}

@Composable
private fun NomineeStepContent(
    uiState: com.example.anantapp.feature.verify.presentation.viewmodel.VerifyIncomeUiState,
    viewModel: VerifyIncomeViewModel,
    onSkipClick: () -> Unit,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    // Back button
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackClick() },
            tint = PurpleAccent
        )

        Text(
            text = "Skip",
            fontSize = 14.sp,
            color = PurpleAccent,
            modifier = Modifier.clickable { onSkipClick() }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Icon(
        imageVector = Icons.Outlined.Person,
        contentDescription = null,
        modifier = Modifier.size(80.dp),
        tint = PurpleAccent
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Nominee Details",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Nominee Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IncomeGradientInputField(
            value = uiState.nomineeName,
            onValueChange = { viewModel.updateNomineeField("nomineeName", it) },
            placeholder = "Nominee Name",
            icon = Icons.Outlined.Person
        )

        IncomeGradientInputField(
            value = uiState.nomineeDob,
            onValueChange = { viewModel.updateNomineeField("nomineeDob", it) },
            placeholder = "Date of Birth",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.nomineeAccountNumber,
            onValueChange = { viewModel.updateNomineeField("nomineeAccountNumber", it) },
            placeholder = "Account Number",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.nomineeIfscCode,
            onValueChange = { viewModel.updateNomineeField("nomineeIfscCode", it) },
            placeholder = "IFSC Code",
            icon = Icons.Outlined.Calculate
        )

        IncomeGradientInputField(
            value = uiState.shareOfFunds,
            onValueChange = { viewModel.updateNomineeField("shareOfFunds", it) },
            placeholder = "Share of Funds (%)",
            icon = Icons.Outlined.Calculate
        )
    }

    Spacer(modifier = Modifier.height(32.dp))

    // Submit Button
    IncomeSubmitButton(
        text = "Verify Income",
        onClick = { onSubmitClick() }
    )
}

@Composable
private fun IncomeGradientInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(
                brush = FieldGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Composable
private fun IncomeSubmitButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 2.dp,
                brush = SubmitBorderGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = PurpleAccent
        )
    }
}

@Composable
private fun LoadingOverlayIncome() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = PurpleAccent)
        }
    }
}

@Composable
private fun BackgroundDecorationIncome() {
    Box(
        modifier = Modifier
            .size(250.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(PurpleAccent.copy(alpha = 0.1f), Color.Transparent)
                ),
                shape = RoundedCornerShape(100.dp)
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyIncomeScreenPreview() {
    VerifyIncomeScreen()
}
