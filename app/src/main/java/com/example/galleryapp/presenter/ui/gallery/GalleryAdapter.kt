package com.example.galleryapp.presenter.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.presenter.ui.main.MainActivity
import com.example.galleryapp.presenter.utils.DiffUtils

class GalleryAdapter(private var list: List<ImageEntity>, private var mainActivity: MainActivity): RecyclerView.Adapter<GalleryViewHolder>(){

    fun updateList(newList: List<ImageEntity>){
        val galleryDiff = DiffUtils(list, newList)
        val result = DiffUtil.calculateDiff(galleryDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_rv_home, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.paint(list[position], mainActivity)
    }
}