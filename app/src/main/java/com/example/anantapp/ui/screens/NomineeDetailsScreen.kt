package com.example.anantapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anantapp.R
import com.example.anantapp.ui.theme.AnantAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NomineeDetailsScreen(
    onSubmitClick: () -> Unit = {},
    onGoBackClick: () -> Unit = {},
    onVerifyMobileClick: () -> Unit = {},
    onUploadClick: (onFilesSelected: (frontFile: String, backFile: String) -> Unit) -> Unit = {},
    onUploadSuccess: (Boolean) -> Unit = {}
) {
    // Form States
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
    val isEmergencyContact = remember { mutableStateOf(false) }
    val showDatePicker = remember { mutableStateOf(false) }
    
    // Upload State
    val isIdProofUploaded = remember { mutableStateOf(false) }
    val uploadedFrontFileName = remember { mutableStateOf("") }
    val uploadedBackFileName = remember { mutableStateOf("") }

    // Theme Gradient: #006FEE to #8BC1FF
    val primaryGradient = Brush.linearGradient(
        colors = listOf(Color(0xFF006FEE), Color(0xFF8BC1FF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGradient)
    ) {
        // Shared Bubble Background Image
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
                onDismiss = {
                    showDatePicker.value = false
                }
            )
        }

        // Main Frosted Glass Card Wrapper
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 48.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(32.dp),
                    ambientColor = Color.Black.copy(alpha = 0.15f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
        ) {
            // Blurred Background Layer for Frosted Glass Effect
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp))
                    .blur(radius = 30.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.3f),
                                Color.White.copy(alpha = 0.15f),
                                Color(0xFF4A9EFF).copy(alpha = 0.1f)
                            ),
                            radius = 600f
                        )
                    )
                    .border(
                        width = 1.5.dp,
                        color = Color.White.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(32.dp)
                    )
            )

            // Scrollable Form Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header: Minimalist Profile Icon and Title
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    MinimalistProfileIcon()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Nominee Details",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                // Add Another Nominee Button
                WhitePillButton(
                    icon = Icons.Default.Add,
                    text = "Add Another Nominee",
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Standard Form Fields
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WhitePillInputField(
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        placeholder = "Full Name"
                    )

                    WhitePillInputField(
                        value = relationToUser.value,
                        onValueChange = { relationToUser.value = it },
                        placeholder = "Relation to User"
                    )

                    WhitePillInputFieldWithIcon(
                        value = dateOfBirth.value,
                        onValueChange = { dateOfBirth.value = it },
                        placeholder = "Date of Birth",
                        icon = Icons.Default.DateRange,
                        keyboardType = KeyboardType.Number,
                        onIconClick = { showDatePicker.value = true }
                    )

                    WhitePillInputField(
                        value = gender.value,
                        onValueChange = { gender.value = it },
                        placeholder = "Gender Male / Female / Other"
                    )

                    WhitePillInputField(
                        value = mobileNumber.value,
                        onValueChange = { mobileNumber.value = it },
                        placeholder = "Mobile Number",
                        keyboardType = KeyboardType.Number,
                        maxLength = 10,
                        operation = {
                            OperationButton(text = "Verify", onClick = onVerifyMobileClick, gradient = primaryGradient)
                        }
                    )

                    WhitePillInputField(
                        value = emailId.value,
                        onValueChange = { emailId.value = it },
                        placeholder = "Email ID"
                    )
                }

                // Address Section
                SectionTitle(text = "Address", modifier = Modifier.padding(top = 24.dp, bottom = 16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WhitePillInputField(
                        value = houseNo.value,
                        onValueChange = { houseNo.value = it },
                        placeholder = "House NO. / Flat No. Building Name"
                    )

                    WhitePillInputField(
                        value = areaStreet.value,
                        onValueChange = { areaStreet.value = it },
                        placeholder = "Area, Street, Landmark"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        WhitePillInputField(
                            value = city.value,
                            onValueChange = { city.value = it },
                            placeholder = "City",
                            modifier = Modifier.weight(1f)
                        )
                        WhitePillInputField(
                            value = pinCode.value,
                            onValueChange = { pinCode.value = it },
                            placeholder = "Pin code",
                            modifier = Modifier.weight(1f),
                            keyboardType = KeyboardType.Number,
                            maxLength = 6
                        )
                    }
                }

                // ID Proof Type Section
                SectionTitle(text = "ID Proof Type", modifier = Modifier.padding(top = 24.dp, bottom = 16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IdProofToggleButton(
                            text = "Aadhaar",
                            isSelected = selectedIdProof.value == "Aadhaar",
                            onClick = {
                                selectedIdProof.value = "Aadhaar"
                                idNumber.value = ""
                            },
                            modifier = Modifier.weight(1f),
                            gradient = primaryGradient
                        )
                        IdProofToggleButton(
                            text = "Pan Card",
                            isSelected = selectedIdProof.value == "Pan Card",
                            onClick = {
                                selectedIdProof.value = "Pan Card"
                                idNumber.value = ""
                            },
                            modifier = Modifier.weight(1f),
                            gradient = primaryGradient
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IdProofToggleButton(
                            text = "Passport",
                            isSelected = selectedIdProof.value == "Passport",
                            onClick = {
                                selectedIdProof.value = "Passport"
                                idNumber.value = ""
                            },
                            modifier = Modifier.weight(1f),
                            gradient = primaryGradient
                        )
                        IdProofToggleButton(
                            text = "Other",
                            isSelected = selectedIdProof.value == "Other",
                            onClick = {
                                selectedIdProof.value = "Other"
                                idNumber.value = ""
                            },
                            modifier = Modifier.weight(1f),
                            gradient = primaryGradient
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dynamic ID Number Input Field
                    WhitePillInputField(
                        value = idNumber.value,
                        onValueChange = { idNumber.value = it },
                        placeholder = when (selectedIdProof.value) {
                            "Aadhaar" -> "Aadhaar Number"
                            "Pan Card" -> "Pan Card Number"
                            "Passport" -> "Passport Number"
                            else -> "ID Proof Number"
                        },
                        keyboardType = when (selectedIdProof.value) {
                            "Aadhaar" -> KeyboardType.Number
                            else -> KeyboardType.Text
                        },
                        maxLength = when (selectedIdProof.value) {
                            "Aadhaar" -> 12
                            "Pan Card" -> 10
                            else -> null
                        }
                    )
                }

                // Upload ID Proof Section
                SectionTitle(text = "Upload ID Proof (PDF/JPG)", modifier = Modifier.padding(top = 24.dp, bottom = 16.dp))

                if (isIdProofUploaded.value) {
                    // SHOW UPLOADED FILES LIST
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        UploadedFileItem(
                            title = "Upload Front Side",
                            fileName = uploadedFrontFileName.value.ifEmpty { "document_front.pdf" },
                            onDeleteClick = { 
                                isIdProofUploaded.value = false
                                uploadedFrontFileName.value = ""
                                uploadedBackFileName.value = ""
                            }
                        )
                        UploadedFileItem(
                            title = "Upload Back Side",
                            fileName = uploadedBackFileName.value.ifEmpty { "document_back.pdf" },
                            onDeleteClick = { 
                                isIdProofUploaded.value = false
                                uploadedFrontFileName.value = ""
                                uploadedBackFileName.value = ""
                            }
                        )
                    }
                } else {
                    // SHOW ORIGINAL UPLOAD BOX
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White.copy(alpha = 0.6f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.upload_doc),
                                    contentDescription = "Upload",
                                    modifier = Modifier.size(80.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Pls Upload Both The Front Side And Back Side",
                                    fontSize = 12.sp,
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )
                            }

                            // Upload Button with Gradient
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .height(48.dp)
                                    .clickable { 
                                        onUploadClick { frontFile, backFile ->
                                            uploadedFrontFileName.value = frontFile
                                            uploadedBackFileName.value = backFile
                                            isIdProofUploaded.value = true
                                        }
                                    }
                                    .shadow(6.dp, RoundedCornerShape(24.dp), ambientColor = Color.Black.copy(0.1f))
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(primaryGradient),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Upload ID Proof",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Emergency Contact Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Is nominee an emergency contact too?",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = isEmergencyContact.value,
                        onCheckedChange = { isEmergencyContact.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = Color.Black.copy(alpha = 0.3f),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
                        )
                    )
                }

                // Terms Text
                Text(
                    text = "I confirm these details are true and I agree to be a nominee.",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Submit Button (Linear Gradient)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(28.dp), ambientColor = Color.Black.copy(0.1f))
                        .clip(RoundedCornerShape(28.dp))
                        .background(primaryGradient)
                        .clickable { onSubmitClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Go Back Footer
                Row(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable { onGoBackClick() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "Go Back",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ----------------------------------------------------
// Reusable Sub-Components
// ----------------------------------------------------

@Composable
private fun UploadedFileItem(
    title: String,
    fileName: String,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp, // Soft shadow
                shape = RoundedCornerShape(16.dp), // Matched to UI image proportions
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = fileName,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            // Success Checkmark
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50)), // Green
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }

            // Delete Cross
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF44336)) // Red
                    .clickable { onDeleteClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun WhitePillInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int? = null,
    operation: (@Composable () -> Unit)? = null
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
                    onValueChange = {
                        if (maxLength == null || it.length <= maxLength) {
                            onValueChange(it)
                        }
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
            operation?.invoke()
        }
    }
}

@Composable
private fun WhitePillInputFieldWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int? = null,
    onIconClick: (() -> Unit)? = null,
    operation: (@Composable () -> Unit)? = null
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
                    onValueChange = {
                        if (maxLength == null || it.length <= maxLength) {
                            onValueChange(it)
                        }
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

            operation?.invoke() ?: Icon(
                imageVector = icon,
                contentDescription = placeholder,
                modifier = Modifier
                    .size(30.dp)
                    .padding(end = 8.dp)
                    .clickable { onIconClick?.invoke() },
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun OperationButton(
    text: String,
    onClick: () -> Unit,
    gradient: Brush
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(gradient)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun WhitePillButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
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
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun IdProofToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(if (isSelected) gradient else Brush.linearGradient(listOf(Color.White, Color.White)))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
private fun MinimalistProfileIcon() {
    Canvas(modifier = Modifier.size(64.dp)) {
        val strokeWidth = 5f

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
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
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
                    datePickerState.selectedDateMillis?.let {
                        onDateSelected(it)
                    }
                }
            ) {
                Text("OK")
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
private fun NomineeDetailsScreenPreview() {
    AnantAppTheme {
        NomineeDetailsScreen()
    }
}
