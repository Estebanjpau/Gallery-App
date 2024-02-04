package com.example.galleryapp.presenter.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val capturePhotoUseCase: CapturePhotoUseCase
): ViewModel() {

    private val _photoPath = MutableLiveData<String>()
    val photoPath: LiveData<String> get() = _photoPath

    private val isLoading = MutableLiveData<Boolean>()

    fun takePhoto() {
        viewModelScope.launch {
            isLoading.postValue(true)
                capturePhotoUseCase.takePhotoAndSaveInDB()
            isLoading.postValue(false)
        }
    }
}



