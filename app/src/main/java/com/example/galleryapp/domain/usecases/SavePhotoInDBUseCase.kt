package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.ImageRepository
import com.example.galleryapp.data.entities.ImageModel
import javax.inject.Inject

class SavePhotoInDBUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke(image: ImageModel) {
        imageRepository.insertNewImage(image)
    }
}