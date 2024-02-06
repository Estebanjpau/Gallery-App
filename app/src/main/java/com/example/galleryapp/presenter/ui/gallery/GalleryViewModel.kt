package com.example.galleryapp.presenter.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.domain.usecases.GetAllImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getAllImageUseCase: GetAllImageUseCase,
) : ViewModel() {

    private val _listImageEntity = getAllImageUseCase()
    val listImageEntity: LiveData<List<ImageEntity>> get() = _listImageEntity


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


}