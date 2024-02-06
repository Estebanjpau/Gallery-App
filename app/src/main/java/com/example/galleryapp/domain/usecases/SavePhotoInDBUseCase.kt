package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.ImageRepository
import com.example.galleryapp.data.entities.ImageEntity
import javax.inject.Inject

class SavePhotoInDBUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(image: ImageEntity) {
        imageRepository.insertNewImage(image)
    }
}