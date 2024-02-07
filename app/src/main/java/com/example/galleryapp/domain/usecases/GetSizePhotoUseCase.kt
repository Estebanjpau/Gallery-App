package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.LSRepository
import com.example.galleryapp.data.camera.CameraService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GetSizePhotoUseCase @Inject constructor(
    private val lsRepository: LSRepository
) {

   operator fun invoke(path: String): String {
        return lsRepository.getSizePhotoFromLS(path)
    }
}