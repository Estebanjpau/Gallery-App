package com.example.galleryapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.galleryapp.data.entities.ImageModel

@Dao
interface ImageDao {

    @Query("SELECT * FROM images_table")
    suspend fun getAllImages(): List<ImageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewImage(image: ImageModel)
}