package com.example.galleryapp.presenter.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.galleryapp.data.entities.ImageEntity

class GalleryDiffUtils(
    private val newList: List<ImageEntity>,
    private val oldList: List<ImageEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}