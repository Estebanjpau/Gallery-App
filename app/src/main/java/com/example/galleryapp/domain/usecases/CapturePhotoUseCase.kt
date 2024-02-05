package com.example.galleryapp.domain.usecases

import com.example.galleryapp.domain.CameraService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CapturePhotoUseCase @Inject constructor(
    private val cameraService: CameraService,
) {

    suspend operator fun invoke(): String? = suspendCoroutine { continuation ->
        cameraService.takePhoto { photoPath ->
            if (photoPath != null) {
                continuation.resume(photoPath)
            } else {
                continuation.resume(null)
            }
        }
    }
}