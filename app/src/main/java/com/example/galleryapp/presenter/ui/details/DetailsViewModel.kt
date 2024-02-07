package com.example.galleryapp.presenter.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galleryapp.di.LSUseCases
import com.example.galleryapp.di.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val lsUseCases: LSUseCases,
    private val roomUseCases: RoomUseCases,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun deletePhoto(imagePath: String, id: Int) {
        if (isLoading.value != true) {
            viewModelScope.launch {
                _isLoading.postValue(true)
                val it = lsUseCases.deleteImageFromLSUseCase(imagePath)
                if (it){
                    deletePhotoFromRoomDB(id)
                }
            }
        }
        _isLoading.postValue(false)
    }

    private fun deletePhotoFromRoomDB(id:Int){
        coroutineScope.launch {
            roomUseCases.deleteImagesFromDB(id)
        }
    }

}