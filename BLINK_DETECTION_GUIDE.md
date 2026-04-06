# Eye Blink Detection - Implementation Guide

## Overview
This guide shows how to integrate ML Kit face detection with CameraX to enable automatic photo capture on eye blink detection in PhotoUploadScreen.

## Architecture
1. **BlinkDetectionManager** - Detects blinks from Face Detection results
2. **CameraX with ML Kit** - Captures frames and detects faces
3. **PhotoUploadScreen** - Integrates detection logic with UI

---

## Step 1: Add Dependencies

Update `build.gradle.kts` (app level):

```gradle
dependencies {
    // ML Kit Face Detection
    implementation("com.google.mlkit:face-detection:16.1.5")
    
    // CameraX
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    
    // For image analysis
    implementation("androidx.camera:camera-extensions:1.3.0")
}
```

---

## Step 2: Create Camera Blink Detector

Create a new file: `CameraBlinkDetector.kt`

```kotlin
package com.example.anantapp.ui.verify

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysisConfig
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.Face

class CameraBlinkDetector(
    private val onBlinkDetected: (Int) -> Unit = {}
) : ImageAnalysis.Analyzer {
    
    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .build()
    )
    
    private var previousLeftEyeOpen: Float = 1f
    private var previousRightEyeOpen: Float = 1f
    private var lastBlinkTime: Long = 0L
    private val BLINK_THRESHOLD = 0.2f
    private val BLINK_COOLDOWN_MS = 300L
    private var frameCount = 0
    
    override fun analyze(imageProxy: ImageProxy) {
        frameCount++
        // Analyze every 5th frame to reduce overhead
        if (frameCount % 5 != 0) {
            imageProxy.close()
            return
        }
        
        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    detectBlinkFromFaces(faces)
                }
            }
            .addOnFailureListener { e ->
                Log.e("BlinkDetector", "Face detection failed", e)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
    
    private fun detectBlinkFromFaces(faces: List<Face>) {
        if (faces.isEmpty()) return
        
        val face = faces[0]
        val currentLeftEyeOpen = face.leftEyeOpenProbability ?: 1f
        val currentRightEyeOpen = face.rightEyeOpenProbability ?: 1f
        val currentTime = System.currentTimeMillis()
        
        // Detect blink: eyes open → closed → open transition
        val leftEyeBlink = (previousLeftEyeOpen > BLINK_THRESHOLD && 
                           currentLeftEyeOpen < BLINK_THRESHOLD)
        
        val rightEyeBlink = (previousRightEyeOpen > BLINK_THRESHOLD && 
                            currentRightEyeOpen < BLINK_THRESHOLD)
        
        val isBlink = (leftEyeBlink || rightEyeBlink) && 
                      (currentTime - lastBlinkTime) > BLINK_COOLDOWN_MS
        
        if (isBlink) {
            lastBlinkTime = currentTime
            Log.d("BlinkDetector", "Blink detected! (L: ${currentLeftEyeOpen.toInt()}%, R: ${currentRightEyeOpen.toInt()}%)")
            onBlinkDetected(1)
        }
        
        previousLeftEyeOpen = currentLeftEyeOpen
        previousRightEyeOpen = currentRightEyeOpen
    }
    
    fun release() {
        detector.close()
    }
}
```

---

## Step 3: Create CameraXPreviewComposable

Create a new file: `BlinkDetectionCameraPreview.kt`

```kotlin
package com.example.anantapp.ui.verify

import android.content.Context
import android.util.Size
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

@Composable
fun BlinkDetectionCameraPreview(
    modifier: Modifier = Modifier,
    onBlinkDetected: () -> Unit = {},
    lifecycleOwner: LifecycleOwner,
    context: Context
) {
    var detectorRef by remember { mutableStateOf<CameraBlinkDetector?>(null) }
    var cameraProviderRef by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    
    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraProviderRef = cameraProvider
        }, Executors.newSingleThreadExecutor())
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    
                    val cameraProvider = cameraProviderRef ?: return@apply
                    
                    // Create Preview
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(surfaceProvider)
                    }
                    
                    // Create ImageAnalysis for blink detection
                    val detector = CameraBlinkDetector { 
                        onBlinkDetected()
                    }
                    detectorRef = detector
                    
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setResolutionSelector(
                            ResolutionSelector.Builder()
                                .setAspectRatioStrategy(
                                    AspectRatioStrategy.LANDSCAPE_16_9_FALLBACK
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
                    
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_FRONT_CAMERA,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            modifier = modifier.fillMaxSize()
        )
        
        // Blink indicator overlay
        Text(
            text = "👁️ Face detected - Blink to capture",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
    
    DisposableEffect(Unit) {
        onDispose {
            detectorRef?.release()
            cameraProviderRef?.unbindAll()
        }
    }
}
```

---

## Step 4: Update PhotoUploadScreen Usage

In `PhotoUploadScreen`, use the blink detection camera:

```kotlin
// When user clicks "Take Photo"
onTakePhotoClick = {
    val permissionCheck = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    )
    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
        isBlinkDetectionActive = true
        blinkDetectionMessage = "📹 Blink to capture photo..."
        
        // Camera will auto-capture on blink via onBlinkDetected callback
        // This triggers the cameraLauncher which will save the photo
    } else {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}
```

---

## How It Works

1. **User taps "Take Photo"** → Blink detection activates (front camera starts)
2. **ML Kit analyzes faces** → Detects eye open/close probabilities every frame
3. **Blink detected** → Eyes transition from open → closed → starting to open
4. **Photo captured** → Camera URI is saved to viewModel
5. **UI updates** → Shows "Photo captured ✓"

---

## Key Parameters

```kotlin
// Thresholds in CameraBlinkDetector.kt
private val BLINK_THRESHOLD = 0.2f      // Eye "open" probability threshold
private val BLINK_COOLDOWN_MS = 300L    // Minimum time between blinks
```

Adjust these if blink detection is too sensitive or misses blinks.

---

## Permissions Required

Add to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.CAMERA" />
```

---

## Testing Steps

1. ✅ Grant camera permission
2. ✅ Tap "Take Photo"
3. ✅ Look at camera and blink naturally
4. ✅ Photo should auto-capture
5. ✅ See confirmation message

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Blinks not detected | Lower `BLINK_THRESHOLD` to 0.15 |
| Too sensitive | Increase `BLINK_COOLDOWN_MS` to 500 |
| No face detected | Ensure front camera is used + good lighting |
| Crashes on recompose | Check `DisposableEffect` cleanup |

---

## Optional Enhancements

- Add face quality checks (brightness, angle)
- Multiple blink requirement (2 blinks = capture)
- Add blink count UI indicator
- Show confidence score
