package com.example.anantapp.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.R
import com.example.anantapp.presentation.viewmodel.CreateFundraiserViewModel
import com.example.anantapp.ui.components.FormLabel
import com.example.anantapp.ui.components.RequiredFormLabel
import com.example.anantapp.ui.theme.AnantAppTheme

data class CountryData(val name: String, val code: String, val flag: String, val dialCode: String)

@Composable
fun CreateFundraiserScreen(
    viewModel: CreateFundraiserViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onDraftSaved: () -> Unit = {},
    onFundraiserCreated: (fundraiserId: String) -> Unit = {},
    onNavigateToTargetPayments: () -> Unit = {}
) {
    // Step State: 1 = Create Fundraiser, 2 = Beneficiary Information, 3 = Upload Documents
    var currentStep by remember { mutableIntStateOf(1) }

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
        Crossfade(targetState = currentStep, label = "Step Transition") { step ->
            when (step) {
                1 -> StepOneCreateFundraiser(
                    viewModel = viewModel,
                    mainGradient = mainGradient,
                    onBackClick = onBackClick,
                    onDraftSaved = onDraftSaved,
                    onNextClick = { currentStep = 2 } // Proceed to Step 2
                )
                2 -> StepTwoBeneficiaryInfo(
                    mainGradient = mainGradient,
                    onBackClick = { currentStep = 1 }, // Return to Step 1
                    onDraftSaved = onDraftSaved,
                    onNextClick = { currentStep = 3 } // Proceed to Step 3
                )
                3 -> StepThreeUploadDocuments(
                    mainGradient = mainGradient,
                    onBackClick = { currentStep = 2 }, // Return to Step 2
                    onDraftSaved = onDraftSaved,
                    onNextClick = { 
                        onFundraiserCreated("${System.currentTimeMillis()}")
                        // Navigate to Target & Payments screen
                        onNavigateToTargetPayments()
                    }
                )
            }
        }
    }
}

@Composable
fun StepOneCreateFundraiser(
    viewModel: CreateFundraiserViewModel,
    mainGradient: Brush,
    onBackClick: () -> Unit,
    onDraftSaved: () -> Unit,
    onNextClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showCountryDropdown by remember { mutableStateOf(false) }
    
    // Upload photo states
    var uploadedPhotos by remember { mutableStateOf<List<String>>(emptyList()) }

    val categories = listOf("Health", "Accident Relief", "Death Support", "Education", "Orphan Care", "Other")
    val countries = listOf(
        CountryData("India", "+91", "🇮🇳", "+91"),
        CountryData("USA", "+1", "🇺🇸", "+1"),
        CountryData("UK", "+44", "🇬🇧", "+44")
    )
    var selectedCountry by remember { mutableStateOf(countries[0]) }
    
    // File picker launcher for gallery photos
    val photoGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> 
            uri?.let { 
                uploadedPhotos = uploadedPhotos + it.toString()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        ScreenHeader(title = "Create new Fundraiser", onBackClick = onBackClick)

        // Hero Image
        Image(
            painter = painterResource(id = R.drawable.people_volunteer_img),
            contentDescription = "Volunteers",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Photo Gallery Thumbnails
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { 
                UploadPhotoButton(gradient = mainGradient) { 
                    photoGalleryLauncher.launch("image/*")
                } 
            }

            items(uploadedPhotos.size) { index ->
                Box(modifier = Modifier.wrapContentSize()) {
                    // Placeholder box since we don't have direct image rendering from URI
                    // In production, use Coil or another image library
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE0E0E0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Photo\n${index + 1}", fontSize = 12.sp, textAlign = TextAlign.Center, color = Color.Gray)
                    }
                    
                    // Remove button
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Remove Photo",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = (-8).dp)
                            .size(24.dp)
                            .background(Color.Red, CircleShape)
                            .padding(4.dp)
                            .clickable {
                                uploadedPhotos = uploadedPhotos.filterIndexed { i, _ -> i != index }
                            },
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Form Content
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Fundraiser details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Fundraiser Title")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(brush = mainGradient, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = uiState.title.ifEmpty { "Help Mohan fight Cancer" },
                    onValueChange = { viewModel.updateTitle(it) },
                    textStyle = TextStyle(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Short Description")
            CustomOutlinedInput(
                value = uiState.shortDescription,
                onValueChange = { viewModel.updateShortDescription(it) },
                placeholder = "Mohan needs urgent chemother..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Full Story")
            CustomOutlinedInput(
                value = uiState.fullStory,
                onValueChange = { viewModel.updateFullStory(it) },
                placeholder = "Explain situation, why help is needed.",
                height = 120.dp,
                singleLine = false
            )

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Category")
            Box {
                CustomOutlinedInput(
                    value = uiState.selectedCategory.ifEmpty { "Select Category" },
                    onValueChange = {},
                    placeholder = "Select Category",
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.clickable { showCategoryDropdown = true }
                        )
                    }
                )

                DropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category, fontSize = 16.sp) },
                            onClick = {
                                viewModel.updateCategory(category)
                                showCategoryDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Enter Contact Details")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.wrapContentSize().clickable { showCountryDropdown = true }, contentAlignment = Alignment.Center) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                        Text(text = selectedCountry.flag, fontSize = 20.sp)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                    }
                    DropdownMenu(expanded = showCountryDropdown, onDismissRequest = { showCountryDropdown = false }) {
                        countries.forEach { country ->
                            DropdownMenuItem(
                                text = { Text("${country.flag} ${country.name} ${country.code}", fontSize = 14.sp) },
                                onClick = {
                                    selectedCountry = country
                                    viewModel.updateCountryCode(country.code)
                                    showCountryDropdown = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE0E0E0)))
                Spacer(modifier = Modifier.width(12.dp))

                Text(text = selectedCountry.dialCode, color = Color.Gray, fontSize = 16.sp)
                BasicTextField(
                    value = uiState.phoneNumber,
                    onValueChange = { viewModel.updatePhoneNumber(it) },
                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        if (uiState.phoneNumber.isEmpty()) Text("0000000000", color = Color.Gray, fontSize = 16.sp)
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Be honest and clear. Verified stories build trust.",
                fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            BottomActionButtons(
                mainGradient = mainGradient,
                onDraftClick = { viewModel.saveDraft(); onDraftSaved() },
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
fun StepTwoBeneficiaryInfo(
    mainGradient: Brush,
    onBackClick: () -> Unit,
    onDraftSaved: () -> Unit,
    onNextClick: () -> Unit
) {
    // Local state for Step 2 inputs
    var fullName by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var nomineeName by remember { mutableStateOf("") }
    var nomineeAge by remember { mutableStateOf("") }
    var nomineeContact by remember { mutableStateOf("") }
    
    // Upload states
    var photoUri by remember { mutableStateOf<String?>(null) }
    var aadhaarFrontUri by remember { mutableStateOf<String?>(null) }
    var aadhaarBackUri by remember { mutableStateOf<String?>(null) }
    
    // File picker launchers
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> photoUri = uri?.toString() }
    )
    
    val aadhaarFrontLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> aadhaarFrontUri = uri?.toString() }
    )
    
    val aadhaarBackLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> aadhaarBackUri = uri?.toString() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        ScreenHeader(title = "Beneficiary Information", onBackClick = onBackClick)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            // Standard Inputs
            FormLabel("Full Name (as per Govt ID)")
            CustomOutlinedInput(value = fullName, onValueChange = { fullName = it }, placeholder = "Enter Name")
            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Relation to Fundraiser Creator")
            CustomOutlinedInput(value = relation, onValueChange = { relation = it }, placeholder = "Father, Mother, Friend, Etc...")
            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Age")
            CustomOutlinedInput(value = age, onValueChange = { age = it }, placeholder = "Enter Age", keyboardType = KeyboardType.Number)
            Spacer(modifier = Modifier.height(24.dp))

            // Photo Upload with Status
            FormLabel("Photo Upload")
            LargeUploadBox(
                icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFFAAAAAA)) },
                helperText = if (photoUri != null) "✓ Photo Uploaded" else "Use a clear, recent photo. No cap or sunglasses",
                isUploaded = photoUri != null,
                onClick = { photoPickerLauncher.launch("image/*") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Aadhaar Upload (Front)")
            LargeUploadBox(
                icon = { AadhaarIconWithShield() },
                helperText = if (aadhaarFrontUri != null) "✓ Aadhaar Front Uploaded" else "Upload clear front side of your Aadhaar card",
                isUploaded = aadhaarFrontUri != null,
                onClick = { aadhaarFrontLauncher.launch("image/*") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            FormLabel("Aadhaar Upload (Back)")
            LargeUploadBox(
                icon = { AadhaarIconWithShield() },
                helperText = if (aadhaarBackUri != null) "✓ Aadhaar Back Uploaded" else "Upload clear back side of your Aadhaar card",
                isUploaded = aadhaarBackUri != null,
                onClick = { aadhaarBackLauncher.launch("image/*") }
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Nominee Group
            FormLabel("Nominee Details (optional)")
            CustomOutlinedInput(value = nomineeName, onValueChange = { nomineeName = it }, placeholder = "Enter Name")
            Spacer(modifier = Modifier.height(12.dp))
            CustomOutlinedInput(value = nomineeAge, onValueChange = { nomineeAge = it }, placeholder = "Enter Age", keyboardType = KeyboardType.Number)
            Spacer(modifier = Modifier.height(12.dp))
            CustomOutlinedInput(value = nomineeContact, onValueChange = { nomineeContact = it }, placeholder = "Enter Contact Detail")

            Spacer(modifier = Modifier.height(32.dp))

            BottomActionButtons(
                mainGradient = mainGradient,
                onDraftClick = onDraftSaved,
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
fun StepThreeUploadDocuments(
    mainGradient: Brush,
    onBackClick: () -> Unit,
    onDraftSaved: () -> Unit,
    onNextClick: () -> Unit
) {
    // Upload states for documents - now supporting multiple files
    var medicalReportsUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var prescriptionUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var invoiceUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var deathCertificateUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var patientFamilyPhotoUris by remember { mutableStateOf<List<String>>(emptyList()) }
    var policeReportUris by remember { mutableStateOf<List<String>>(emptyList()) }
    
    // File picker launchers for multiple files
    val medicalReportsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> medicalReportsUris = medicalReportsUris + uris.map { it.toString() } }
    )
    
    val prescriptionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> prescriptionUris = prescriptionUris + uris.map { it.toString() } }
    )
    
    val invoiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> invoiceUris = invoiceUris + uris.map { it.toString() } }
    )
    
    val deathCertificateLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> deathCertificateUris = deathCertificateUris + uris.map { it.toString() } }
    )
    
    val patientPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> patientFamilyPhotoUris = patientFamilyPhotoUris + uris.map { it.toString() } }
    )
    
    val policeReportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> policeReportUris = policeReportUris + uris.map { it.toString() } }
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        ScreenHeader(title = "Upload Supporting Documents", onBackClick = onBackClick)

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            RequiredFormLabel("Medical Reports (PDF/JPEG)")
            MultiDocumentUploadBox(
                icon = { DocumentWithAttachmentIcon() },
                helperText = "Upload clear scans of test reports. You can upload multiple files.",
                uploadedUris = medicalReportsUris,
                onUploadClick = { medicalReportsLauncher.launch("*/*") },
                onRemoveFile = { index -> medicalReportsUris = medicalReportsUris.filterIndexed { i, _ -> i != index } }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequiredFormLabel("Doctor's Prescription")
            MultiDocumentUploadBox(
                icon = { Icon(Icons.Outlined.Article, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFFAAAAAA)) },
                helperText = "Add signed prescription for medical proof. Upload multiple if needed.",
                uploadedUris = prescriptionUris,
                onUploadClick = { prescriptionLauncher.launch("*/*") },
                onRemoveFile = { index -> prescriptionUris = prescriptionUris.filterIndexed { i, _ -> i != index } }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequiredFormLabel("Hospital Estimate / Invoice")
            MultiDocumentUploadBox(
                icon = { Icon(Icons.Outlined.Receipt, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFFAAAAAA)) },
                helperText = "Attach hospital estimate or bill copy. Multiple documents supported.",
                uploadedUris = invoiceUris,
                onUploadClick = { invoiceLauncher.launch("*/*") },
                onRemoveFile = { index -> invoiceUris = invoiceUris.filterIndexed { i, _ -> i != index } }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequiredFormLabel("Death Certificate (if applicable)")
            MultiDocumentUploadBox(
                icon = { DocumentWithEyeIcon() },
                helperText = "Upload official death certificate for verification. Multiple copies allowed.",
                uploadedUris = deathCertificateUris,
                onUploadClick = { deathCertificateLauncher.launch("*/*") },
                onRemoveFile = { index -> deathCertificateUris = deathCertificateUris.filterIndexed { i, _ -> i != index } }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequiredFormLabel("Photo of Patient/Family")
            MultiDocumentUploadBox(
                icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color(0xFFAAAAAA)) },
                helperText = "Add clear photos to build trust with donors. Upload multiple photos.",
                uploadedUris = patientFamilyPhotoUris,
                onUploadClick = { patientPhotoLauncher.launch("image/*") },
                onRemoveFile = { index -> patientFamilyPhotoUris = patientFamilyPhotoUris.filterIndexed { i, _ -> i != index } }
            )
            Spacer(modifier = Modifier.height(16.dp))

            RequiredFormLabel("Any Extra (Police Report for Accident)", isRequired = false)
            MultiDocumentUploadBox(
                icon = { DocumentWithArrowIcon() },
                helperText = "If accident case, upload FIR or police report. Multiple documents accepted.",
                uploadedUris = policeReportUris,
                onUploadClick = { policeReportLauncher.launch("*/*") },
                onRemoveFile = { index -> policeReportUris = policeReportUris.filterIndexed { i, _ -> i != index } }
            )

            Spacer(modifier = Modifier.height(32.dp))

            BottomActionButtons(
                mainGradient = mainGradient,
                onDraftClick = onDraftSaved,
                onNextClick = onNextClick
            )
        }
    }
}

// --- REUSABLE COMPONENTS ---

@Composable
fun ScreenHeader(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onBackClick() },
            tint = Color(0xFF1A1A2E)
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A2E),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f).offset(x = (-12).dp)
        )
    }
}

@Composable
fun CustomOutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: androidx.compose.ui.unit.Dp = 52.dp,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = if (singleLine) 0.dp else 16.dp),
        contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                readOnly = readOnly,
                textStyle = TextStyle(color = Color(0xFF333333), fontSize = 16.sp),
                singleLine = singleLine,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) Text(placeholder, color = Color.Gray, fontSize = 16.sp)
                    innerTextField()
                }
            )
            trailingIcon?.invoke()
        }
    }
}

@Composable
fun LargeUploadBox(
    icon: @Composable () -> Unit,
    helperText: String,
    isUploaded: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .border(
                width = 1.dp,
                color = if (isUploaded) Color(0xFF9500FF) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isUploaded) Color(0xFF9500FF).copy(alpha = 0.05f) else Color.White
            )
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            icon()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = helperText,
                fontSize = 12.sp,
                color = if (isUploaded) Color(0xFF9500FF) else Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = if (isUploaded) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun MultiDocumentUploadBox(
    icon: @Composable () -> Unit,
    helperText: String,
    uploadedUris: List<String>,
    onUploadClick: () -> Unit,
    onRemoveFile: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Upload Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .border(
                    width = 1.dp,
                    color = if (uploadedUris.isNotEmpty()) Color(0xFF9500FF) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color = if (uploadedUris.isNotEmpty()) Color(0xFF9500FF).copy(alpha = 0.05f) else Color.White
                )
                .clickable { onUploadClick() }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                icon()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = helperText,
                    fontSize = 12.sp,
                    color = if (uploadedUris.isNotEmpty()) Color(0xFF9500FF) else Color.Gray,
                    textAlign = TextAlign.Center,
                    fontWeight = if (uploadedUris.isNotEmpty()) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }

        // Display uploaded files list
        if (uploadedUris.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF9500FF).copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "✓ ${uploadedUris.size} file(s) uploaded",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF9500FF),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                uploadedUris.forEachIndexed { index, uri ->
                    DocumentFileItem(
                        fileName = "Document ${index + 1}",
                        fileUri = uri,
                        onRemove = { onRemoveFile(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun DocumentFileItem(
    fileName: String,
    fileUri: String,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(6.dp))
            .border(0.5.dp, Color(0xFFE0E0E0), RoundedCornerShape(6.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Article,
                contentDescription = "Document",
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF9500FF)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fileName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    maxLines = 1
                )
                Text(
                    text = fileUri.takeLast(30),
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }

        // Remove button
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Remove",
            modifier = Modifier
                .size(20.dp)
                .clickable { onRemove() },
            tint = Color(0xFFFF2F4B)
        )
    }
}

// Custom Icon Composables to match the design references

@Composable
fun AadhaarIconWithShield() {
    Box(modifier = Modifier.size(64.dp)) {
        Icon(
            imageVector = Icons.Outlined.CreditCard,
            contentDescription = null,
            modifier = Modifier.size(56.dp).align(Alignment.TopStart),
            tint = Color(0xFFAAAAAA)
        )
        Icon(
            imageVector = Icons.Outlined.Security,
            contentDescription = null,
            modifier = Modifier.size(28.dp).align(Alignment.BottomEnd)
                .background(Color.White, CircleShape), // Cutout effect
            tint = Color(0xFFAAAAAA)
        )
    }
}

@Composable
fun DocumentWithAttachmentIcon() {
    Box(modifier = Modifier.size(64.dp)) {
        Icon(
            imageVector = Icons.Outlined.Article,
            contentDescription = null,
            modifier = Modifier.size(56.dp).align(Alignment.TopStart),
            tint = Color(0xFFAAAAAA)
        )
        Icon(
            imageVector = Icons.Outlined.AttachFile,
            contentDescription = null,
            modifier = Modifier.size(28.dp).align(Alignment.BottomEnd)
                .background(Color.White, CircleShape),
            tint = Color(0xFFAAAAAA)
        )
    }
}

@Composable
fun DocumentWithEyeIcon() {
    Box(modifier = Modifier.size(64.dp)) {
        Icon(
            imageVector = Icons.Outlined.Article,
            contentDescription = null,
            modifier = Modifier.size(56.dp).align(Alignment.TopStart),
            tint = Color(0xFFAAAAAA)
        )
        Icon(
            imageVector = Icons.Outlined.Visibility,
            contentDescription = null,
            modifier = Modifier.size(28.dp).align(Alignment.BottomEnd)
                .background(Color.White, CircleShape),
            tint = Color(0xFFAAAAAA)
        )
    }
}

@Composable
fun DocumentWithArrowIcon() {
    Box(modifier = Modifier.size(64.dp)) {
        Icon(
            imageVector = Icons.Outlined.Article,
            contentDescription = null,
            modifier = Modifier.size(56.dp).align(Alignment.TopStart),
            tint = Color(0xFFAAAAAA)
        )
        Icon(
            imageVector = Icons.Outlined.ArrowDownward,
            contentDescription = null,
            modifier = Modifier.size(24.dp).align(Alignment.BottomEnd)
                .background(Color.White, CircleShape),
            tint = Color(0xFFAAAAAA)
        )
    }
}

@Composable
fun BottomActionButtons(
    mainGradient: Brush,
    onDraftClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .border(1.5.dp, mainGradient, CircleShape)
                .clip(CircleShape)
                .clickable { onDraftClick() },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Bookmark, contentDescription = "Draft", tint = Color(0xFF9500FF), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Draft", fontWeight = FontWeight.Bold, color = Color(0xFF333333), fontSize = 16.sp)
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
                .background(mainGradient, CircleShape)
                .clip(CircleShape)
                .clickable { onNextClick() },
            contentAlignment = Alignment.Center
        ) {
            Text("Next", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun UploadPhotoButton(gradient: Brush, onClick: () -> Unit) {
    val stroke = Stroke(width = 4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f))
    Box(
        modifier = Modifier
            .size(90.dp)
            .drawBehind {
                drawRoundRect(brush = gradient, style = stroke, cornerRadius = CornerRadius(36f, 36f))
            }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Add, contentDescription = "Upload", tint = Color(0xFF1A1A2E), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text("Upload\nfoto", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateFundraiserScreenPreview() {
    AnantAppTheme {
        CreateFundraiserScreen()
    }
}