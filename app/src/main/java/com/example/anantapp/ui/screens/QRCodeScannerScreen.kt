package com.example.anantapp.ui.screens

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.anantapp.R
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun QRCodeScannerScreen(
    onQRCodeDetected: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    
    var scannedQRCode by remember { mutableStateOf<String?>(null) }
    var cameraPermissionGranted by remember { mutableStateOf(false) }
    var isScanningActive by remember { mutableStateOf(false) }
    
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionGranted = isGranted
        if (isGranted) {
            isScanningActive = true
        }
    }
    
    LaunchedEffect(Unit) {
        val permission = Manifest.permission.CAMERA
        cameraPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        
        if (!cameraPermissionGranted) {
            cameraPermissionLauncher.launch(permission)
        } else {
            isScanningActive = true
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        // Back Button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        // Top-Left Solid Blue Blob (behind the card)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-30).dp, y = 60.dp)
                .size(160.dp)
                .background(Color(0xFF1E7AE5), CircleShape)
        )

        // Bottom-Right Solid Blue Blob (behind the card)
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = (-20).dp)
                .size(180.dp)
                .background(Color(0xFF1866C9), CircleShape)
        )

        // Main Card
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 64.dp, bottom = 48.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = Color.Black.copy(alpha = 0.08f),
                    ambientColor = Color.Black.copy(alpha = 0.02f)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Top-Left Glassy Blue Blur Overlay (inside card)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(220.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFF8AB4F8).copy(alpha = 0.4f), Color.Transparent),
                                radius = 250f
                            )
                        )
                )

                // Bottom-Right Glassy Blue Blur Overlay (inside card)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(260.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFF669DF6).copy(alpha = 0.6f), Color.Transparent),
                                radius = 350f
                            )
                        )
                )

                // Main Content Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Show different content based on scan state
                    if (scannedQRCode != null) {
                        // Success State
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Success",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF4CAF50)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "QR Code Detected!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Display scanned code
                        Text(
                            text = scannedQRCode!!,
                            fontSize = 14.sp,
                            color = Color(0xFF666666),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFF5F5F5),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Reset Button
                        Button(
                            onClick = {
                                scannedQRCode = null
                                isScanningActive = true
                                onQRCodeDetected("")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(28.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Scan Again", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        // Scanning State
                        Image(
                            painter = painterResource(id = R.drawable.qr_code),
                            contentDescription = "QR Code Icon",
                            modifier = Modifier.size(64.dp),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Scan QR Code",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Camera Preview with QR Detection
                        if (cameraPermissionGranted && isScanningActive) {
                            CameraPreviewWithQRScanner(
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                onQRCodeScanned = { qrCode ->
                                    scannedQRCode = qrCode
                                    isScanningActive = false
                                    onQRCodeDetected(qrCode)
                                    Log.d("QRCodeScanner", "QR Code detected: $qrCode")
                                }
                            )
                        } else {
                            // Fallback Viewfinder
                            QRViewfinder(
                                modifier = Modifier.size(200.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        // Scan Button
                        ScanButton(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Security message
                    SecurityMessage()
                }
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
private fun CameraPreviewWithQRScanner(
    modifier: Modifier = Modifier,
    onQRCodeScanned: (String) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    try {
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(surfaceProvider)
                        }
                        
                        val imageAnalyzer = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also { analysis ->
                                analysis.setAnalyzer(
                                    Executors.newSingleThreadExecutor()
                                ) { imageProxy ->
                                    val mediaImage = imageProxy.image
                                    if (mediaImage != null) {
                                        val inputImage = InputImage.fromMediaImage(
                                            mediaImage,
                                            imageProxy.imageInfo.rotationDegrees
                                        )
                                        
                                        val scanner = BarcodeScanning.getClient()
                                        scanner.process(inputImage)
                                            .addOnSuccessListener { barcodes ->
                                                for (barcode in barcodes) {
                                                    // Filter for QR codes only
                                                    if (barcode.format == Barcode.FORMAT_QR_CODE) {
                                                        val qrValue = barcode.rawValue
                                                        if (!qrValue.isNullOrEmpty()) {
                                                            onQRCodeScanned(qrValue)
                                                            Log.d("QRScanner", "Detected: $qrValue")
                                                        }
                                                    }
                                                }
                                            }
                                            .addOnFailureListener { e ->
                                                Log.e("QRScanner", "Error: ${e.message}")
                                            }
                                            .addOnCompleteListener {
                                                imageProxy.close()
                                            }
                                    }
                                }
                            }
                        
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalyzer
                        )
                    } catch (e: Exception) {
                        Log.e("CameraPreview", "Camera initialization failed", e)
                    }
                }, ContextCompat.getMainExecutor(ctx))
            }
        }
    )
}

@Composable
private fun QRViewfinder(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 2.dp.toPx()
        val cornerLength = 48.dp.toPx()
        val cornerRadius = 16.dp.toPx()
        val color = Color.Black
        val style = Stroke(width = strokeWidth, cap = StrokeCap.Round)

        // Top-Left Bracket
        drawPath(
            Path().apply {
                moveTo(0f, cornerLength)
                lineTo(0f, cornerRadius)
                quadraticTo(0f, 0f, cornerRadius, 0f)
                lineTo(cornerLength, 0f)
            },
            color = color, style = style
        )

        // Top-Right Bracket
        drawPath(
            Path().apply {
                moveTo(size.width - cornerLength, 0f)
                lineTo(size.width - cornerRadius, 0f)
                quadraticTo(size.width, 0f, size.width, cornerRadius)
                lineTo(size.width, cornerLength)
            },
            color = color, style = style
        )

        // Bottom-Left Bracket
        drawPath(
            Path().apply {
                moveTo(0f, size.height - cornerLength)
                lineTo(0f, size.height - cornerRadius)
                quadraticTo(0f, size.height, cornerRadius, size.height)
                lineTo(cornerLength, size.height)
            },
            color = color, style = style
        )

        // Bottom-Right Bracket
        drawPath(
            Path().apply {
                moveTo(size.width, size.height - cornerLength)
                lineTo(size.width, size.height - cornerRadius)
                quadraticTo(size.width, size.height, size.width - cornerRadius, size.height)
                lineTo(size.width - cornerLength, size.height)
            },
            color = color, style = style
        )

        // Center Horizontal Scanning Line
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun ScanButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val buttonShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .height(56.dp)
            .shadow(
                elevation = 6.dp,
                shape = buttonShape,
                spotColor = Color.Black.copy(alpha = 0.15f),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = buttonShape
            )
            .clip(buttonShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFEAEAEA)
                    )
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.12f),
                            Color.Transparent,
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 40f
                    )
                )
        )

        Text(
            text = "Scan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun SecurityMessage() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Security",
            modifier = Modifier.size(12.dp),
            tint = Color(0xFFC0C0C0)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "All scans are logged securely",
            fontSize = 12.sp,
            color = Color(0xFFC0C0C0),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun QRCodeScannerScreenPreview() {
    MaterialTheme {
        QRCodeScannerScreen()
    }
}