package com.example.anantapp.feature.verify.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.feature.verify.presentation.viewmodel.VerifyViewModel

private val PurpleAccent = Color(0xFF9500FF)
private val RedAccent = Color(0xFFFF6264)
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
    var showBottomSheet by remember { mutableStateOf(false) }
    var isSuccessScreen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            isSuccessScreen = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackground)
    ) {
        BackgroundDecoration(isSuccessScreen)

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

                if (!isSuccessScreen) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Assignment,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = PurpleAccent
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Document Verification",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Upload your KYC documents for verification",
                        fontSize = 14.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Document Types
                    DocumentTypeSection {
                        showBottomSheet = true
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Uploaded Documents List
                    if (uiState.uploadedDocuments.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.uploadedDocuments.forEach { doc ->
                                UploadedDocumentCard(doc) {
                                    viewModel.removeDocument(doc.id)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Submit Button
                    GradientButton(
                        text = "Verify Documents",
                        onClick = { viewModel.submitVerification() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
                } else {
                    SuccessContent(onVerifySuccess)
                }
            }
        }

        if (uiState.isLoading) {
            LoadingOverlay()
        }

        SnackbarHost(snackbarHostState)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            UploadOptionsBottomSheet(
                onOptionSelected = { documentType ->
                    viewModel.selectDocument(documentType)
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun DocumentTypeSection(onUploadClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf("Aadhar Card", "PAN Card", "Passport", "Driving License").forEach { doc ->
            DocumentButton(doc) { onUploadClick() }
        }
    }
}

@Composable
private fun DocumentButton(
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(listOf(PurpleAccent, RedAccent)),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Icon(
                imageVector = Icons.Filled.Upload,
                contentDescription = null,
                tint = PurpleAccent,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun UploadedDocumentCard(
    document: com.example.anantapp.feature.verify.presentation.viewmodel.DocumentUpload,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = document.documentType,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = document.status.uppercase(),
                    fontSize = 12.sp,
                    color = if (document.status == "approved") Color.Green else Color(0xFFFFA500)
                )
            }
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRemove() },
                tint = PurpleAccent
            )
        }
    }
}

@Composable
private fun UploadOptionsBottomSheet(
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Upload Document",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        UploadOptionItem(Icons.Filled.CameraAlt, "Take Photo") {
            onOptionSelected("Camera")
        }
        
        UploadOptionItem(Icons.Filled.PhotoLibrary, "Choose from Gallery") {
            onOptionSelected("Gallery")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun UploadOptionItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = PurpleAccent
        )
        Text(text = label, fontSize = 16.sp)
    }
}

@Composable
private fun ColumnScope.SuccessContent(onSuccess: () -> Unit) {
    // Replaced weight with fixed Spacer for scrollable Column compatibility
    Spacer(modifier = Modifier.height(64.dp))

    Icon(
        imageVector = Icons.Filled.Check,
        contentDescription = null,
        modifier = Modifier
            .size(80.dp)
            .background(Color(0xFF6B9B25), CircleShape)
            .padding(12.dp),
        tint = Color.White
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Documents Verified!",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Your documents have been successfully verified",
        fontSize = 14.sp,
        color = TextSecondary,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(64.dp))

    GradientButton(
        text = "Continue",
        onClick = { onSuccess() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}

@Composable
private fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(PurpleAccent, RedAccent)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = PurpleAccent,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun BoxScope.BackgroundDecoration(isSuccess: Boolean) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        if (isSuccess) Color(0xFF6B9B25).copy(alpha = 0.1f) else PurpleAccent.copy(alpha = 0.1f),
                        Color.Transparent
                    )
                ),
                shape = CircleShape
            )
            .align(Alignment.TopEnd)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyScreenPreview() {
    VerifyScreen()
}
