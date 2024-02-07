package com.example.galleryapp.presenter.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.FragmentDetailsBinding
import com.example.galleryapp.presenter.ui.drawers.DetailsDrawerFragment
import com.example.galleryapp.presenter.ui.gallery.GalleryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE_ID = "imageId"
        private const val ARG_IMAGE_URL = "imageUrl"
        private const val ARG_IMAGE_DATE = "imageDate"

        fun newInstance(imageId: Int, imageUrl: String, imageData: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_ID, imageId)
            args.putString(ARG_IMAGE_URL, imageUrl)
            args.putString(ARG_IMAGE_DATE, imageData)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var imageEntity: ImageEntity

    private val viewModel:DetailsViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColor(requireActivity().window)

        imageEntity = ImageEntity(
            arguments?.getInt(ARG_IMAGE_ID),
            arguments?.getString(ARG_IMAGE_URL)!!,
            arguments?.getString(ARG_IMAGE_DATE)!!
        )

        binding.clDetailScreen.setOnClickListener {

        }

        binding.btnDeletePhoto.setOnClickListener {
            popBackStack()
            viewModel.deletePhoto(imageEntity.imageString, imageEntity.id!!)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.content_layout, GalleryFragment()).commit()
        }

        binding.btnExpandDetails.setOnClickListener {
            val detailsDrawerFragment = DetailsDrawerFragment.newInstance(imageEntity.id!!, imageEntity.imageString, imageEntity.dateString)

            detailsDrawerFragment.show(parentFragmentManager, "DetailsDrawerFragment")
        }

        Glide.with(requireContext()).load(imageEntity.imageString).centerCrop().into(binding.ivDetailsScreen)

        binding.btnDetailsPopBack.setOnClickListener {
            popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun popBackStack(){
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun changeStatusBarColor(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.black, null)
    }
}