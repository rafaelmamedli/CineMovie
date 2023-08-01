package com.rafael.movieapp.presentation.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.DateConverter
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.data.util.Status
import com.rafael.movieapp.data.util.toRoomResult
import com.rafael.movieapp.data.util.toast
import com.rafael.movieapp.databinding.FragmentDetailBinding
import com.rafael.movieapp.presentation.viewmodel.LocalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private var objMovie: Result? = null
    private val viewModelLocal: LocalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getObjects()
        binding.btnFav.setOnClickListener {
            objMovie?.toRoomResult()?.let { it1 -> viewModelLocal.addFavMovie(it1) }
        }
        

    }

    @SuppressLint("NewApi")
    private fun getObjects() {

        val type = arguments?.getString("type", null)
        type?.let {
            when (it) {
                "popular_movie" -> {
                    objMovie = arguments?.getParcelable("popular")
                    binding.apply {
                        txtTitle.text = objMovie?.title
                        txtDescription.text = objMovie?.overview
                        txtImdb.text = objMovie?.vote_average.toString()
                        Glide.with(posterImage)
                            .load("https://image.tmdb.org/t/p/w342/" + objMovie?.backdrop_path)
                            .into(posterImage)
                        val imdb = objMovie?.vote_average?.toFloat()
                        imdb?.let {
                            ratingBar.rating = (imdb / 2)
                        }
                        val formattedDate = objMovie?.release_date?.let { it1 ->
                            DateConverter.formatDate(it1)
                        }
                        txtDate.text = formattedDate
                    }

                }

                "recent_movie" -> {
                    objMovie = arguments?.getParcelable("recent")
                    binding.apply {
                        txtTitle.text = objMovie?.title
                        txtDescription.text = objMovie?.overview
                        txtImdb.text = objMovie?.vote_average.toString()
                        Glide.with(posterImage)
                            .load("https://image.tmdb.org/t/p/w342/" + objMovie?.backdrop_path)
                            .into(posterImage)
                        val imdb = objMovie?.vote_average?.toFloat()
                        imdb?.let {
                            ratingBar.rating = (imdb / 2)
                        }
                        val formattedDate = objMovie?.release_date?.let { it1 ->
                            DateConverter.formatDate(it1)
                        }
                        txtDate.text = formattedDate
                    }
                }

                "top_rated_movie" -> {
                    objMovie = arguments?.getParcelable("top_rated")
                    binding.apply {
                        txtTitle.text = objMovie?.title
                        txtDescription.text = objMovie?.overview
                        txtImdb.text = objMovie?.vote_average.toString()

                        Glide.with(posterImage)
                            .load("https://image.tmdb.org/t/p/w342/" + objMovie?.backdrop_path)
                            .into(posterImage)
                        val imdb = objMovie?.vote_average?.toFloat()
                        imdb?.let {
                            ratingBar.rating = (imdb / 2)
                        }
                        val formattedDate = objMovie?.release_date?.let { it1 ->
                            DateConverter.formatDate(it1)
                        }
                        txtDate.text = formattedDate
                    }
                }

                "all_movie" -> {
                    objMovie = arguments?.getParcelable("all")
                    binding.apply {
                        txtTitle.text = objMovie?.title
                        txtDescription.text = objMovie?.overview
                        txtImdb.text = objMovie?.vote_average.toString()
                        Glide.with(posterImage)
                            .load("https://image.tmdb.org/t/p/w342/" + objMovie?.backdrop_path)
                            .into(posterImage)
                        val imdb = objMovie?.vote_average?.toFloat()
                        imdb?.let {
                            ratingBar.rating = (imdb / 2)
                        }
                        val formattedDate = objMovie?.release_date?.let { it1 ->
                            DateConverter.formatDate(it1)
                        }
                        txtDate.text = formattedDate
                    }
                }


                else -> {}
            }


        }


    }


}