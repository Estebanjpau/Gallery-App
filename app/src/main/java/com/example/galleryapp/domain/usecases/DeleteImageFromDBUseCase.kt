package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.RoomRepository
import javax.inject.Inject

class DeleteImageFromDBUseCase @Inject constructor(
    private val roomRepository: RoomRepository,
) {
    operator fun invoke(int: Int) {
        return roomRepository.deleteImageFromDB(int)
    }
}