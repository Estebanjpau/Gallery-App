package com.example.galleryapp.presenter.ui.camera

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.data.entities.ImageModel
import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import com.example.galleryapp.domain.usecases.SavePhotoInDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val capturePhotoUseCase: CapturePhotoUseCase,
    private val savePhotoInDBUseCase: SavePhotoInDBUseCase
) : ViewModel() {

    private val _photoPath = MutableLiveData<String?>()
    val photoPath: LiveData<String?> get() = _photoPath

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun takePhoto() {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val result = capturePhotoUseCase()
                if (!result.isNullOrEmpty()) {
                    _photoPath.postValue(result)
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun keepPhoto(context: Context) {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val imagePath = photoPath.value
                if (!imagePath.isNullOrBlank()) {
                    val imageModel = ImageModel(id = null, imageString = imagePath)
                    savePhotoInDBUseCase(imageModel)
                } else {
                    Toast.makeText(context, "No photo previusly saved", Toast.LENGTH_SHORT).show()
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun editPhoto() {

    }

    fun deletePhoto() {

    }
}



