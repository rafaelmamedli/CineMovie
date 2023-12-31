package com.rafael.movieapp.presentation.view.fragments.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.movie.Result
import com.rafael.movieapp.data.models.remote.detail.Cast
import com.rafael.movieapp.data.util.ALL
import com.rafael.movieapp.data.util.ALL_MOVIE
import com.rafael.movieapp.data.util.FAVOURITE
import com.rafael.movieapp.data.util.FAVOURITE_MOVIE
import com.rafael.movieapp.data.util.MOVIE_ID
import com.rafael.movieapp.data.util.POPULAR
import com.rafael.movieapp.data.util.POPULAR_MOVIE
import com.rafael.movieapp.data.util.RECENT
import com.rafael.movieapp.data.util.RECENT_MOVIE
import com.rafael.movieapp.data.util.SEARCHED
import com.rafael.movieapp.data.util.SEARCHED_MOVIE
import com.rafael.movieapp.data.util.Status.*
import com.rafael.movieapp.data.util.TOP_RATED
import com.rafael.movieapp.data.util.TOP_RATED_MOVIE
import com.rafael.movieapp.data.util.formatDate
import com.rafael.movieapp.data.util.glide
import com.rafael.movieapp.data.util.hide
import com.rafael.movieapp.data.util.showSnackBar
import com.rafael.movieapp.data.util.toRoomResult
import com.rafael.movieapp.databinding.FragmentDetailBinding
import com.rafael.movieapp.presentation.view.adapter.CastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private var objMovie: Result? = null
    private val viewModel: DetailViewModel by viewModels()
    private var isMovieInFavorites = false
    lateinit var adapter: CastAdapter
    private var movieId: Int? = null
    private val listCast = mutableListOf<Cast>()


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
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        getAdapters()
        getObjects()
        movieId?.let { getCrew(it) }
        checkMovie()
        checkBox()
        goToTrailer()

    }

    private fun getAdapters() {
        adapter = CastAdapter(listCast)
        binding.recyclerView.adapter = adapter
    }

    private fun goToTrailer() {
        binding.btnTrailer.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailFragment_to_trailerFragment,
                Bundle().apply { movieId?.let { putInt(MOVIE_ID, it) }
                })
        }

    }


    private fun getCrew(movieId: Int) {
        viewModel.crewMovie(movieId)
        lifecycleScope.launch {
            viewModel.getCrew.collect { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        adapter = CastAdapter(listCast)
                        binding.recyclerView.adapter = adapter
                        val cast = resource.data?.cast
                        cast?.let { listCast.addAll(it) }
                    }

                    ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }

                    LOADING -> {

                    }
                }
            }
        }

    }


    private fun checkMovie() = lifecycleScope.launch {
        viewModel.getFavMovies.collect { resource ->
            when (resource.status) {
                SUCCESS -> {
                    isMovieInFavorites =
                        resource.data?.any { it.title == objMovie?.title } == true // Added this line
                    binding.btnFav.isChecked = isMovieInFavorites
                }

                ERROR -> Log.e("ERROR", resource.message.toString())
                LOADING -> {}
            }
        }

    }

    private fun checkBox() {
        binding.btnFav.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isMovieInFavorites) {
                    objMovie?.let { viewModel.addFavMovie(it.toRoomResult()) }
                    isMovieInFavorites = true
                    showSnackBar("Added to favorite")
                }
            } else {
                if (isMovieInFavorites) {
                    viewModel.getFavMovies.value.data?.find { it.title == objMovie?.title }
                        ?.let {
                            viewModel.deleteFavMovie(it)
                            showSnackBar("Removed from favorites")
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
            POPULAR_MOVIE -> arguments?.getParcelable(POPULAR)
            RECENT_MOVIE -> arguments?.getParcelable(RECENT)
            TOP_RATED_MOVIE -> arguments?.getParcelable(TOP_RATED)
            ALL_MOVIE -> arguments?.getParcelable(ALL)
            SEARCHED_MOVIE -> arguments?.getParcelable(SEARCHED)
            else -> null
        }
        val favMovie: FavMovies? =
            type.takeIf { it == FAVOURITE_MOVIE }?.let {
                binding.btnFav.hide()
                arguments?.getParcelable(FAVOURITE)
            }


        result?.let {
            objMovie = it
            movieId = it.id
            binding.apply {
                txtTitle.text = it.title
                txtDescription.text = it.overview
                txtImdb.text = it.vote_average.toString()
                backDrop.glide(it.backdrop_path)
                posterImage.glide(it.poster_path)
                val imdb = it.vote_average?.toFloat()
                imdb?.let { ratingBar.rating = (it / 2) }
                txtDate.text = it.release_date.formatDate()
            }
        }

        favMovie?.let {
            movieId = it.id
            binding.apply {
                txtTitle.text = it.title
                txtDescription.text = it.overview
                txtImdb.text = it.vote_average.toString()
                backDrop.glide(it.backdrop_path)
                posterImage.glide(it.poster_path)
                val imdb = it.vote_average?.toFloat()
                imdb?.let { ratingBar.rating = (it / 2) }
                val formattedDate = it.release_date?.formatDate()
                txtDate.text = formattedDate
            }
        }
    }


}