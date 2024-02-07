package com.example.galleryapp.domain.usecases

import androidx.lifecycle.LiveData
import com.example.galleryapp.data.RoomRepository
import com.example.galleryapp.data.entities.ImageEntity
import javax.inject.Inject

class GetAllImageUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke() : LiveData<List<ImageEntity>> {
        return roomRepository.getAllImages()
    }

}