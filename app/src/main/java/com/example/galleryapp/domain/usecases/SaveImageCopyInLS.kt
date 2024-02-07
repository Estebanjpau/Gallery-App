package com.example.galleryapp.domain.usecases

import android.net.Uri
import com.example.galleryapp.data.LSRepository
import com.example.galleryapp.data.camera.CameraService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SaveImageCopyInLS @Inject constructor(
    private val lsRepository: LSRepository
) {

    operator fun invoke(imagePath:Uri):String?{
        return lsRepository.copyPhotoAndSaveInLs(imagePath)
    }
}