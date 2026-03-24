package com.example.anantapp.presentation.screen

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.LocalTextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.GenerateQRCodeViewModel

// ==================== Data Classes ====================

data class QRCodeFormState(
    val fullName: String = "",
    val selectedVehicleType: String? = null,
    val vehicleNumberPlate: String = "",
    val insurancePolicyNumber: String = "",
    val insuranceValidTill: String = "",
    val emergencyContactFamilyName: String = "",
    val emergencyContactFamilyPhone: String = "",
    val emergencyContactFriendName: String = "",
    val emergencyContactFriendPhone: String = "",
    val selectedMedicalConditions: Set<String> = emptySet(),
    val uploadedPhotoPath: String? = null,
    val uploadedRCPath: String? = null,
    val uploadedInsurancePath: String? = null
)

// ==================== Main Screen ====================

@Composable
fun GenerateQRCodeScreen(
    viewModel: GenerateQRCodeViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNextClick: (QRCodeFormState) -> Unit = {}
) {
    val formState by viewModel.formState.collectAsState()
    GenerateQRCodeContent(
        formState = formState,
        onFullNameChange = { viewModel.updateFullName(it) },
        onPhotoPathUpdate = { viewModel.updatePhotoPath(it) },
        onVehicleTypeUpdate = { viewModel.updateVehicleType(it) },
        onVehicleNumberPlateUpdate = { viewModel.updateVehicleNumberPlate(it) },
        onInsurancePolicyNumberUpdate = { viewModel.updateInsurancePolicyNumber(it) },
        onInsuranceValidTillUpdate = { viewModel.updateInsuranceValidTill(it) },
        onRCPathUpdate = { viewModel.updateRCPath(it) },
        onInsurancePathUpdate = { viewModel.updateInsurancePath(it) },
        onEmergencyContactFamilyNameUpdate = { viewModel.updateEmergencyContactFamilyName(it) },
        onEmergencyContactFamilyPhoneUpdate = { viewModel.updateEmergencyContactFamilyPhone(it) },
        onEmergencyContactFriendNameUpdate = { viewModel.updateEmergencyContactFriendName(it) },
        onEmergencyContactFriendPhoneUpdate = { viewModel.updateEmergencyContactFriendPhone(it) },
        onMedicalConditionToggle = { viewModel.toggleMedicalCondition(it) },
        onBackClick = onBackClick,
        onNextClick = { onNextClick(viewModel.getFormData()) }
    )
}

@Composable
fun GenerateQRCodeContent(
    formState: QRCodeFormState,
    onFullNameChange: (String) -> Unit,
    onPhotoPathUpdate: (String) -> Unit,
    onVehicleTypeUpdate: (String) -> Unit,
    onVehicleNumberPlateUpdate: (String) -> Unit,
    onInsurancePolicyNumberUpdate: (String) -> Unit,
    onInsuranceValidTillUpdate: (String) -> Unit,
    onRCPathUpdate: (String) -> Unit,
    onInsurancePathUpdate: (String) -> Unit,
    onEmergencyContactFamilyNameUpdate: (String) -> Unit,
    onEmergencyContactFamilyPhoneUpdate: (String) -> Unit,
    onEmergencyContactFriendNameUpdate: (String) -> Unit,
    onEmergencyContactFriendPhoneUpdate: (String) -> Unit,
    onMedicalConditionToggle: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    // Image launcher for photo upload
    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { onPhotoPathUpdate(it.toString()) }
    }

    // Camera launcher for selfie
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            onPhotoPathUpdate("camera://selfie_${System.currentTimeMillis()}")
        }
    }

    // File launcher for RC document
    val rcFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onRCPathUpdate(it.toString()) }
    }

    // File launcher for Insurance document
    val insuranceFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onInsurancePathUpdate(it.toString()) }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        BackgroundBubbles()

        // Main content with glassmorphic card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, bottom = 24.dp)
        ) {
            // Header with back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onBackClick() },
                    tint = Color.White
                )
            }

            // Glassmorphic main card
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.6f))
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header with QR icon and title
                    HeaderSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Full Name Input
                    CustomInputField(
                        value = formState.fullName,
                        onValueChange = onFullNameChange,
                        placeholder = "Full Name",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Upload Photo Section
                    UploadPhotoSection(
                        hasPhoto = formState.uploadedPhotoPath != null,
                        onTakeSelfieClick = { cameraLauncher.launch(null) },
                        onChooseFromGalleryClick = {
                            photoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Vehicle Type Section
                    VehicleTypeSection(
                        selectedType = formState.selectedVehicleType,
                        onTypeSelected = onVehicleTypeUpdate
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Vehicle Details Section
                    VehicleDetailsSection(
                        vehicleNumberPlate = formState.vehicleNumberPlate,
                        onNumberPlateChange = onVehicleNumberPlateUpdate,
                        insurancePolicyNumber = formState.insurancePolicyNumber,
                        onPolicyNumberChange = onInsurancePolicyNumberUpdate,
                        insuranceValidTill = formState.insuranceValidTill,
                        onValidTillChange = onInsuranceValidTillUpdate
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Upload RC Copy Section
                    UploadDocumentSection(
                        title = "Upload RC Copy (PDF/JPEG)",
                        description = "Upload clear scan of RC for verification",
                        onUploadClick = { rcFileLauncher.launch("*/*") },
                        isUploaded = formState.uploadedRCPath != null
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Upload Insurance Copy Section
                    UploadDocumentSection(
                        title = "Upload Insurance Copy (PDF/JPEG)",
                        description = "Add active insurance to help Responders",
                        onUploadClick = { insuranceFileLauncher.launch("*/*") },
                        isUploaded = formState.uploadedInsurancePath != null
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Emergency Contact (Family) Section
                    EmergencyContactSection(
                        title = "Emergency Contact (Family)",
                        fullName = formState.emergencyContactFamilyName,
                        onNameChange = onEmergencyContactFamilyNameUpdate,
                        phoneNumber = formState.emergencyContactFamilyPhone,
                        onPhoneChange = onEmergencyContactFamilyPhoneUpdate
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Emergency Contact (Friend) Section
                    EmergencyContactSection(
                        title = "Emergency Contact (Friend)",
                        fullName = formState.emergencyContactFriendName,
                        onNameChange = onEmergencyContactFriendNameUpdate,
                        phoneNumber = formState.emergencyContactFriendPhone,
                        onPhoneChange = onEmergencyContactFriendPhoneUpdate
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Medical Condition Section
                    MedicalConditionSection(
                        selectedConditions = formState.selectedMedicalConditions,
                        onConditionToggle = onMedicalConditionToggle
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Footer text
                    Text(
                        text = "This info will be shown if someone scans your QR in an emergency.",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Next Button
                    Button(
                        onClick = onNextClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ==================== Composable Components ====================

@Composable
fun BackgroundBubbles() {
    Image(
        painter = painterResource(id = R.drawable.blue_background_with_bubbles),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // QR Code Icon
        Image(
            painter = painterResource(id = R.drawable.qr_code),
            contentDescription = "QR Code",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Title
        Text(
            text = "Generate QR Code",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        modifier = modifier
            .height(52.dp)
            .background(
                color = Color(0xFFE8F0FF).copy(alpha = 0.6f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun UploadPhotoSection(
    hasPhoto: Boolean,
    onTakeSelfieClick: () -> Unit,
    onChooseFromGalleryClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Upload Your Photo",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onTakeSelfieClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC))
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Selfie")
            }
            Button(
                onClick = onChooseFromGalleryClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066CC))
            ) {
                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gallery")
            }
        }
    }
}

@Composable
private fun VehicleTypeSection(
    selectedType: String?,
    onTypeSelected: (String) -> Unit
) {
    val types = listOf("2 Wheeler", "4 Wheeler", "Other")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Select Vehicle Type",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            types.forEach { type ->
                FilterChip(
                    selected = selectedType == type,
                    onClick = { onTypeSelected(type) },
                    label = { Text(type) }
                )
            }
        }
    }
}

@Composable
private fun VehicleDetailsSection(
    vehicleNumberPlate: String,
    onNumberPlateChange: (String) -> Unit,
    insurancePolicyNumber: String,
    onPolicyNumberChange: (String) -> Unit,
    insuranceValidTill: String,
    onValidTillChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CustomInputField(value = vehicleNumberPlate, onValueChange = onNumberPlateChange, placeholder = "Vehicle Number Plate")
        CustomInputField(value = insurancePolicyNumber, onValueChange = onPolicyNumberChange, placeholder = "Insurance Policy Number")
        CustomInputField(value = insuranceValidTill, onValueChange = onValidTillChange, placeholder = "Insurance Valid Till (DD/MM/YYYY)")
    }
}

@Composable
private fun UploadDocumentSection(
    title: String,
    description: String,
    onUploadClick: () -> Unit,
    isUploaded: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = description, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onUploadClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = if (isUploaded) Color.Green else Color(0xFF0066CC))
        ) {
            Text(if (isUploaded) "Uploaded" else "Upload")
        }
    }
}

@Composable
private fun EmergencyContactSection(
    title: String,
    fullName: String,
    onNameChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        CustomInputField(value = fullName, onValueChange = onNameChange, placeholder = "Full Name")
        CustomInputField(value = phoneNumber, onValueChange = onPhoneChange, placeholder = "Phone Number", keyboardType = KeyboardType.Phone)
    }
}

@Composable
private fun MedicalConditionSection(
    selectedConditions: Set<String>,
    onConditionToggle: (String) -> Unit
) {
    val conditions = listOf("Diabetes", "Hypertension", "Asthma", "Allergy", "None")
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Medical Conditions", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            conditions.forEach { condition ->
                FilterChip(
                    selected = selectedConditions.contains(condition),
                    onClick = { onConditionToggle(condition) },
                    label = { Text(condition) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenerateQRCodeScreen() {
    GenerateQRCodeContent(
        formState = QRCodeFormState(fullName = "John Doe"),
        onFullNameChange = {},
        onPhotoPathUpdate = {},
        onVehicleTypeUpdate = {},
        onVehicleNumberPlateUpdate = {},
        onInsurancePolicyNumberUpdate = {},
        onInsuranceValidTillUpdate = {},
        onRCPathUpdate = {},
        onInsurancePathUpdate = {},
        onEmergencyContactFamilyNameUpdate = {},
        onEmergencyContactFamilyPhoneUpdate = {},
        onEmergencyContactFriendNameUpdate = {},
        onEmergencyContactFriendPhoneUpdate = {},
        onMedicalConditionToggle = {},
        onBackClick = {},
        onNextClick = {}
    )
}
