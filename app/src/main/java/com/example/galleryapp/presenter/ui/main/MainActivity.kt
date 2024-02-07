package com.example.galleryapp.presenter.ui.main

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
import com.example.galleryapp.presenter.ui.camera.CameraFragment
import com.example.galleryapp.presenter.ui.gallery.GalleryFragment
import com.example.galleryapp.presenter.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
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

        binding.bvnMain.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.mHome -> transaction.setCustomAnimations(
                    R.anim.enter_from_rigth,
                    R.anim.exit_from_left,
                )
                    .replace(R.id.content_layout, HomeFragment()).commit()

                R.id.mGallery -> transaction.setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_from_right,
                )
                    .replace(R.id.content_layout, GalleryFragment()).commit()
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
}