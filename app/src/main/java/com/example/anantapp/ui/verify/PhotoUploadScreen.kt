package com.example.anantapp.ui.verify

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anantapp.data.model.PhotoUploadState
import java.io.File

// Exact colors mapped from the provided UI image
private val OrangeGradientStart = Color(0xFFFF6300)
private val OrangeGradientEnd = Color(0xFFFFCF11)
private val MainBackground = Color(0xFFF6F6F6)
private val TextPrimary = Color(0xFF000000)
private val TextSecondary = Color(0xFF666666)
private val GradientBorderColors = listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121))

@Composable
fun PhotoUploadScreen(
    viewModel: PhotoUploadViewModel = viewModel(),
    onSkipClick: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Create a temporary file URI for camera capture
    val cameraImageUri = remember {
        val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    // Camera launcher - captures photo directly
    // Must be declared before cameraPermissionLauncher to be accessible in the lambda
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            viewModel.selectPhoto(cameraImageUri.toString())
        }
    }

    // Permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            takePictureLauncher.launch(cameraImageUri)
        }
    }

    // Gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.selectPhoto(uri.toString())
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

    PhotoUploadContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSkipClick = onSkipClick,
        onTakePhotoClick = {
            val permissionCheck = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                takePictureLauncher.launch(cameraImageUri)
            } else {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
        onChoosePhotoClick = {
            galleryLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onSubmitClick = viewModel::submitPhoto
    )
}

@Composable
private fun PhotoUploadContent(
    uiState: PhotoUploadState,
    snackbarHostState: SnackbarHostState,
    onSkipClick: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onChoosePhotoClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
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
                .padding(horizontal = 20.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PhotoUploadCard(
                uiState = uiState,
                onSkipClick = onSkipClick,
                onTakePhotoClick = onTakePhotoClick,
                onChoosePhotoClick = onChoosePhotoClick,
                onSubmitClick = onSubmitClick
            )
        }

        if (uiState.isLoading) {
            LoadingOverlayPhoto()
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun PhotoUploadCard(
    uiState: PhotoUploadState,
    onSkipClick: () -> Unit,
    onTakePhotoClick: () -> Unit,
    onChoosePhotoClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    // Glassmorphism Card
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(32.dp), spotColor = Color.LightGray.copy(alpha = 0.5f))
            .clip(RoundedCornerShape(32.dp))
            .background(Color.White.copy(alpha = 0.85f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Header Row: Centered Camera Icon & Top-Right Skip Button
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = "Camera",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                    tint = TextPrimary
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onSkipClick() }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Skip >>", color = TextSecondary, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title & Subtitle
            Text(
                text = "Upload Your Photo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This keeps your profile verified as\nper banking KYC norms.",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Central Area: Dashed Background Box
            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFD0D0D0),
                            style = Stroke(width = 0.3f, pathEffect = dashEffect),
                            cornerRadius = CornerRadius(24.dp.toPx())
                        )
                    }
                    .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isPhotoSelected) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF6B9B25), modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Photo Selected ✓", color = Color(0xFF6B9B25), fontWeight = FontWeight.Bold)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.Face,
                            contentDescription = "Face placeholder",
                            tint = TextPrimary,
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Add a clear photo of yourself.", color = TextSecondary, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Take Photo
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = OrangeGradientStart.copy(alpha = 0.5f))
                        .background(Brush.horizontalGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onTakePhotoClick() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CameraAlt, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Take Photo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    }
                }

                // Choose from Gallery
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = OrangeGradientStart.copy(alpha = 0.5f))
                        .background(Brush.horizontalGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onChoosePhotoClick() }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.AddPhotoAlternate, contentDescription = null, tint = TextPrimary, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text("Choose", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 14.sp)
                            Text("from Gallery", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary, lineHeight = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .border(
                        width = 2.dp,
                        brush = Brush.horizontalGradient(GradientBorderColors),
                        shape = RoundedCornerShape(27.dp)
                    )
                    .background(Color.White.copy(alpha = 0.7f), RoundedCornerShape(27.dp))
                    .clip(RoundedCornerShape(27.dp))
                    .clickable(enabled = uiState.isSubmitEnabled) { onSubmitClick() },
                contentAlignment = Alignment.Center
            ) {
                Text("Submit", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null,
                    tint = Color(0xFFC0C0C0),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Your data stays private & encrypted.",
                    fontSize = 13.sp,
                    color = Color(0xFFC0C0C0)
                )
            }
        }
    }
}

@Composable
private fun LoadingOverlayPhoto() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Composable
private fun BoxScope.BackgroundDecorationPhoto() {
    // Top Left Background Element
    Box(
        modifier = Modifier
            .size(250.dp)
            .align(Alignment.TopStart)
            .offset(x = (-60).dp, y = (-60).dp)
            .background(Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), CircleShape)
    )

    // Bottom Right Background Element
    Box(
        modifier = Modifier
            .size(250.dp)
            .align(Alignment.BottomEnd)
            .offset(x = 60.dp, y = 60.dp)
            .background(Brush.linearGradient(listOf(OrangeGradientStart, OrangeGradientEnd)), CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoUploadContentPreview() {
    PhotoUploadContent(
        uiState = PhotoUploadState(),
        snackbarHostState = remember { SnackbarHostState() },
        onSkipClick = {},
        onTakePhotoClick = {},
        onChoosePhotoClick = {},
        onSubmitClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoUploadContentSelectedPreview() {
    PhotoUploadContent(
        uiState = PhotoUploadState(isPhotoSelected = true),
        snackbarHostState = remember { SnackbarHostState() },
        onSkipClick = {},
        onTakePhotoClick = {},
        onChoosePhotoClick = {},
        onSubmitClick = {}
    )
}
