package com.example.galleryapp.data.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.Base64

object BitmapConverter {

    fun converterBitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val byteArray = baos.toByteArray()

        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }

    fun converterStringToBitmap(encodedString: String): Bitmap? {
        return try {
            val encodeByte = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}