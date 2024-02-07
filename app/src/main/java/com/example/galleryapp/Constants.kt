package com.example.galleryapp

import android.Manifest

object Constants {

    const val TAG = "cameraX"
    const val FILE_NAME_FORMAT = "yyMMddHHss"
    const val REQUEST_CODE_PERMISION = 123
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    const val PICK_IMAGE_REQUEST_CODE = 1001

}