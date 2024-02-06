package com.example.galleryapp.presenter.ui.gallery

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.ItemGalleryRvHomeBinding
import com.example.galleryapp.presenter.ui.details.DetailsFragment

class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGalleryRvHomeBinding.bind(view)

    fun paint(imageEntity: ImageEntity, fragmentManager: FragmentManager) {

        Glide.with(itemView.context)
            .load(imageEntity.imageString)
            .centerCrop()
            .into(binding.ivGalleryItem)

        binding.clGalleryItem.setOnClickListener {
            fragmentManager.beginTransaction()
                .add(R.id.details_layout, DetailsFragment.newInstance(imageEntity.id!!, imageEntity.imageString, imageEntity.dateString)).addToBackStack(null).commit()
        }
    }
}