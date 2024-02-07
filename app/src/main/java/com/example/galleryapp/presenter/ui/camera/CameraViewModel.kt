package com.example.galleryapp.presenter.ui.camera

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.data.utils.DateConverter
import com.example.galleryapp.di.LSUseCases
import com.example.galleryapp.di.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val lsUseCases: LSUseCases,
    private val roomUseCases: RoomUseCases
) : ViewModel() {

    private val _photoPath = MutableLiveData<String?>()
    val photoPath: LiveData<String?> get() = _photoPath

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun takePhoto() {
        if (isLoading.value != true) {
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

    fun keepPhoto(context: Context) {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val imagePath = photoPath.value
                if (!imagePath.isNullOrBlank()) {
                    val date = DateConverter.convertirFecha(imagePath.takeLast(14))
                    date.take(10)
                    println(date)
                    val imageEntity = ImageEntity(id = null, imageString = imagePath, dateString = date)
                    roomUseCases.savePhotoInLS(imageEntity)

                    Toast.makeText(context, "photo previusly saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "No photo previusly saved", Toast.LENGTH_SHORT).show()
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



