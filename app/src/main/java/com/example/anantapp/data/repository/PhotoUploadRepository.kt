package com.example.anantapp.data.repository

import com.example.anantapp.data.model.PhotoUploadResult
import kotlinx.coroutines.delay

interface IPhotoUploadRepository {
    suspend fun uploadPhoto(photoUri: String): PhotoUploadResult
    suspend fun validatePhoto(photoUri: String): Boolean
}

class PhotoUploadRepository : IPhotoUploadRepository {
    override suspend fun uploadPhoto(photoUri: String): PhotoUploadResult {
        return try {
            // Simulate network call
            delay(2500)
            
            if (photoUri.isNotEmpty()) {
                PhotoUploadResult.Success("Photo uploaded successfully!")
            } else {
                PhotoUploadResult.Error("Photo URI is empty")
            }
        } catch (e: Exception) {
            PhotoUploadResult.Error("Failed to upload photo: ${e.message}")
        }
    }

    override suspend fun validatePhoto(photoUri: String): Boolean {
        delay(500)
        return photoUri.isNotEmpty()
    }
}
