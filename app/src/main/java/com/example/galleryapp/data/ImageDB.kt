package com.example.galleryapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.galleryapp.data.dao.ImageDao
import com.example.galleryapp.data.entities.ImageModel

@Database(entities = [ImageModel::class], version = 1)
abstract class ImageDB: RoomDatabase() {

    abstract fun getImageDao(): ImageDao

//    abstract val imageDao: ImageDao

    companion object {
        private var INSTANCE: ImageDB? = null

        fun getIntance(context: Context): ImageDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    ImageDB::class.java,
                    "image_db"
                ).build()
            }

            return INSTANCE as ImageDB
        }
    }

}