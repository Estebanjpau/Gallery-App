package com.example.galleryapp.presenter.ui.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.galleryapp.Constants
import com.example.galleryapp.R
import com.example.galleryapp.databinding.ActivityMainBinding
import com.example.galleryapp.domain.CameraService
import com.example.galleryapp.presenter.ui.fragments.GalleryFragment
import com.example.galleryapp.presenter.ui.fragments.camera.CameraFragment
import com.example.galleryapp.presenter.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var camaraFragment: CameraFragment

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.content_layout, GalleryFragment()).commit()

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

        viewModel.photoPath.observe(this, Observer { path ->
            Toast.makeText(this, "Foto guardada en $path", Toast.LENGTH_SHORT).show()
        })
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

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, resources.getString(R.string.app_name)).apply {
                mkdir()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }
}