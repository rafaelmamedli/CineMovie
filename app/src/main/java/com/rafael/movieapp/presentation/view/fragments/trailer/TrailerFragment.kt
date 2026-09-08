package com.rafael.movieapp.presentation.view.fragments.trailer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rafael.movieapp.R
import com.rafael.movieapp.data.models.remote.movie.ResultX
import com.rafael.movieapp.data.util.MOVIE_ID
import com.rafael.movieapp.data.util.Status.*
import com.rafael.movieapp.data.util.gone
import com.rafael.movieapp.data.util.show
import com.rafael.movieapp.databinding.FragmentTrailerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TrailerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTrailerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrailerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrailerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpWebView()
        getMovieId()
        observeTrailer()
    }

    private fun getMovieId() {
        val videoId = arguments?.getInt(MOVIE_ID)
        if (videoId == null || videoId == 0) {
            showMessage(getString(R.string.trailer_not_available))
            return
        }
        viewModel.getTrailer(videoId)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        binding.youtubeWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = true
            // No local content is loaded into this WebView, so file access stays off.
            allowFileAccess = false
            allowContentAccess = false
        }
        binding.youtubeWebView.webChromeClient = WebChromeClient()
    }

    private fun observeTrailer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trailerMovie.collect { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        val key = resource.data?.results?.bestTrailerKey()
                        if (key.isNullOrBlank()) {
                            showMessage(getString(R.string.trailer_not_available))
                        } else {
                            playTrailer(key)
                        }
                    }

                    ERROR -> showMessage(
                        resource.message ?: getString(R.string.trailer_load_error)
                    )

                    LOADING -> {
                        binding.progressBar.show()
                        binding.txtMessage.gone()
                    }
                }
            }
        }
    }

    /** Prefers the official YouTube trailer, then any YouTube video, then anything at all. */
    private fun List<ResultX>.bestTrailerKey(): String? {
        val youtube = filter { it.key?.isNotBlank() == true && (it.site == null || it.site == YOUTUBE) }
        return (youtube.firstOrNull { it.type == TRAILER && it.official == true }
            ?: youtube.firstOrNull { it.type == TRAILER }
            ?: youtube.firstOrNull()
            ?: firstOrNull { it.key?.isNotBlank() == true })?.key
    }

    private fun playTrailer(videoKey: String) {
        binding.progressBar.gone()
        binding.txtMessage.gone()
        binding.youtubeWebView.show()

        val video = "<html><body style=\"margin:0;padding:0;background:#000;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/$videoKey\" " +
                "title=\"YouTube video player\" frameborder=\"0\" " +
                "allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture; web-share\" " +
                "allowfullscreen></iframe></body></html>"

        // A real https base url is required, otherwise YouTube refuses to embed the player.
        binding.youtubeWebView.loadDataWithBaseURL(
            "https://www.youtube.com", video, "text/html", "utf-8", null
        )
    }

    private fun showMessage(message: String) {
        binding.progressBar.gone()
        binding.youtubeWebView.gone()
        binding.txtMessage.text = message
        binding.txtMessage.show()
    }

    override fun onDestroyView() {
        // Stop playback and release the WebView so no audio keeps playing after dismiss.
        _binding?.youtubeWebView?.apply {
            loadUrl("about:blank")
            stopLoading()
            destroy()
        }
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val YOUTUBE = "YouTube"
        const val TRAILER = "Trailer"
    }
}
