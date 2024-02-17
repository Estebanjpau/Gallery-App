package com.example.galleryapp.domain.usecases

import com.example.galleryapp.data.LSRepository
import javax.inject.Inject

class GetSizePhotoUseCase @Inject constructor(
    private val lsRepository: LSRepository
) {

   operator fun invoke(path: String): String {
        return lsRepository.getSizePhotoFromLS(path)
    }
}