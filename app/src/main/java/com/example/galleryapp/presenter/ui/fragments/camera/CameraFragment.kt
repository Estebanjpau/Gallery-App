package com.example.galleryapp.presenter.ui.fragments.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.galleryapp.databinding.FragmentCameraBinding
import com.example.galleryapp.domain.CameraService
import com.example.galleryapp.presenter.ui.viewmodels.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment @Inject constructor(): Fragment() {

    @Inject
    lateinit var cameraService: CameraService

    private val viewModel: CameraViewModel by viewModels()

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnTakePhoto.setOnClickListener {
            viewModel.takePhoto()
        }

        cameraService.setCamera(cameraService.getOutputDirectory(requireContext()), requireContext())

        cameraService.startCamera(binding.previewView, viewLifecycleOwner, requireContext())
    }
}