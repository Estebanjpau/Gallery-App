package com.example.galleryapp.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {
    fun convertirFecha(string: String): String {
        try {
            val oldFormat = SimpleDateFormat("yyMMddHHmm", Locale.getDefault())
            val newFormat = SimpleDateFormat("dd 'de' MMM 'de' yyyy ~ HH:mm", Locale.getDefault())

            val fecha: Date? = oldFormat.parse(string)

            if (fecha != null) {
                return newFormat.format(fecha)
            } else {
                return "Fecha inválida"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "Fecha inválida"
    }
}