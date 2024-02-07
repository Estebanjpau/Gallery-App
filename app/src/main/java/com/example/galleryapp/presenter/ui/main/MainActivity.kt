package com.example.galleryapp.presenter.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.galleryapp.Constants
import com.example.galleryapp.R
import com.example.galleryapp.data.camera.CameraService
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.presenter.ui.camera.CameraFragment
import com.example.galleryapp.presenter.ui.gallery.GalleryFragment
import com.example.galleryapp.presenter.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var camaraFragment: CameraFragment

    @Inject
    lateinit var cameraService: CameraService

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraService.setCamera(cameraService.getOutputDirectory(this), this, cameraExecutor)

        binding.bvnMain.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            val currentFragment = supportFragmentManager.findFragmentById(R.id.content_layout)
            when (it.itemId) {
                R.id.mHome -> {
                    if (currentFragment !is HomeFragment) {
                        transaction.setCustomAnimations(
                            R.anim.enter_from_rigth,
                            R.anim.exit_from_left,
                        )
                        transaction.replace(R.id.content_layout, HomeFragment()).commit()
                    }
                }

                R.id.mGallery -> {
                    if (currentFragment !is GalleryFragment) {
                        transaction.setCustomAnimations(
                            R.anim.enter_from_left,
                            R.anim.exit_from_right,
                        )
                        transaction.replace(R.id.content_layout, GalleryFragment()).commit()
                    }
                }
            }
            true
        }

        supportFragmentManager.beginTransaction().add(R.id.content_layout, HomeFragment()).commit()

        if (allPermissionGranted()) {
            Toast.makeText(this, "We have permission", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSIONS, Constants.REQUEST_CODE_PERMISION
            )
        }

        binding.fabMain.setOnClickListener {
            if (allPermissionGranted()) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_bottom,
                        R.anim.enter_from_top,
                        R.anim.exit_from_top,
                        R.anim.exit_from_bottom
                    )
                    .add(R.id.camera_layout, CameraFragment()).addToBackStack("home").commit()
            } else {
                ActivityCompat.requestPermissions(
                    this, Constants.REQUIRED_PERMISSIONS, Constants.REQUEST_CODE_PERMISION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISION) {

            if (allPermissionGranted()) {
                //
            } else {
                Toast.makeText(this, "permission denay", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionGranted() = Constants.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }
}