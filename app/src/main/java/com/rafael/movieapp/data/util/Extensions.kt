package com.rafael.movieapp.data.util

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.models.remote.Result

fun goTo(it: View, id: Int) {
    Navigation.findNavController(it).navigate(id)
}


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

fun NavController.navigateWithBundle(actionId: Int, type: String, movie: Parcelable) {
    navigate(
        actionId, Bundle().apply {
            putString("type", type)
            putParcelable(type, movie)
        }
    )
}

fun NavController.toDetail(type: String, movie: Result) {
    navigateWithBundle(R.id.action_homeFragment_to_detailFragment, type, movie)
}

fun NavController.toAllSee(type: String, movie: Movie) {
    navigateWithBundle(R.id.action_homeFragment_to_seeAllFragment, type, movie )
}




fun ImageView.glide(path: String?) {
    val baseUrl = "https://image.tmdb.org/t/p/w342/"
    val imageUrl = baseUrl + path
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}