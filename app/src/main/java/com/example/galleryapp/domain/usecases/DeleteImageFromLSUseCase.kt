package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.LSRepository
import javax.inject.Inject

class DeleteImageFromLSUseCase @Inject constructor(
    private val lsRepository: LSRepository
){
    operator fun invoke(imagePath: String) : Boolean{
        return lsRepository.deletePhotoInLS(imagePath)
    }
}