package com.example.anantapp.ui.verify

import android.util.Log
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

class BlinkDetectionManager(
    private val onBlinkDetected: (Boolean) -> Unit = {}
) {
    private val detector: FaceDetector
    private var lastBlinkTime: Long = 0L
    private val blinkThreshold = 0.3f  // Eye open probability threshold
    private val blinkCooldownMs = 800L  // Minimum time between blinks
    
    // State tracking for a full blink (Open -> Closed -> Open)
    private var eyeWasClosed = false

    init {
        val options = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .build()

        detector = FaceDetection.getClient(options)
    }

    fun detectBlink(faces: List<Face>): Boolean {
        if (faces.isEmpty()) {
            eyeWasClosed = false
            return false
        }

        val face = faces[0]
        val leftOpen = face.leftEyeOpenProbability ?: 1f
        val rightOpen = face.rightEyeOpenProbability ?: 1f
        val currentEyeOpenProbability = (leftOpen + rightOpen) / 2f
        val currentTime = System.currentTimeMillis()

        // 1. Detect Eyes Closing
        if (currentEyeOpenProbability < blinkThreshold) {
            if (!eyeWasClosed) {
                Log.d("BlinkDetection", "Eyes closed... waiting for re-open")
                eyeWasClosed = true
            }
            return false
        }

        // 2. Detect Eyes Re-opening after being closed
        if (eyeWasClosed && currentEyeOpenProbability > (blinkThreshold + 0.1f)) {
            // Check cooldown to avoid double triggers
            if ((currentTime - lastBlinkTime) > blinkCooldownMs) {
                lastBlinkTime = currentTime
                eyeWasClosed = false // Reset state
                Log.d("BlinkDetection", "Blink complete! Triggering capture while eyes are OPEN.")
                onBlinkDetected(true)
                return true
            } else {
                eyeWasClosed = false // Reset even if in cooldown
            }
        }

        return false
    }

    fun release() {
        detector.close()
    }
}
