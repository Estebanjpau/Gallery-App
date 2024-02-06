package com.example.galleryapp.presenter.ui.details

import androidx.lifecycle.ViewModel
import com.example.galleryapp.di.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases,
): ViewModel() {

    fun deletePhoto(int: Int){
        roomUseCases.deleteImagesFromDB(int)
    }

}