package com.example.galleryapp.presenter.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.galleryapp.R
import com.example.galleryapp.data.camera.CameraService
import com.example.galleryapp.databinding.FragmentCameraBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var cameraService: CameraService

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: CameraViewModel by viewModels()

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColor(requireActivity().window)

        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnDeleteCapturePhoto.setOnClickListener {
            deletePhotowithImagePath()
            showContraintCameraOption()
        }

        binding.btnTakePhoto.setOnClickListener {
            viewModel.takePhoto()
        }

        binding.btnKeepCapturePhoto.setOnClickListener {
            viewModel.keepPhoto(requireContext())
            showContraintCameraOption()
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.photoPath.observe(
            viewLifecycleOwner, Observer {
                if (it != null) {

                    glide.load(it).into(binding.ivPreviewPhoto)

                    binding.clCameraOptions.visibility = View.INVISIBLE
                    binding.clCapturePhotoOptions.visibility = View.VISIBLE
                }
            }
        )

        cameraService.startCamera(binding.previewView, viewLifecycleOwner, requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        deletePhotowithImagePath()
        _binding = null
    }

    fun deletePhotowithImagePath(){
        if (!viewModel.photoPath.value.isNullOrEmpty()){
            viewModel.deletePhoto(viewModel.photoPath.value!!)
        }
    }

    private fun showContraintCameraOption(){
        binding.clCapturePhotoOptions.visibility = View.INVISIBLE
        binding.clCameraOptions.visibility = View.VISIBLE
    }

    private fun changeStatusBarColor(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.black, null)
    }
}