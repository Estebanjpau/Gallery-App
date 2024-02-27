
package com.example.galleryapp.presenter.ui.capturePhoto

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.galleryapp.R
import com.example.galleryapp.databinding.FragmentCapturedPhotoBinding
import com.example.galleryapp.presenter.utils.listeners.CameraContainerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CapturedPhotoFragment : Fragment() {

    private val args: CapturedPhotoFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: CapturedPhotoViewModel by viewModels()

    private var _binding: FragmentCapturedPhotoBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: CameraContainerListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CameraContainerListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCapturedPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glide.load(args.path).into(binding.ivPreviewPhoto)

        binding.btnDeleteCapturePhoto.setOnClickListener {
            deletePhotoWithImagePath()
        }

        binding.btnKeepCapturePhoto.setOnClickListener {
            viewModel.keepPhoto(binding.root, args.path)
            findNavController().navigate(R.id.cameraFragment)
            listener.onHideNavCameraContainer()
        }
    }

    private fun deletePhotoWithImagePath(){
        if (args.path.isNotEmpty()){
            viewModel.deletePhoto(args.path)
            findNavController().navigate(R.id.cameraFragment)
        }
    }
}