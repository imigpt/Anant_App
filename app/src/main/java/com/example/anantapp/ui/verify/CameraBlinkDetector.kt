package com.example.anantapp.ui.verify

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.Face

/**
 * Analyzes camera frames to detect eye blinks using ML Kit Face Detection.
 * Requires minimum 2 consecutive blinks before triggering photo capture.
 * 
 * Blink Detection Logic:
 * - Eyes closed (probability < 0.3) prevents capture
 * - Eyes open without prior blink prevents capture
 * - Only captures after detecting 2+ complete blink cycles (open → closed → open)
 */
class CameraBlinkDetector(
    private val onBlinkDetected: () -> Unit = {},
    private val onBlinkCountChanged: (Int) -> Unit = {}
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
    
    // Blink counting
    private var blinkCount: Int = 0
    private var requiredBlinks: Int = 2  // Minimum blinks needed to capture
    private var blinkResetTimeoutMs: Long = 3000L  // Reset count if no blink for 3 seconds
    private var lastDetectedBlinkTime: Long = 0L
    
    // Tunable parameters
    private val BLINK_THRESHOLD = 0.3f       // Eye "open" probability threshold (0-1)
    private val BLINK_COOLDOWN_MS = 200L     // Minimum ms between individual blinks
    private val EYES_CLOSED_MINIMUM_DURATION = 50L  // Eyes must be closed for at least 50ms for a valid blink
    
    private var frameCount = 0
    private var eyesClosedStartTime: Long? = null  // Track when eyes closed
    
    /**
     * Called by CameraX for each camera frame.
     * Extracts face data and detects blinks.
     */
    override fun analyze(imageProxy: ImageProxy) {
        try {
            frameCount++
            
            // Analyze every 5th frame to reduce CPU load
            if (frameCount % 5 != 0) {
                imageProxy.close()
                return
            }
            
            val mediaImage = imageProxy.image ?: run {
                imageProxy.close()
                return
            }
            
            // Create ML Kit InputImage from media frame
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            
            // Process with ML Kit face detection
            detector.process(image)
                .addOnSuccessListener { faces ->
                    if (faces.isNotEmpty()) {
                        detectBlinkFromFaces(faces)
                    } else {
                        resetBlinkDetection()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("BlinkDetector", "Face detection failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } catch (e: Exception) {
            Log.e("BlinkDetector", "Error analyzing frame", e)
            imageProxy.close()
        }
    }
    
    /**
     * Analyzes face ML Kit results to detect blink events.
     * A blink is a complete cycle: open → closed → open
     * Only triggers capture after detecting 2+ blinks.
     */
    private fun detectBlinkFromFaces(faces: List<Face>) {
        if (faces.isEmpty()) {
            resetBlinkDetection()
            return
        }
        
        val face = faces[0]
        val currentLeftEyeOpen = face.leftEyeOpenProbability ?: 1f
        val currentRightEyeOpen = face.rightEyeOpenProbability ?: 1f
        val currentEyeOpenProb = (currentLeftEyeOpen + currentRightEyeOpen) / 2f
        val currentTime = System.currentTimeMillis()
        
        // Check if blink count should be reset (no blinks detected for too long)
        if (lastDetectedBlinkTime > 0 && 
            (currentTime - lastDetectedBlinkTime) > blinkResetTimeoutMs) {
            Log.d("BlinkDetector", "Blink timeout reached. Resetting count.")
            resetBlinkDetection()
        }
        
        // Detect eyes closing
        if (currentEyeOpenProb < BLINK_THRESHOLD) {
            if (eyesClosedStartTime == null) {
                eyesClosedStartTime = currentTime
                Log.d("BlinkDetector", "Eyes closed detected. Waiting for minimum duration...")
            }
        } else {
            // Eyes are open
            // Check if we just completed a valid blink (eyes were closed for minimum duration)
            val wasEyesClosed = eyesClosedStartTime != null
            val eyesWereClosedLongEnough = wasEyesClosed && 
                (currentTime - (eyesClosedStartTime ?: currentTime)) >= EYES_CLOSED_MINIMUM_DURATION
            
            if (wasEyesClosed && eyesWereClosedLongEnough && 
                (currentTime - lastBlinkTime) > BLINK_COOLDOWN_MS) {
                // Valid blink detected
                lastBlinkTime = currentTime
                blinkCount++
                lastDetectedBlinkTime = currentTime
                
                Log.d("BlinkDetector", 
                    "Blink #$blinkCount detected! Left: ${(currentLeftEyeOpen * 100).toInt()}%, Right: ${(currentRightEyeOpen * 100).toInt()}%")
                
                onBlinkCountChanged(blinkCount)
                
                // Trigger capture if minimum blinks reached
                if (blinkCount >= requiredBlinks) {
                    Log.d("BlinkDetector", "Required $requiredBlinks blinks reached! Triggering photo capture.")
                    onBlinkDetected()
                    resetBlinkDetection()
                }
            }
            
            // Reset eyes closed tracking
            eyesClosedStartTime = null
        }
        
        // Update state for next frame
        previousLeftEyeOpen = currentLeftEyeOpen
        previousRightEyeOpen = currentRightEyeOpen
    }
    
    /**
     * Reset blink detection state
     */
    private fun resetBlinkDetection() {
        blinkCount = 0
        eyesClosedStartTime = null
        lastDetectedBlinkTime = 0L
        onBlinkCountChanged(0)
    }
    
    /**
     * Get current blink count
     */
    fun getBlinkCount(): Int = blinkCount
    
    /**
     * Set the required number of blinks to trigger capture (default: 2)
     */
    fun setRequiredBlinks(count: Int) {
        requiredBlinks = count
    }
    
    /**
     * Clean up detector resources. Call when done with camera.
     */
    fun release() {
        try {
            detector.close()
        } catch (e: Exception) {
            Log.e("BlinkDetector", "Error releasing detector", e)
        }
    }
}
