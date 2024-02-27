package com.example.galleryapp.di

import com.example.galleryapp.domain.usecases.DeleteImageFromDBUseCase
import com.example.galleryapp.domain.usecases.GetAllImageUseCase
import com.example.galleryapp.domain.usecases.GetImageByIdUseCase
import com.example.galleryapp.domain.usecases.SavePhotoInDBUseCase

data class RoomUseCases(
    val getAllImages: GetAllImageUseCase,
    val getImageById: GetImageByIdUseCase,
    val deleteImagesFromDB: DeleteImageFromDBUseCase,
    val savePhotoInDB: SavePhotoInDBUseCase,
)