package com.example.galleryapp.data

import com.example.galleryapp.data.dao.ImageDao
import com.example.galleryapp.data.entities.ImageModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageDao: ImageDao) {

    fun getAllImages(): List<ImageModel> = runBlocking {
        imageDao.getAllImages()
    }

    fun insertNewImage(image: ImageModel) = runBlocking {
        imageDao.insertNewImage(image)
    }

}