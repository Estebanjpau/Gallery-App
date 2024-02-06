package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.LSRepository
import com.example.galleryapp.data.camera.CameraService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CapturePhotoUseCase @Inject constructor(
    private val lsRepository: LSRepository
) {

    suspend operator fun invoke(): String? {
        return lsRepository.takePhotoAndSaveInLS()
    }
}