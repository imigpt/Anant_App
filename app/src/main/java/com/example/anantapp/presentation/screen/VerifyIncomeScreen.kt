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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TextFormat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerifyIncomeScreen(
    onSkip: () -> Unit,
    onSubmit: () -> Unit
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
        // Decorative orange circles in background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFFF8C00),
                center = Offset(size.width, 100f),
                radius = 350f
            )
            drawCircle(
                color = Color(0xFFFF8C00),
                center = Offset(0f, size.height),
                radius = 300f
            )
        }

        // Main White Card Container
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 56.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Crossfade(targetState = showAddNomineeScreen, label = "ScreenTransition") { showingNominee ->
                // The crucial fix: Each screen state is wrapped in its own scrollable Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
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

    // Central Rupee Icon
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(3.dp, Color.Black, CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.CurrencyRupee,
            contentDescription = "Income Verification Icon",
            modifier = Modifier.size(40.dp),
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
        AppInputField(
            leadingIcon = Icons.Outlined.BarChart,
            hint = "Gross salary annually",
            value = grossSalary,
            onValueChange = onGrossSalaryChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.Calculate,
            hint = "Net salary",
            value = netSalary,
            onValueChange = onNetSalaryChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.AccountBalance,
            hint = "Salary account number",
            value = accountNumber,
            onValueChange = onAccountNumberChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.TextFormat,
            hint = "IFSC code",
            value = ifscCode,
            onValueChange = onIfscCodeChange
        )

        // Special "Add Nominee" Button configured exactly like an input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(2.dp, RoundedCornerShape(28.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF8C00), Color(0xFFFFD700))
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(2.dp)
                .background(Color.White, RoundedCornerShape(28.dp))
                .clickable { onAddNomineeClick() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, Color(0xFFFF8C00), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Nominee Icon",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Add Nominee",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go",
                    modifier = Modifier.size(20.dp).padding(end = 8.dp),
                    tint = Color.Black
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(48.dp))

    // Submit Button
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(4.dp, RoundedCornerShape(28.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63), Color(0xFFFF8C00))
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(2.dp)
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

    // Bold Rupee Icon
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(3.dp, Color.Black, CircleShape)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.CurrencyRupee,
            contentDescription = "Income Icon",
            modifier = Modifier.size(40.dp),
            tint = Color.Black
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Nominee Title
    Text(
        text = "Add Nominee Details",
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
        AppInputField(
            leadingIcon = Icons.Outlined.Person,
            hint = "Full Name",
            value = fullName,
            onValueChange = onFullNameChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.CalendarToday,
            trailingIcon = Icons.Outlined.CalendarToday,
            hint = "Date of Birth",
            value = dob,
            onValueChange = onDobChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.Key,
            hint = "Bank Account Number",
            value = accountNumber,
            onValueChange = onAccountNumberChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.TextFormat,
            hint = "IFSC code",
            value = ifscCode,
            onValueChange = onIfscCodeChange
        )
        AppInputField(
            leadingIcon = Icons.Outlined.BarChart,
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
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF9C27B0), Color(0xFFE91E63), Color(0xFFFF8C00))
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(2.dp)
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
    PrivacyFooter()
}

@Composable
fun AppInputField(
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
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
                    colors = listOf(Color(0xFFFF8C00), Color(0xFFFFD700))
                ),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(2.dp) // Creates the gradient border
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White, RoundedCornerShape(28.dp)),
            placeholder = {
                Text(
                    text = hint,
                    fontSize = 15.sp,
                    color = Color.Black.copy(alpha = 0.6f)
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
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(28.dp),
            singleLine = true,
            leadingIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, Color(0xFFFF8C00), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = if (trailingIcon != null) {
                {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).padding(end = 12.dp),
                        tint = Color.Black
                    )
                }
            } else null
        )
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
            tint = Color(0xFFB0B0B0),
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