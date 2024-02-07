package com.example.galleryapp.data

import android.net.Uri
import com.example.galleryapp.data.camera.CameraService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LSRepository @Inject constructor(private val cameraService: CameraService) {
    suspend fun takePhotoAndSaveInLS(): String? = suspendCoroutine { continuation ->
        cameraService.takePhoto { photoPath ->
            if (photoPath != null) {
                continuation.resume(photoPath)
            } else {
                continuation.resume(null)
            }
        }
    }

    fun deletePhotoInLS(imagePath: String) : Boolean {
        return cameraService.deletePhoto(imagePath)
    }

    fun copyPhotoAndSaveInLs(imagePath: Uri): String? {
        return cameraService.copyImageToOutputDirectory(imagePath)
    }

    fun getSizePhotoFromLS(path: String): String {
        return cameraService.getFileSizeString(path)
    }
}