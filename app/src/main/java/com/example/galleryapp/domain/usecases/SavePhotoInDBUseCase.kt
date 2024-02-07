package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.RoomRepository
import com.example.galleryapp.data.entities.ImageEntity
import javax.inject.Inject

class SavePhotoInDBUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(image: ImageEntity) {
        roomRepository.insertNewImage(image)
    }
}