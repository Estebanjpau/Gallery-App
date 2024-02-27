package com.example.galleryapp.presenter.ui.camera

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.galleryapp.R
import com.example.galleryapp.data.camera.CameraService
import com.example.galleryapp.databinding.FragmentCameraBinding
import com.example.galleryapp.presenter.utils.listeners.CameraContainerListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class CameraFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var cameraService: CameraService

    private val viewModel: CameraViewModel by viewModels()

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var listener: CameraContainerListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CameraContainerListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColor(requireActivity().window)

        cameraService.startCamera(binding.previewView, viewLifecycleOwner, requireContext())
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.btnBack.setOnClickListener {
            listener.onHideNavCameraContainer()
        }

        binding.btnTakePhoto.setOnClickListener {
            viewModel.takePhoto()
        }

        viewModel.photoPath.observe(
            viewLifecycleOwner, Observer {
                if (it != null) {
                    findNavController().navigate(
                        CameraFragmentDirections.actionCameraFragmentToCapturedPhotoFragment(it)
                    )
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        _binding = null
    }

    private fun changeStatusBarColor(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.black, null)
    }
}