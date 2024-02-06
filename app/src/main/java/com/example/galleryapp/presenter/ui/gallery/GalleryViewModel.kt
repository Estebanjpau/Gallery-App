package com.example.galleryapp.presenter.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.di.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases,

    ) : ViewModel() {

    private val _listImageEntity = roomUseCases.getAllImages()
    val listImageEntity: LiveData<List<ImageEntity>> get() = _listImageEntity


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


}