package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.RoomRepository
import com.example.galleryapp.data.entities.ImageEntity
import javax.inject.Inject

class GetImageByIdUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
){
    operator fun invoke(id:Int) : ImageEntity {
        return roomRepository.getImageById(id)
    }
}