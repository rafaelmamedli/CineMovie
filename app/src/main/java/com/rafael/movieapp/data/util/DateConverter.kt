package com.rafael.movieapp.data.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateConverter {
    @SuppressLint("NewApi")
    private val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    @SuppressLint("NewApi")
    private val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US)

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val date = LocalDate.parse(inputDate, inputFormatter)
        return outputFormatter.format(date)
    }
}