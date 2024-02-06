package com.example.galleryapp.presenter.ui.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.ItemGalleryRvHomeBinding

class GalleryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemGalleryRvHomeBinding.bind(view)

    fun paint(imageEntity: ImageEntity){

    }
}