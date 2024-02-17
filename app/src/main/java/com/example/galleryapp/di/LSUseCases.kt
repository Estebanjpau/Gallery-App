package com.example.galleryapp.di

import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import com.example.galleryapp.domain.usecases.DeleteImageFromLSUseCase
import com.example.galleryapp.domain.usecases.GetSizePhotoUseCase
import com.example.galleryapp.domain.usecases.SaveImageCopyInLS

data class LSUseCases(
    val capturePhotoUseCase: CapturePhotoUseCase,
    val deleteImageFromLSUseCase: DeleteImageFromLSUseCase,
    val saveImageCopyInLSUseCase: SaveImageCopyInLS,
    val getSizePhotoUseCase: GetSizePhotoUseCase
)