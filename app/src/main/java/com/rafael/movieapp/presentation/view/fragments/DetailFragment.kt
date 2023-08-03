package com.rafael.movieapp.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.DateConverter
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.glide
import com.rafael.movieapp.data.util.hide
import com.rafael.movieapp.data.util.toRoomResult
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentDetailBinding
import com.rafael.movieapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private var objMovie: Result? = null
    private val viewModel: DetailViewModel by viewModels()
    private var isMovieInFavorites = false // Added this line


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getObjects()
        checkMovie()
        checkBox()

    }

    private fun checkMovie() = lifecycleScope.launch {
        viewModel.getFavMovies.collect { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    isMovieInFavorites =
                        resource.data?.any { it.title == objMovie?.title } == true // Added this line
                    binding.btnFav.isChecked = isMovieInFavorites
                }

                else -> {}
            }
        }

    }

    private fun checkBox() {
        binding.btnFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isMovieInFavorites) {
                    objMovie?.let { viewModel.addFavMovie(it.toRoomResult()) }
                    toast("Added to favorites")
                    isMovieInFavorites = true
                }
            } else {
                if (isMovieInFavorites) {
                    val value =
                        viewModel.getFavMovies.value.data?.find { it.title == objMovie?.title }
                            ?.let {
                                viewModel.deleteFavMovie(it)
                                toast("Removed from favorites")
                                isMovieInFavorites = false
                            }
                }
            }
        }
    }


    @SuppressLint("NewApi")
    private fun getObjects() {
        val type = arguments?.getString("type", null)
        val result: Result? = when (type) {
            "popular_movie" -> arguments?.getParcelable("popular")
            "recent_movie" -> arguments?.getParcelable("recent")
            "top_rated_movie" -> arguments?.getParcelable("top_rated")
            "all_movie" -> arguments?.getParcelable("all")
            "searched_movie" -> arguments?.getParcelable("searched")
            else -> null
        }
        val favMovie: FavMovies? =
            type.takeIf { it == "favourite_movie" }?.let {
                binding.btnFav.hide()
                arguments?.getParcelable("favourite")
            }


        result?.let {
            objMovie = it
            binding.apply {
                txtTitle.text = it.title
                txtDescription.text = it.overview
                txtImdb.text = it.vote_average.toString()
                backDrop.glide(it.backdrop_path)
                posterImage.glide(it.poster_path)
                val imdb = it.vote_average?.toFloat()
                imdb?.let { ratingBar.rating = (it / 2) }
                val formattedDate = it.release_date?.let { DateConverter.formatDate(it) }
                txtDate.text = formattedDate
            }
        }

        favMovie?.let {
            binding.apply {
                txtTitle.text = it.title
                txtDescription.text = it.overview
                txtImdb.text = it.vote_average.toString()
                backDrop.glide(it.backdrop_path)
                posterImage.glide(it.poster_path)
                val imdb = it.vote_average?.toFloat()
                imdb?.let { ratingBar.rating = (it / 2) }
                val formattedDate = it.release_date?.let { DateConverter.formatDate(it) }
                txtDate.text = formattedDate
            }
        }
    }


}