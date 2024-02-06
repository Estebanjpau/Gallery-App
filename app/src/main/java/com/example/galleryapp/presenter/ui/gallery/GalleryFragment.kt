package com.example.galleryapp.presenter.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var GalleryAdapter: GalleryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GalleryAdapter = GalleryAdapter(emptyList(), requireActivity().supportFragmentManager)

        viewModel.listImageEntity.observe(
            viewLifecycleOwner, Observer { images ->
                images?.let {
                    if (it.isNotEmpty()) {
                        GalleryAdapter.updateList(it)
                    }
                }
            })

        binding.rvGalleryF.apply {
            layoutManager = GridLayoutManager(requireContext(), setWidthRecyclerItemsColum())
            adapter = GalleryAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setWidthRecyclerItemsColum(): Int {
        val widthScreen = resources.displayMetrics.widthPixels
        val sizeImage = resources.getDimensionPixelSize(R.dimen.recycler_gallery_item_width)
        val numColumns = widthScreen / sizeImage
        return if (numColumns > 0) numColumns else 1
    }

    private fun changeStatusBarColor(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.background_light, null)
    }
}