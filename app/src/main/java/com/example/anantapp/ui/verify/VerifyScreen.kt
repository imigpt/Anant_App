package com.example.anantapp.ui.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Exact Color definitions
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val PurpleAccent = Color(0xFFC026D3)
private val PurpleBlobColor = Color(0xFFA142FF)
private val GreenGradientStart = Color(0xFF6B9B25)
private val GreenGradientEnd = Color(0xFFB5E453)
private val SuccessCheckColor = Color(0xFF6B9B25)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyScreen(
    viewModel: VerifyViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onVerifySuccess: () -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }

    var isSuccessScreen by remember { mutableStateOf(false) }

    // Bottom Sheet State
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var currentDocumentForUpload by remember { mutableStateOf("") }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            isSuccessScreen = true
            snackbarHostState.showSnackbar(uiState.successMessage)
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage)
            viewModel.clearMessages()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        BackgroundDecoration(isSuccess = isSuccessScreen)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            UploadCard(
                uiState = uiState,
                isSuccess = isSuccessScreen,
                onSkipClick = onSkipClick,
                onDocumentSelected = { documentName ->
                    currentDocumentForUpload = documentName
                    showBottomSheet = true
                },
                onSubmitClick = viewModel::submitVerification,
                onContinueClick = { onVerifySuccess() }
            )

            Spacer(modifier = Modifier.weight(1f))
            PrivacyFooter()
        }

        if (uiState.isLoading) LoadingOverlay()

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) { data ->
            Snackbar(modifier = Modifier.padding(bottom = 16.dp)) { Text(data.visuals.message) }
        }
    }

    // Bottom Sheet for Camera / Photo selection
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            UploadOptionsBottomSheet(
                documentName = currentDocumentForUpload,
                onOptionSelected = {
                    // Here you would normally launch the Camera or Photo Picker intent.
                    // For now, we simulate a successful upload instantly:
                    viewModel.markDocumentUploaded(currentDocumentForUpload)
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun UploadOptionsBottomSheet(
    documentName: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Upload $documentName",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Camera Option
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onOptionSelected("Camera") }
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFFFFF3E0), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Camera", tint = OrangeGradientStart, modifier = Modifier.size(32.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Camera", fontWeight = FontWeight.Medium)
            }

            // Photo Option
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { onOptionSelected("Photo") }
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color(0xFFE8F5E9), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.PhotoLibrary, contentDescription = "Photo", tint = GreenGradientStart, modifier = Modifier.size(32.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Photo", fontWeight = FontWeight.Medium)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun UploadCard(
    uiState: com.example.anantapp.data.model.VerifyState,
    isSuccess: Boolean,
    onSkipClick: () -> Unit = {},
    onDocumentSelected: (String) -> Unit = {},
    onSubmitClick: () -> Unit = {},
    onContinueClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                shape = RoundedCornerShape(32.dp),
                spotColor = Color.Black.copy(alpha = 0.15f),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
            .border(
                width = 1.5.dp,
                color = Color.White.copy(alpha = 0.4f),
                shape = RoundedCornerShape(32.dp)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White.copy(alpha = 0.6f), Color.White.copy(alpha = 0.3f))
                ),
                shape = RoundedCornerShape(32.dp)
            ),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isSuccess) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clickable { onSkipClick() }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Skip >>", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(28.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (isSuccess) SuccessIconSection() else UploadIconSection()

            Spacer(modifier = Modifier.height(40.dp))

            // Document options
            DocumentOptionsSection(
                uploadedDocuments = uiState.uploadedDocuments,
                isSuccess = isSuccess,
                onDocumentSelected = onDocumentSelected
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (isSuccess) {
                SolidGradientButton(text = "Continue", onClick = onContinueClick)
            } else {
                GradientBorderButton(
                    text = "Submit",
                    onClick = onSubmitClick,
                    isEnabled = uiState.isSubmitEnabled && !uiState.isLoading
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DocumentOptionsSection(
    uploadedDocuments: Set<String>,
    isSuccess: Boolean,
    onDocumentSelected: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DocumentButton(
                label = "PAN Card",
                icon = Icons.Filled.Assignment,
                isUploaded = uploadedDocuments.contains("PAN Card"),
                isSuccess = isSuccess,
                onClick = { onDocumentSelected("PAN Card") },
                modifier = Modifier.weight(1f)
            )

            DocumentButton(
                label = "Aadhaar",
                icon = Icons.Filled.Fingerprint,
                isUploaded = uploadedDocuments.contains("Aadhaar"),
                isSuccess = isSuccess,
                onClick = { onDocumentSelected("Aadhaar") },
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            DocumentButton(
                label = "Driving License",
                icon = Icons.Filled.CreditCard,
                isUploaded = uploadedDocuments.contains("Driving License"),
                isSuccess = isSuccess,
                onClick = { onDocumentSelected("Driving License") },
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}

@Composable
private fun DocumentButton(
    label: String,
    icon: ImageVector,
    isUploaded: Boolean,
    isSuccess: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // If it's the success screen, it's green. If it's just uploaded, it keeps its orange gradient but shows a checkmark
    val gradientColors = if (isSuccess) {
        listOf(GreenGradientStart, GreenGradientEnd)
    } else {
        listOf(OrangeGradientStart, OrangeGradientEnd)
    }

    Box(
        modifier = modifier
            .height(50.dp)
            .background(brush = Brush.linearGradient(colors = gradientColors), shape = RoundedCornerShape(50))
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(50), clip = false, spotColor = gradientColors.first().copy(alpha = 0.5f))
            // Show a white border if it is uploaded (to distinguish it on the upload screen)
            .border(width = if (isUploaded && !isSuccess) 2.dp else 0.dp, color = if (isUploaded) Color.White else Color.Transparent, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .clickable(enabled = !isSuccess) { onClick() }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(34.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Change icon to a checkmark if the user has uploaded it during the upload process
                Icon(
                    imageVector = if (isUploaded && !isSuccess) Icons.Filled.CheckCircle else icon,
                    contentDescription = label,
                    tint = if (isUploaded && !isSuccess) SuccessCheckColor else TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
        }
    }
}

@Composable
private fun LoadingOverlay() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.size(80.dp).background(Color.White, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = OrangeGradientStart, modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
private fun BoxScope.BackgroundDecoration(isSuccess: Boolean) {
    if (!isSuccess) {
        Box(modifier = Modifier.size(240.dp).align(Alignment.TopStart).offset((-40).dp, 80.dp).blur(40.dp).background(brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), shape = CircleShape))
        Box(modifier = Modifier.size(280.dp).align(Alignment.BottomEnd).offset(80.dp, (-80).dp).blur(40.dp).background(brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), shape = CircleShape))
    } else {
        Box(modifier = Modifier.size(220.dp).align(Alignment.TopStart).offset((-20).dp, 60.dp).blur(40.dp).background(PurpleBlobColor, CircleShape))
        Box(modifier = Modifier.size(80.dp).align(Alignment.TopEnd).offset((-40).dp, 80.dp).blur(20.dp).background(brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), shape = CircleShape))
        Box(modifier = Modifier.size(140.dp).align(Alignment.BottomStart).offset((-20).dp, (-180).dp).blur(30.dp).background(brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), shape = CircleShape))
    }
}

@Composable
private fun UploadIconSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Filled.Upload, contentDescription = "Upload", tint = TextPrimary, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Upload", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
private fun SuccessIconSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(80.dp).border(4.dp, SuccessCheckColor, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Check, contentDescription = "Success", tint = SuccessCheckColor, modifier = Modifier.size(48.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Successfully uploaded", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
    }
}

@Composable
private fun GradientBorderButton(text: String, onClick: () -> Unit, isEnabled: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(brush = Brush.linearGradient(listOf(PurpleAccent, OrangeGradientStart)), shape = RoundedCornerShape(50))
            .padding(2.dp)
            .background(if (isEnabled) Color.White else Color(0xFFF0F0F0), RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isEnabled) TextPrimary else TextSecondary)
    }
}

@Composable
private fun SolidGradientButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(brush = Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
private fun PrivacyFooter() {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Lock, contentDescription = "Secure", tint = Color(0xFFB0B0B0), modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("Your data stays private & encrypted.", fontSize = 12.sp, color = Color(0xFFB0B0B0))
    }
}