package com.example.anantapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.UserDetailsViewModel

// ==================== Data Classes ====================

data class UserDetailsFormState(
    val userName: String = "",
    val userPhotoPath: String? = null,
    val selectedVehicleType: String? = null,
    val vehicleNumberPlate: String = "",
    val insurancePolicyNumber: String = "",
    val insuranceValidTill: String = "",
    val rcDocumentPath: String? = null,
    val insuranceDocumentPath: String? = null,
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val emergencyContactAddress1: String = "",
    val emergencyContactAddress2: String = "",
    val bloodType: String = "",
    val selectedMedicalConditions: Set<String> = emptySet()
)

// ==================== Main Screen ====================

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onNextClick: (UserDetailsFormState) -> Unit = {}
) {
    val formState by viewModel.formState.collectAsState()
    
    UserDetailsContent(
        formState = formState,
        onUserNameChange = { viewModel.updateUserName(it) },
        onVehicleNumberPlateChange = { viewModel.updateVehicleNumberPlate(it) },
        onInsurancePolicyNumberChange = { viewModel.updateInsurancePolicyNumber(it) },
        onInsuranceValidTillChange = { viewModel.updateInsuranceValidTill(it) },
        onEmergencyContactNameChange = { viewModel.updateEmergencyContactName(it) },
        onEmergencyContactPhoneChange = { viewModel.updateEmergencyContactPhone(it) },
        onEmergencyContactAddress1Change = { viewModel.updateEmergencyContactAddress1(it) },
        onEmergencyContactAddress2Change = { viewModel.updateEmergencyContactAddress2(it) },
        onBloodTypeChange = { viewModel.updateBloodType(it) },
        onMedicalConditionToggle = { viewModel.toggleMedicalCondition(it) },
        onBackClick = onBackClick,
        onNextClick = { onNextClick(viewModel.getFormData()) }
    )
}

@Composable
fun UserDetailsContent(
    formState: UserDetailsFormState = UserDetailsFormState(),
    onUserNameChange: (String) -> Unit = {},
    onVehicleNumberPlateChange: (String) -> Unit = {},
    onInsurancePolicyNumberChange: (String) -> Unit = {},
    onInsuranceValidTillChange: (String) -> Unit = {},
    onEmergencyContactNameChange: (String) -> Unit = {},
    onEmergencyContactPhoneChange: (String) -> Unit = {},
    onEmergencyContactAddress1Change: (String) -> Unit = {},
    onEmergencyContactAddress2Change: (String) -> Unit = {},
    onBloodTypeChange: (String) -> Unit = {},
    onMedicalConditionToggle: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.blue_background_with_bubbles),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Semi-transparent blue overlay
        Box(modifier = Modifier.fillMaxSize().background(Color.Blue.copy(alpha = 0.1f)))

        // Main Glassmorphic Card Container
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .background(
                    color = Color.White.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Profile Icon & Title
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(56.dp),
                    tint = Color.Black
                )

                Text(
                    text = "User Details",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Name Field
                GlassInputField(
                    value = formState.userName,
                    onValueChange = onUserNameChange,
                    placeholder = "Full Name"
                )

                // User Photo Section
                SectionLabel("User Photo")
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFFD9D9D9), CircleShape)
                    )
                }

                // Vehicle Type Section
                SectionLabel("Vehicle Type:")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GlassChip(text = "2 Wheeler", isSelected = formState.selectedVehicleType == "2 Wheeler", modifier = Modifier.weight(1f))
                        GlassChip(text = "3 Wheeler Auto", isSelected = formState.selectedVehicleType == "3 Wheeler Auto", modifier = Modifier.weight(1f))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GlassChip(text = "Other", isSelected = formState.selectedVehicleType == "Other", modifier = Modifier.weight(1f))
                        GlassChip(text = "4 Wheeler Car", isSelected = formState.selectedVehicleType == "4 Wheeler Car", modifier = Modifier.weight(1f))
                    }
                }

                // Vehicle Details Section
                SectionLabel("Vehicle Details:")
                GlassInputField(value = formState.vehicleNumberPlate, onValueChange = onVehicleNumberPlateChange, placeholder = "Vehicle Number Plate")
                GlassInputField(value = formState.insurancePolicyNumber, onValueChange = onInsurancePolicyNumberChange, placeholder = "Insurance Policy Number")
                GlassInputField(value = formState.insuranceValidTill, onValueChange = onInsuranceValidTillChange, placeholder = "Insurance Valid Till")
                GlassInputFieldWithIcon(value = formState.insuranceValidTill, placeholder = "Expiry Date")

                // Upload RC Copy
                SectionLabel("Upload RC Copy (PDF/JPEG)")
                DocumentUploadBox()

                // Upload Insurance Copy
                SectionLabel("Upload Insurance Copy (PDF/JPEG)")
                DocumentUploadBox()

                // Emergency Contact Section
                SectionLabel("Emergency Contact")
                GlassInputField(value = formState.emergencyContactName, onValueChange = onEmergencyContactNameChange, placeholder = "Contact Name")
                GlassInputField(value = formState.emergencyContactPhone, onValueChange = onEmergencyContactPhoneChange, placeholder = "Phone Number")
                Spacer(modifier = Modifier.height(4.dp))
                GlassInputField(value = formState.emergencyContactAddress1, onValueChange = onEmergencyContactAddress1Change, placeholder = "Address Line 1")
                GlassInputField(value = formState.emergencyContactAddress2, onValueChange = onEmergencyContactAddress2Change, placeholder = "Address Line 2")
                GlassInputField(value = formState.bloodType, onValueChange = onBloodTypeChange, placeholder = "Blood Group")

                // Medical Conditions Section
                SectionLabel("Any Medical Condition")
                MedicalConditionsGrid(
                    selectedConditions = formState.selectedMedicalConditions,
                    onToggle = onMedicalConditionToggle
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Footer Text
                Text(
                    text = "This info will be shown if someone\nscans your QR in an emergency.",
                    fontSize = 11.sp,
                    color = Color(0xFF555555),
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Action Buttons Bottom Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onBackClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Back", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = onNextClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.6f)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Next", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// ==================== Reusable UI Components ====================

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF333333),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
}

@Composable
fun GlassInputField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (value.isEmpty()) {
            Text(text = placeholder, fontSize = 14.sp, color = Color(0xFF444444).copy(alpha = 0.6f))
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = Color(0xFF444444))
        )
    }
}

@Composable
fun GlassInputFieldWithIcon(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (value.isEmpty()) {
                Text(text = placeholder, fontSize = 14.sp, color = Color(0xFF444444).copy(alpha = 0.6f))
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = Color(0xFF444444))
            )
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Date",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun GlassChip(text: String, isSelected: Boolean, modifier: Modifier = Modifier) {
    val bgColor = if (isSelected) Color(0xFF3B82F6) else Color.White.copy(alpha = 0.5f)
    val textColor = if (isSelected) Color.White else Color(0xFF555555)

    Box(
        modifier = modifier
            .height(42.dp)
            .shadow(if (isSelected) 6.dp else 0.dp, RoundedCornerShape(24.dp), spotColor = Color.Blue)
            .background(bgColor, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DocumentUploadBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
            )
        }

        Button(
            onClick = { /* Handle Upload */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-4).dp)
                .height(34.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text("Open Document", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun MedicalConditionsGrid(
    selectedConditions: Set<String> = emptySet(),
    onToggle: (String) -> Unit = {}
) {
    val conditions = listOf(
        "Diabetes", "High Blood Pressure",
        "Asthma", "Heart Condition",
        "Epilepsy/Seizures", "Severe Allergies",
        "Organ Transplant", "Blood Clotting Disorder",
        "Currently Pregnant", "Other"
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        conditions.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val cond1 = row[0]
                GlassChip(
                    text = cond1,
                    isSelected = selectedConditions.contains(cond1),
                    modifier = Modifier.weight(1f).clickable { onToggle(cond1) }
                )
                if (row.size > 1) {
                    val cond2 = row[1]
                    GlassChip(
                        text = cond2,
                        isSelected = selectedConditions.contains(cond2),
                        modifier = Modifier.weight(1f).clickable { onToggle(cond2) }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserDetailsScreen() {
    UserDetailsContent()
}
