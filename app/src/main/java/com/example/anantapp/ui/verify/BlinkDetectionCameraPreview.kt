package com.example.anantapp.ui.verify

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

/**
 * A composable that displays camera preview with real-time blink detection.
 * Requires minimum 2 blinks before capturing photo.
 * When 2+ blinks are detected, [onBlinkDetected] callback is triggered.
 */
@Composable
fun BlinkDetectionCameraPreview(
    modifier: Modifier = Modifier,
    onBlinkDetected: () -> Unit = {},
    onBlinkCountChanged: (Int) -> Unit = {},
    lifecycleOwner: LifecycleOwner,
    context: Context,
    requiredBlinks: Int = 2
) {
    var detectorRef by remember { mutableStateOf<CameraBlinkDetector?>(null) }
    var currentBlinkCount by remember { mutableIntStateOf(0) }
    
    DisposableEffect(Unit) {
        onDispose {
            detectorRef?.release()
            Log.d("BlinkCamera", "Camera preview disposed")
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    // USE MAIN EXECUTOR: bindToLifecycle must be called on the main thread
                    cameraProviderFuture.addListener(
                        {
                            try {
                                val cameraProvider = cameraProviderFuture.get()
                                
                                val preview = Preview.Builder().build().also {
                                    it.setSurfaceProvider(surfaceProvider)
                                }
                                
                                val detector = CameraBlinkDetector(
                                    onBlinkDetected = {
                                        Log.d("BlinkCamera", "Photo capture triggered after $requiredBlinks blinks!")
                                        onBlinkDetected()
                                    },
                                    onBlinkCountChanged = { count ->
                                        currentBlinkCount = count
                                        onBlinkCountChanged(count)
                                        Log.d("BlinkCamera", "Blink count updated: $count/$requiredBlinks")
                                    }
                                )
                                detector.setRequiredBlinks(requiredBlinks)
                                detectorRef = detector
                                
                                val imageAnalysis = ImageAnalysis.Builder()
                                    .setResolutionSelector(
                                        ResolutionSelector.Builder()
                                            .setAspectRatioStrategy(
                                                AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY
                                            )
                                            .build()
                                    )
                                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                    .build()
                                    .also { analysis ->
                                        analysis.setAnalyzer(
                                            Executors.newSingleThreadExecutor(),
                                            detector
                                        )
                                    }
                                
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_FRONT_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                                
                                Log.d("BlinkCamera", "Camera bound successfully on Main Thread")
                            } catch (e: Exception) {
                                Log.e("BlinkCamera", "Error binding camera", e)
                            }
                        },
                        ContextCompat.getMainExecutor(ctx)
                    )
                }
            },
            modifier = modifier.fillMaxSize()
        )
        
        // Status Overlay with Blink Counter
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .background(Color.Black.copy(alpha = 0.6f), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "👁️ Blink $currentBlinkCount/$requiredBlinks times to capture",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
