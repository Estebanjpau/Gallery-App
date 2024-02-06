package com.example.galleryapp.presenter.ui.drawers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.ModalDetailsDrawerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class DetailsDrawerFragment : BottomSheetDialogFragment() {
    companion object {
        private const val ARG_IMAGE_ID = "imageId"
        private const val ARG_IMAGE_URL = "imageUrl"
        private const val ARG_IMAGE_DATE = "imageDate"

        fun newInstance(imageId: Int, imageUrl: String, imageData: String): DetailsDrawerFragment {
            val fragment = DetailsDrawerFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_ID, imageId)
            args.putString(ARG_IMAGE_URL, imageUrl)
            args.putString(ARG_IMAGE_DATE, imageData)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var imageEntity: ImageEntity

    private var _binding: ModalDetailsDrawerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ModalDetailsDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUrl = arguments?.getString(ARG_IMAGE_URL) ?: ""
        val imageDate = arguments?.getString(ARG_IMAGE_DATE) ?: ""

        binding.tvNameImage.text = "IMG_${imageUrl.takeLast(14)}"
        binding.tvWeightImage.text = getFileSizeString(requireContext(), imageUrl)
        binding.tvDateImage.text = imageDate
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFileSizeString(context: Context, filePath: String): String {
        val file = File(filePath)
        val fileSizeInBytes = file.length()
        val fileSizeInKB = fileSizeInBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024
        return when {
            fileSizeInMB > 1 -> "$fileSizeInMB MB"
            fileSizeInKB > 1 -> "$fileSizeInKB KB"
            else -> "$fileSizeInBytes Bytes"
        }
    }
}