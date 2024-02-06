package com.example.galleryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.galleryapp.data.dao.ImageDao
import com.example.galleryapp.data.entities.ImageEntity

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class ImageDB: RoomDatabase() {

    abstract fun getImageDao(): ImageDao
}