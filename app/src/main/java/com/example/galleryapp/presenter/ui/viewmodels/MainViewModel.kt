package com.example.galleryapp.presenter.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.galleryapp.domain.usecases.CapturePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val capturePhotoUseCase: CapturePhotoUseCase
) : ViewModel() {

    private val _photoPath = MutableLiveData<String>()
    val photoPath: LiveData<String> get() = _photoPath

}