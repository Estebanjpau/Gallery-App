package com.example.galleryapp.presenter.ui.gallery

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.data.utils.DateConverter
import com.example.galleryapp.di.LSUseCases
import com.example.galleryapp.di.RoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val roomUseCases: RoomUseCases,
    private val lsUseCases: LSUseCases
    ) : ViewModel() {

    private val _listImageEntity = roomUseCases.getAllImages()
    val listImageEntity: LiveData<List<ImageEntity>> get() = _listImageEntity

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun saveCopyImage(imagePath:Uri){
        if (isLoading.value != true) {
            coroutineScope.launch {
                _isLoading.postValue(true)
                 val path = lsUseCases.saveImageCopyInLSUseCase(imagePath)
                if (!path.isNullOrEmpty()){
                    val date = DateConverter.convertirFecha(path.takeLast(14))
                    date.take(10)
                    val imageEntity = ImageEntity(id = null, imageString = path, dateString = date)
                    roomUseCases.savePhotoInDB(imageEntity)
                }
                _isLoading.postValue(false)
            }
        }
    }

}