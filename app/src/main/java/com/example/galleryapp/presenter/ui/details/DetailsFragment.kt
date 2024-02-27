package com.example.galleryapp.presenter.ui.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.galleryapp.R
import com.example.galleryapp.data.entities.ImageEntity
import com.example.galleryapp.databinding.FragmentDetailsBinding
import com.example.galleryapp.presenter.ui.gallery.GalleryFragment
import com.example.galleryapp.presenter.utils.drawers.DetailsDrawerFragment
import com.example.galleryapp.presenter.utils.listeners.CameraContainerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel:DetailsViewModel by viewModels()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: CameraContainerListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColor(requireActivity().window)

        viewModel.loadEntity(args.id)

        binding.btnDeletePhoto.setOnClickListener {
            popBackStack()
            viewModel.deletePhoto(viewModel.imageEntity.value!!.imageString, viewModel.imageEntity.value!!.id!!)
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, GalleryFragment()).commit()
        }

        binding.btnExpandDetails.setOnClickListener {
            val detailsDrawerFragment = DetailsDrawerFragment.newInstance(viewModel.imageEntity.value!!.id!!, viewModel.imageEntity.value!!.imageString, viewModel.imageEntity.value!!.dateString)

            detailsDrawerFragment.show(parentFragmentManager, "DetailsDrawerFragment")
        }

        viewModel.imageEntity.observe(viewLifecycleOwner) { entity ->
            entity?.let {
                glide.load(entity.imageString).centerCrop().into(binding.ivDetailsScreen)
            }
        }

        binding.btnDetailsPopBack.setOnClickListener {
            listener.onHideNavCameraContainer()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CameraContainerListener
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