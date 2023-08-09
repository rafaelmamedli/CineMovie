package com.rafael.movieapp.data.util

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.movie.Result
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Fragment.toast(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(msg: String?,) {
    view?.let {
        Snackbar.make(it, msg ?: "", Snackbar.LENGTH_SHORT).show()
    }
}
fun ImageView.glide(path: String?) {
    val baseUrl = "https://image.tmdb.org/t/p/w342/"
    val imageUrl = baseUrl + path
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}

fun Result.toRoomResult(): FavMovies {
    return FavMovies(
        id=id,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        genrestring = genrestring
    )
}

fun Fragment.disableBackPressed() {
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String?.formatDate(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.US)

    return try {
        val date = LocalDate.parse(this, inputFormatter)
        outputFormatter.format(date)
    } catch (e: DateTimeParseException) {
        e.printStackTrace()
        ""
    }
}

