package com.example.galleryapp.domain.usecases

import android.content.Context
import com.example.galleryapp.data.ImageRepository
import com.example.galleryapp.domain.CameraService
import com.example.galleryapp.presenter.ui.fragments.camera.CameraFragment
import java.io.File
import javax.inject.Inject

class CapturePhotoUseCase @Inject constructor(
    private val cameraService: CameraService,
    private val photoRepository: ImageRepository,
    private val cameraFragment: CameraFragment
) {

    fun takePhotoAndSaveInDB(){
        cameraService.takePhoto()
    }
}