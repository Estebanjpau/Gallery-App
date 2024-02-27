package com.example.galleryapp.presenter.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.galleryapp.Constants
import com.example.galleryapp.R
import com.example.galleryapp.data.camera.CameraService
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.presenter.ui.camera.CameraFragmentDirections
import com.example.galleryapp.presenter.ui.gallery.GalleryFragmentDirections
import com.example.galleryapp.presenter.ui.home.HomeFragmentDirections
import com.example.galleryapp.presenter.utils.listeners.CameraContainerListener
import com.example.galleryapp.presenter.utils.SnackbarManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity(), CameraContainerListener {

    @Inject
    lateinit var cameraService: CameraService

    @Inject
    lateinit var snackbarManager: SnackbarManager

    private var lastActionId: Int? = null

    lateinit var binding: ActivityMainBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCamera()
        initNavigation()
        verifyPermissions()

        binding.navCameraContainer.visibility = View.GONE

        binding.fabMain.setOnClickListener {
            if (allPermissionGranted()) {
                onHideNavHostContainer()
            } else {
                ActivityCompat.requestPermissions(
                    this, Constants.REQUIRED_PERMISSIONS, Constants.REQUEST_CODE_PERMISION
                )
            }
        }
    }

    private fun verifyPermissions() {
        if (allPermissionGranted()) {
            Timber.v("PERMISSION GRANTED")
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS, Constants.REQUEST_CODE_PERMISION
            )
        }
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController
        binding.bnvMain.setupWithNavController(navController)
        binding.bnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    if (lastActionId != R.id.action_gallery_to_home) {
                        navController.navigate(GalleryFragmentDirections.actionGalleryToHome())
                        lastActionId = R.id.action_gallery_to_home
                    }
                }
                R.id.galleryFragment -> {
                    if (lastActionId != R.id.action_home_to_gallery) {
                        navController.navigate(HomeFragmentDirections.actionHomeToGallery())
                        lastActionId = R.id.action_home_to_gallery
                    }
                }
            }
            true
        }
    }

    private fun initCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraService.setCamera(cameraService.getOutputDirectory(this), this, cameraExecutor)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISION) {

            if (allPermissionGranted()) {
                snackbarManager.showSnackbar("Permissions granted", binding.root)
                Timber.i("THE USER HAS NOT GRANTED THE CAMERA PERMISSION")
            } else {
                snackbarManager.showSnackbar("Permissions denay", binding.root)
                Timber.i("THE USER GRANTED THE CAMERA PERMISSION ")
            }
        }
    }

    private fun allPermissionGranted() = Constants.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onHideNavCameraContainer() {
        binding.navHostFragment.visibility = View.VISIBLE
        findNavController(R.id.nav_camera_container).navigate(R.id.cameraFragment)
        binding.navCameraContainer.visibility = View.GONE
    }

   private fun onHideNavHostContainer() {
        binding.navCameraContainer.visibility = View.VISIBLE
        binding.navHostFragment.visibility = View.GONE
    }

    fun showDetailsScreen(id: Int){
        onHideNavHostContainer()
        findNavController(R.id.nav_camera_container).navigate(CameraFragmentDirections.actionCameraFragmentToDetailsFragment(id))
    }
}