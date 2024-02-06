package com.example.galleryapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "image_path")
    var imageString: String,
    @ColumnInfo(name = "date_string")
    var dateString: String
)
