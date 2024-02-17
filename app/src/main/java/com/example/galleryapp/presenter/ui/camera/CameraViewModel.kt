package com.example.galleryapp.presenter.ui.camera

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.data.utils.DateConverter
import com.example.galleryapp.di.LSUseCases
import com.example.galleryapp.di.RoomUseCases
import com.example.galleryapp.presenter.utils.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val lsUseCases: LSUseCases,
    private val roomUseCases: RoomUseCases,
    private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val _photoPath = MutableLiveData<String?>()
    val photoPath: LiveData<String?> get() = _photoPath

    private val _isLoading = MutableLiveData(false)
    private val isLoading: LiveData<Boolean> get() = _isLoading

    fun takePhoto() {
        if (isLoading.value == false) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val result = lsUseCases.capturePhotoUseCase()
                if (!result.isNullOrEmpty()) {
                    _photoPath.postValue(result)
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun keepPhoto(view: View) {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val imagePath = photoPath.value
                if (!imagePath.isNullOrBlank()) {
                    val date = DateConverter.convertirFecha(imagePath.takeLast(14))
                    date.take(10)
                    val imageEntity = ImageEntity(id = null, imageString = imagePath, dateString = date)
                    roomUseCases.savePhotoInDB(imageEntity)

                    snackbarManager.showSnackbar("Saved photo", view)
                } else {
                    snackbarManager.showSnackbar("Can't saved photo", view)
                }
                _isLoading.postValue(false)
            }
        }
    }

    fun deletePhoto(imagePath: String) {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                lsUseCases.deleteImageFromLSUseCase(imagePath)
            }
        }
        _isLoading.postValue(false)
    }
}



