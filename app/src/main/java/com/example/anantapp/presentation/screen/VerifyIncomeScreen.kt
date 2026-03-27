package com.example.anantapp.presentation.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.InsertChartOutlined
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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

// Reusable gradients based on the image
private val FieldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC400))
)
private val SubmitBorderGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF9000FF), Color(0xFFFF007A), Color(0xFFFF8C00))
)

@Composable
fun VerifyIncomeScreen(
    onSkip: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    var showAddNomineeScreen by remember { mutableStateOf(false) }

    // Verify Income Screen States
    var grossSalary by remember { mutableStateOf("") }
    var netSalary by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }

    // Nominee Screen States
    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var nomineeAccountNumber by remember { mutableStateOf("") }
    var nomineeIfscCode by remember { mutableStateOf("") }
    var shareOfFunds by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
    ) {
        // Soft gradient background blobs (not blur)
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top Right Blob
            drawCircle(
                color = Color(0xFFFF9800).copy(alpha = 0.6f),
                center = Offset(size.width - 50f, 150f),
                radius = 250f
            )
            // Bottom Left Blob
            drawCircle(
                color = Color(0xFFFF9800).copy(alpha = 0.6f),
                center = Offset(100f, size.height - 150f),
                radius = 200f
            )
        }

        // Main Card Container with Glassmorphism
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 56.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .border(
                    width = 1.5.dp,
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.75f) // Semi-transparent for glassmorphism
            )
        ) {
            Crossfade(targetState = showAddNomineeScreen, label = "ScreenTransition") { showingNominee ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (showingNominee) {
                        NomineeScreenContent(
                            onBackClick = { showAddNomineeScreen = false },
                            onSkip = onSkip,
                            onSubmit = onSubmit,
                            fullName = fullName,
                            onFullNameChange = { fullName = it },
                            dob = dob,
                            onDobChange = { dob = it },
                            accountNumber = nomineeAccountNumber,
                            onAccountNumberChange = { nomineeAccountNumber = it },
                            ifscCode = nomineeIfscCode,
                            onIfscCodeChange = { nomineeIfscCode = it },
                            shareOfFunds = shareOfFunds,
                            onShareOfFundsChange = { shareOfFunds = it }
                        )
                    } else {
                        MainIncomeContent(
                            onSkip = onSkip,
                            onSubmit = onSubmit,
                            onAddNomineeClick = { showAddNomineeScreen = true },
                            grossSalary = grossSalary,
                            onGrossSalaryChange = { grossSalary = it },
                            netSalary = netSalary,
                            onNetSalaryChange = { netSalary = it },
                            accountNumber = accountNumber,
                            onAccountNumberChange = { accountNumber = it },
                            ifscCode = ifscCode,
                            onIfscCodeChange = { ifscCode = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainIncomeContent(
    onSkip: () -> Unit,
    onSubmit: () -> Unit,
    onAddNomineeClick: () -> Unit,
    grossSalary: String,
    onGrossSalaryChange: (String) -> Unit,
    netSalary: String,
    onNetSalaryChange: (String) -> Unit,
    accountNumber: String,
    onAccountNumberChange: (String) -> Unit,
    ifscCode: String,
    onIfscCodeChange: (String) -> Unit
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

    Spacer(modifier = Modifier.height(24.dp))

    // Central Rupee Icon
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, Color.Black, CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.CurrencyRupee,
            contentDescription = "Income Verification Icon",
            modifier = Modifier.size(44.dp),
            tint = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Title
    Text(
        text = "Verify Income",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Input Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.InsertChartOutlined,
            hint = "Gross salary anually",
            value = grossSalary,
            onValueChange = onGrossSalaryChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.Calculate,
            hint = "Net salary",
            value = netSalary,
            onValueChange = onNetSalaryChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.SaveAlt,
            hint = "Salary account number",
            value = accountNumber,
            onValueChange = onAccountNumberChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.MoreHoriz,
            hint = "IFSC code",
            value = ifscCode,
            onValueChange = onIfscCodeChange
        )

        // Special "Add Nominee" Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(28.dp), spotColor = Color(0xFFFF9800).copy(alpha = 0.5f))
                .background(brush = FieldGradient, shape = RoundedCornerShape(28.dp))
                .clickable { onAddNomineeClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = "Nominee Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Add Nominee",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowOutward, // Diagonal arrow pointing up-right
                    contentDescription = "Go",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp),
                    tint = Color.Black
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))

    // Gradient Bordered Submit Button
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(brush = SubmitBorderGradient, shape = RoundedCornerShape(28.dp))
            .padding(2.dp) // Border thickness
            .background(Color.White, RoundedCornerShape(26.dp))
            .clickable { onSubmit() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Submit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
    PrivacyFooter()
}

@Composable
fun NomineeScreenContent(
    onBackClick: () -> Unit,
    onSkip: () -> Unit,
    onSubmit: () -> Unit,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    dob: String,
    onDobChange: (String) -> Unit,
    accountNumber: String,
    onAccountNumberChange: (String) -> Unit,
    ifscCode: String,
    onIfscCodeChange: (String) -> Unit,
    shareOfFunds: String,
    onShareOfFundsChange: (String) -> Unit
) {
    // Top Row: Back & Skip Buttons
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable { onBackClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Back",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

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

    Spacer(modifier = Modifier.height(24.dp))

    // Bold Rupee Icon
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(2.dp, Color.Black, CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.CurrencyRupee,
            contentDescription = "Income Icon",
            modifier = Modifier.size(44.dp),
            tint = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Nominee Title
    Text(
        text = "Add Nominee",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Nominee Input Fields
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.PersonOutline,
            hint = "Full Name",
            value = fullName,
            onValueChange = onFullNameChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.CalendarToday,
            hint = "Date of Birth",
            value = dob,
            onValueChange = onDobChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.Key,
            hint = "Bank Account Number",
            value = accountNumber,
            onValueChange = onAccountNumberChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.MoreHoriz,
            hint = "IFSC code",
            value = ifscCode,
            onValueChange = onIfscCodeChange
        )
        IncomeGradientInputField(
            leadingIcon = Icons.Outlined.InsertChartOutlined,
            hint = "% Share of Funds",
            value = shareOfFunds,
            onValueChange = onShareOfFundsChange
        )
    }

    Spacer(modifier = Modifier.height(48.dp))

    // Submit Button
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(brush = SubmitBorderGradient, shape = RoundedCornerShape(28.dp))
            .padding(2.dp)
            .background(Color.White, RoundedCornerShape(26.dp))
            .clickable { onSubmit() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Submit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(24.dp))
    PrivacyFooter()
}

@Composable
private fun IncomeGradientInputField(
    leadingIcon: ImageVector,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color(0xFFFF9800).copy(alpha = 0.5f) // Warm drop shadow
            )
            .background(
                brush = FieldGradient,
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // White circular icon background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text input over the gradient
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        fontSize = 14.sp,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Normal
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun PrivacyFooter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Lock,
            contentDescription = "Privacy Lock",
            tint = Color(0xFFFFB74D), // Light orange tint to match image
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Your data stays private & encrypted.",
            fontSize = 12.sp,
            color = Color(0xFFB0B0B0),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyIncomeScreenPreview() {
    VerifyIncomeScreen()
}
