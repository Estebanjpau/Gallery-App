package com.example.galleryapp.di

import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import com.example.galleryapp.domain.usecases.DeleteImageFromDBUseCase
import com.example.galleryapp.domain.usecases.DeleteImageFromLSUseCase
import com.example.galleryapp.domain.usecases.GetAllImageUseCase
import com.example.galleryapp.domain.usecases.SavePhotoInDBUseCase

data class LSUseCases(
    val capturePhotoUseCase: CapturePhotoUseCase,
    val deleteImageFromLSUseCase: DeleteImageFromLSUseCase,
)