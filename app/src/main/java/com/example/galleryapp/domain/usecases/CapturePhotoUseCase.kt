package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.LSRepository
import javax.inject.Inject

class CapturePhotoUseCase @Inject constructor(
    private val lsRepository: LSRepository
) {

    suspend operator fun invoke(): String? {
        return lsRepository.takePhotoAndSaveInLS()
    }
}