package com.example.galleryapp.presenter.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.data.entities.ImageModel
import com.example.galleryapp.presenter.utils.GalleryDiffUtils

class GalleryAdapter(private var list: List<ImageModel>): RecyclerView.Adapter<GalleryViewHolder>(){

    fun updateList(newList: List<ImageModel>){
        val recipeDiff = GalleryDiffUtils(list, newList)
        val result = DiffUtil.calculateDiff(recipeDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_rv_home, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.paint(list[position])
    }
}