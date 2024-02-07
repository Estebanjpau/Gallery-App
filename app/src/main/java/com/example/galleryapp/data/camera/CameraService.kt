package com.example.galleryapp.data.camera

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.galleryapp.Constants
import com.example.galleryapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraService @Inject constructor(){

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var context: Context
    private lateinit var cameraExecutor: ExecutorService

    fun setCamera(outputDirectory: File, context: Context, cameraExecutor: ExecutorService) {
        this.outputDirectory = outputDirectory
        this.context = context
        this.cameraExecutor = cameraExecutor
    }

    fun startCamera(previewView: PreviewView, lifecycleOwner: LifecycleOwner, context: Context) {
        val cameraProviderFuture = ProcessCameraProvider
            .getInstance(context)

        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

        cameraProviderFuture.addListener({
            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        previewView.surfaceProvider
                    )
                }
            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.d(Constants.TAG, "startCamera Fail: $e")
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun getOutputDirectory(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let { mFile ->
            File(mFile, context.resources.getString(R.string.app_name)).apply {
                mkdir()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    fun takePhoto(callback: (String?) -> Unit) {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                Constants.FILE_NAME_FORMAT,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"
                    Toast.makeText(context, "$msg $savedUri", Toast.LENGTH_SHORT).show()

                    callback(savedUri.toString())
                }

                override fun onError(e: ImageCaptureException) {
                    Log.e(Constants.TAG, "onError: ${e.message}", e)

                    callback(null)
                }
            }
        )
    }

    fun deletePhoto(imageUri: String): Boolean {
        val fileToDelete = File(Uri.parse(imageUri).path ?: "")
        return try {
            if (fileToDelete.exists()) {
                fileToDelete.delete()
            } else {
                false
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            false
        }
    }
}