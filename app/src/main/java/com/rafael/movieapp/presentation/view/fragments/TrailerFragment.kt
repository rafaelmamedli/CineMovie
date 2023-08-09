package com.rafael.movieapp.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rafael.movieapp.data.util.MOVIE_ID
import com.rafael.movieapp.data.util.Status.*
import com.rafael.movieapp.databinding.FragmentTrailerBinding
import com.rafael.movieapp.presentation.viewmodel.TrailerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrailerFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentTrailerBinding
    private val viewModel: TrailerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrailerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieId()
        launchWebView()
    }

    private fun getMovieId() {
        val videoId = arguments?.getInt(MOVIE_ID)
        videoId?.let { viewModel.getTrailer(it) }
    }

    private fun launchWebView() {
        lifecycleScope.launch {
            viewModel.trailerMovie.collect { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        val movieKey = resource.data?.results?.last()?.key
                        movieKey?.let {

                        }
                        val video =
                            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$movieKey\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
                        binding.apply {
                            youtubeWebView.loadData(video, "text/html", "utf-8")
                            youtubeWebView.settings.javaScriptEnabled = true
                            youtubeWebView.webChromeClient = WebChromeClient()
                        }
                    }

                    ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }

                    LOADING -> {
                        Log.e("LOADING", resource.message.toString())

                    }
                }
            }
        }
    }
}
