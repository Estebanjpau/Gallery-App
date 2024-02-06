package com.example.galleryapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.galleryapp.data.ImageRepository
import com.example.galleryapp.data.entities.ImageEntity
import javax.inject.Inject

class GetAllImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    operator fun invoke() : LiveData<List<ImageEntity>> {
        return imageRepository.getAllImages()
    }

}