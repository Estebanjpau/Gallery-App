package com.example.galleryapp.domain.usecases

import android.net.Uri
import com.example.galleryapp.data.LSRepository
import javax.inject.Inject

class SaveImageCopyInLS @Inject constructor(
    private val lsRepository: LSRepository
) {

    operator fun invoke(imagePath:Uri):String?{
        return lsRepository.copyPhotoAndSaveInLs(imagePath)
    }
}