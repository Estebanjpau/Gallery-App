package com.example.galleryapp.presenter.ui.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.ItemGalleryRvHomeBinding
import com.example.galleryapp.presenter.ui.main.MainActivity

class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGalleryRvHomeBinding.bind(view)

    fun paint(imageEntity: ImageEntity, view: MainActivity) {

        Glide.with(itemView.context)
            .load(imageEntity.imageString)
            .centerCrop()
            .into(binding.ivGalleryItem)

        binding.clGalleryItem.setOnClickListener {
            view.showDetailsScreen(imageEntity.id!!)
        }
    }
}