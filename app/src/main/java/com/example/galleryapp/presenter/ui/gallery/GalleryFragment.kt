package com.example.galleryapp.presenter.ui.gallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galleryapp.Constants
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by viewModels()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var galleryAdapter: GalleryAdapter

    private var selectedImagePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryAdapter = GalleryAdapter(emptyList(), requireActivity().supportFragmentManager)

        changeStatusBarColor(requireActivity().window)

        viewModel.listImageEntity.observe(
            viewLifecycleOwner, Observer { images ->
                images?.let {
                    if (it.isNotEmpty()) {
                        galleryAdapter.updateList(it)
                    }
                }
            })

        binding.btnPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, Constants.PICK_IMAGE_REQUEST_CODE)
        }

        binding.rvGalleryF.apply {
            layoutManager = GridLayoutManager(requireContext(), setWidthRecyclerItemsColum())
            adapter = galleryAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { path ->
                selectedImagePath = path
                viewModel.saveCopyImage(path)

            }
        }
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