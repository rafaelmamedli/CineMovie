package com.rafael.movieapp.presentation.view.fragments.trailer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.remote.movie.Trailer
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.remote.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val trailerUseCase: GetMovieTrailerUseCase
) : ViewModel() {

    private val _trailerMovie: MutableStateFlow<Resource<Trailer>> =
        MutableStateFlow(Resource.loading(null))
    val trailerMovie = _trailerMovie.asStateFlow()


    fun getTrailer(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    trailerUseCase(movieId)
                }
                result.collectLatest { resource ->
                    resource.data?.results?.let { results ->
                        if (results.isNotEmpty()) {
                            _trailerMovie.value = resource
                        }
                    }
                }
            } catch (e: Exception) {
                _trailerMovie.value = Resource.error(e.localizedMessage ?: "Unknown error", null)
            }
        }
    }


}