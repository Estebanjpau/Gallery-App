package com.example.galleryapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.galleryapp.data.entities.ImageEntity

@Dao
interface ImageDao {

    @Query("SELECT * FROM images_table ORDER BY image_path DESC")
    fun getAllImages() : LiveData<List<ImageEntity>>

    @Query("SELECT * FROM images_table WHERE id = :id")
    fun getImagesById(id: Int) : ImageEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewImage(image: ImageEntity)

    @Query("DELETE FROM images_table WHERE id = :id")
    fun deleteImageById(id: Int)

    @Query("DELETE FROM images_table ")
    fun deleteAllImage()

}