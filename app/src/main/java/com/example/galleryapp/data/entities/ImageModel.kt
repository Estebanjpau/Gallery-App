package com.example.galleryapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var imageString: String
)
