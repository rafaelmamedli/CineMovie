package com.rafael.movieapp.data.util

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide

fun goTo(it: View, id: Int) {
    Navigation.findNavController(it).navigate(id)
}


fun View.hide(){
    visibility = View.INVISIBLE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.glide(path: String?) {
    val baseUrl = "https://image.tmdb.org/t/p/w342/"
    val imageUrl = baseUrl + path
    Glide.with(this)
        .load(imageUrl)
        .into(this)
}