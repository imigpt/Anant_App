package com.example.anantapp.ui.verify

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

// Color definitions
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val PurpleAccent = Color(0xFFC026D3)
private val MainBackground = Color(0xFFFAFAFA)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF888888)

@Composable
fun PhotoUploadScreen(
    viewModel: PhotoUploadViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Blink detection UI state
    var showCameraPreview by remember { mutableStateOf(false) }
    var blinkStatusMessage by remember { mutableStateOf("Blink your eyes 2 times to capture") }
    var currentBlinkCount by remember { mutableIntStateOf(0) }
    val requiredBlinks = 2  // Minimum blinks needed

    // Permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showCameraPreview = true
        } else {
            // Handle permission denied
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.selectPhoto(uri.toString())
            showCameraPreview = false
        }
    }

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
        BackgroundDecorationPhoto()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            PhotoUploadCard(
                uiState = uiState,
                showCameraPreview = showCameraPreview,
                blinkStatusMessage = blinkStatusMessage,
                currentBlinkCount = currentBlinkCount,
                requiredBlinks = requiredBlinks,
                onSkipClick = onSkipClick,
                onTakePhotoClick = {
                    val permissionCheck = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    )
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        showCameraPreview = true
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                onChoosePhotoClick = {
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onBlinkDetected = {
                    // Logic triggered when user blinks - will be called after 2 blinks
                    blinkStatusMessage = "Captured! ✓"
                    viewModel.selectPhoto("camera://blink_capture_${System.currentTimeMillis()}")
                    showCameraPreview = false
                },
                onBlinkCountChanged = { count ->
                    currentBlinkCount = count
                    blinkStatusMessage = "Blink $count/$requiredBlinks times"
                },
                onSubmitClick = viewModel::submitPhoto,
                lifecycleOwner = lifecycleOwner,
                context = context
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color(0xFFB0B0B0),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Your data stays private & encrypted.",
                    fontSize = 12.sp,
                    color = Color(0xFFB0B0B0)
                )
            }
        }

        if (uiState.isLoading) {
            LoadingOverlayPhoto()
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
private fun PhotoUploadCard(
    uiState: com.example.anantapp.data.model.PhotoUploadState,
    showCameraPreview: Boolean,
    blinkStatusMessage: String,
    currentBlinkCount: Int,
    requiredBlinks: Int,
    onSkipClick: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onChoosePhotoClick: () -> Unit,
    onBlinkDetected: () -> Unit,
    onBlinkCountChanged: (Int) -> Unit,
    onSubmitClick: () -> Unit,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    context: android.content.Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(32.dp))
            .background(Color.White, RoundedCornerShape(32.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(Icons.Filled.CameraAlt, contentDescription = null, modifier = Modifier.size(32.dp))
                TextButton(onClick = onSkipClick) {
                    Text("Skip >>", color = TextSecondary, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title & Subtitle
            Text(text = uiState.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "This keeps your profile verified as per banking KYC norms.",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Central Area: Camera Preview or Placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (showCameraPreview) {
                    // LIVE CAMERA PREVIEW WITH BLINK DETECTION
                    BlinkDetectionCameraPreview(
                        onBlinkDetected = onBlinkDetected,
                        onBlinkCountChanged = onBlinkCountChanged,
                        lifecycleOwner = lifecycleOwner,
                        context = context,
                        requiredBlinks = requiredBlinks
                    )
                } else if (uiState.isPhotoSelected) {
                    // Success View
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF6B9B25), modifier = Modifier.size(64.dp))
                        Text("Photo Selected ✓", color = Color(0xFF6B9B25), fontWeight = FontWeight.Bold)
                    }
                } else {
                    // Default View
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("😊", fontSize = 48.sp)
                        Text("Add a clear photo of yourself", color = TextSecondary, fontSize = 14.sp)
                    }
                }
            }

            if (showCameraPreview) {
                Text(
                    text = "📹 $blinkStatusMessage",
                    modifier = Modifier.padding(top = 12.dp),
                    color = Color(0xFFFF6300),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onTakePhotoClick,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeGradientStart)
                ) {
                    Icon(Icons.Filled.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Take Photo", fontSize = 13.sp)
                }

                Button(
                    onClick = onChoosePhotoClick,
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeGradientStart)
                ) {
                    Icon(Icons.Filled.Collections, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gallery", fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
            Button(
                onClick = onSubmitClick,
                enabled = uiState.isSubmitEnabled,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(27.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleAccent,
                    disabledContainerColor = Color(0xFFE0E0E0)
                )
            ) {
                Text("Submit Verification", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
private fun LoadingOverlayPhoto() {
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Composable
private fun BoxScope.BackgroundDecorationPhoto() {
    Box(
        modifier = Modifier
            .size(300.dp)
            .align(Alignment.TopEnd)
            .offset(x = 100.dp, y = (-50).dp)
            .background(Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), CircleShape)
    )
}
