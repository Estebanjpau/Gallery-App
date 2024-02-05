package com.example.galleryapp.presenter.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.data.ImageDB
import com.example.galleryapp.data.ImageRepository
import com.example.galleryapp.data.entities.ImageModel
import com.example.galleryapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var vmGalleryAdapter: GalleryAdapter

    private val repository: ImageRepository by lazy {
        ImageRepository(ImageDB.getIntance(requireContext()).getImageDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vmGalleryAdapter = GalleryAdapter(
            listOf(
                ImageModel(23, "as"),
                ImageModel(13, "sas"),
                ImageModel(53, "aes"),
                ImageModel(53, "aes"),
                ImageModel(53, "aes"),
                ImageModel(53, "aes"),
                ImageModel(53, "aes"),
            )
        )

        binding.rvGalleryF.apply {
            layoutManager = GridLayoutManager(requireContext(), setWidthRecyclerItemsColum())
            adapter = vmGalleryAdapter
        }
    }

    private fun setWidthRecyclerItemsColum(): Int {
        val widthScreen = resources.displayMetrics.widthPixels
        val sizeImage = resources.getDimensionPixelSize(R.dimen.recycler_gallery_item_width)
        val numColumns = widthScreen / sizeImage
        return if (numColumns > 0) numColumns else 1
    }
}