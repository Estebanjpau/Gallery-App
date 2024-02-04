package com.example.galleryapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var imageString: String
)
