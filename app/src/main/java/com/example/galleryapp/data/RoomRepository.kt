package com.example.galleryapp.data

import androidx.lifecycle.LiveData
import com.example.galleryapp.data.dao.ImageDao
import com.example.galleryapp.data.entities.ImageEntity
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RoomRepository @Inject constructor(private val imageDao: ImageDao) {

    fun getAllImages(): LiveData<List<ImageEntity>> {
        return imageDao.getAllImages()
    }

    fun insertNewImage(image: ImageEntity) = runBlocking {
        imageDao.insertNewImage(image)
    }

    fun deleteImageFromDB(int:Int) = runBlocking{
        imageDao.deleteImageById(int)
    }

}