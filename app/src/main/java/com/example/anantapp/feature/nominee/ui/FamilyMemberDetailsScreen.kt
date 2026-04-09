package com.example.anantapp.feature.nominee.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R
import com.example.anantapp.ui.theme.AnantAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FamilyMemberDetailsScreen(
    onSubmitClick: () -> Unit = {},
    onGoBackClick: () -> Unit = {},
    onUploadClick: (onFilesSelected: (frontFile: String, backFile: String) -> Unit) -> Unit = {}
) {
    val fullName = remember { mutableStateOf("") }
    val relationToUser = remember { mutableStateOf("") }
    val dateOfBirth = remember { mutableStateOf("") }
    val gender = remember { mutableStateOf("") }
    val mobileNumber = remember { mutableStateOf("") }
    val emailId = remember { mutableStateOf("") }
    val houseNo = remember { mutableStateOf("") }
    val areaStreet = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val pinCode = remember { mutableStateOf("") }
    val idNumber = remember { mutableStateOf("") }
    val selectedIdProof = remember { mutableStateOf("Aadhaar") }
    val showDatePicker = remember { mutableStateOf(false) }
    
    val isIdProofUploaded = remember { mutableStateOf(false) }
    val uploadedFrontFileName = remember { mutableStateOf("") }
    val uploadedBackFileName = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.blue_background_with_bubbles),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Date Picker Dialog
        if (showDatePicker.value) {
            DatePickerDialogComponent(
                onDateSelected = { selectedDate ->
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    dateOfBirth.value = formatter.format(Date(selectedDate))
                    showDatePicker.value = false
                },
                onDismiss = { showDatePicker.value = false }
            )
        }

        // Main Frosted Glass Card
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.88f)
                .padding(vertical = 48.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color(0xFF000000).copy(alpha = 0.1f),
                    spotColor = Color(0xFF000000).copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.White.copy(alpha = 0.15f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.1f)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            // Scrollable Form Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Column(
                    modifier = Modifier.padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MinimalistProfileIconFamily()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Family Member Details",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Form Fields
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WhitePillInputFieldFamily(
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        placeholder = "Full Name",
                        keyboardType = KeyboardType.Text
                    )

                    WhitePillInputFieldFamily(
                        value = relationToUser.value,
                        onValueChange = { relationToUser.value = it },
                        placeholder = "Relation to User (e.g., Spouse, Son, Mother)"
                    )

                    WhitePillInputFieldWithIconFamily(
                        value = dateOfBirth.value,
                        onValueChange = { dateOfBirth.value = it },
                        placeholder = "Date of Birth",
                        icon = Icons.Default.DateRange,
                        onIconClick = { showDatePicker.value = true }
                    )

                    WhitePillInputFieldFamily(
                        value = gender.value,
                        onValueChange = { gender.value = it },
                        placeholder = "Gender (Male/Female/Other)"
                    )

                    WhitePillInputFieldFamily(
                        value = mobileNumber.value,
                        onValueChange = { 
                            if (it.length <= 10) {
                                mobileNumber.value = it.filter { c -> c.isDigit() }
                            }
                        },
                        placeholder = "Mobile Number",
                        keyboardType = KeyboardType.Phone
                    )

                    WhitePillInputFieldFamily(
                        value = emailId.value,
                        onValueChange = { emailId.value = it },
                        placeholder = "Email ID",
                        keyboardType = KeyboardType.Email
                    )

                    // Address Section
                    Text(
                        text = "Address",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    WhitePillInputFieldFamily(
                        value = houseNo.value,
                        onValueChange = { houseNo.value = it },
                        placeholder = "House No. / Flat No."
                    )

                    WhitePillInputFieldFamily(
                        value = areaStreet.value,
                        onValueChange = { areaStreet.value = it },
                        placeholder = "Area / Street / Road"
                    )

                    WhitePillInputFieldFamily(
                        value = city.value,
                        onValueChange = { city.value = it },
                        placeholder = "City"
                    )

                    WhitePillInputFieldFamily(
                        value = pinCode.value,
                        onValueChange = { 
                            if (it.length <= 6) {
                                pinCode.value = it.filter { c -> c.isDigit() }
                            }
                        },
                        placeholder = "Pin Code",
                        keyboardType = KeyboardType.Number
                    )

                    // ID Proof Section
                    Text(
                        text = "ID Proof",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    WhitePillInputFieldFamily(
                        value = selectedIdProof.value,
                        onValueChange = { selectedIdProof.value = it },
                        placeholder = "ID Type (Aadhaar/PAN/Passport/DL)"
                    )

                    WhitePillInputFieldFamily(
                        value = idNumber.value,
                        onValueChange = { idNumber.value = it },
                        placeholder = "ID Number"
                    )

                    // Upload Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.15f)
                            )
                            .clip(RoundedCornerShape(28.dp))
                            .background(Color.White)
                            .clickable { 
                                onUploadClick { front, back ->
                                    isIdProofUploaded.value = true
                                    uploadedFrontFileName.value = front
                                    uploadedBackFileName.value = back
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isIdProofUploaded.value) "✓ ID Proof Uploaded" else "Upload ID Proof (Front & Back)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Go Back and Submit Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Go Back Button
                    Box(
                        modifier = Modifier
                            .weight(0.4f)
                            .height(48.dp)
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.15f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .clickable { onGoBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Go Back",
                                modifier = Modifier.size(18.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Back",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }

                    // Submit Button
                    Box(
                        modifier = Modifier
                            .weight(0.6f)
                            .height(48.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                ambientColor = Color.Black.copy(alpha = 0.1f),
                                spotColor = Color.Black.copy(alpha = 0.15f)
                            )
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .clickable { onSubmitClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Submit Details",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun WhitePillInputFieldFamily(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(start = 24.dp, end = 24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                color = Color(0xFFAAAAAA),
                fontWeight = FontWeight.Normal
            )
        }
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                val finalValue = maxLength?.let { newValue.take(it) } ?: newValue
                onValueChange(finalValue)
            },
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
private fun WhitePillInputFieldWithIconFamily(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int? = null,
    onIconClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .padding(start = 24.dp, end = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = Color(0xFFAAAAAA),
                        fontWeight = FontWeight.Normal
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        val finalValue = maxLength?.let { newValue.take(it) } ?: newValue
                        onValueChange(finalValue)
                    },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onIconClick?.invoke() },
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun MinimalistProfileIconFamily() {
    Canvas(modifier = Modifier.size(64.dp)) {
        val strokeWidth = 0.3f

        // Head circle
        drawCircle(
            color = Color.Black,
            radius = size.width * 0.20f,
            center = Offset(size.width / 2, size.height * 0.30f),
            style = Stroke(width = strokeWidth)
        )

        // Shoulders arc
        drawArc(
            color = Color.Black,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.15f, size.height * 0.45f),
            size = Size(size.width * 0.70f, size.height * 0.60f),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerDialogComponent(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun FamilyMemberDetailsScreenPreview() {
    AnantAppTheme {
        FamilyMemberDetailsScreen()
    }
}
