package com.rafael.movieapp.presentation.view.fragments.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.remote.movie.Movie
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.remote.GetMovieByNameUseCase
import com.rafael.movieapp.domein.use_case.remote.GetRecentMoviesUseCase
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
class SearchViewModel @Inject constructor(

    private val useCaseMovieByName: GetMovieByNameUseCase,
    private val useCasePopular: GetRecentMoviesUseCase
) : ViewModel() {

    init {
        getPopularMovies()
    }

    private val _popularMovieList: MutableStateFlow<Resource<Movie>> =
        MutableStateFlow(Resource.loading(null))
    val popularMovieList = _popularMovieList.asStateFlow()


    private val _movieByName: MutableStateFlow<Resource<Movie>> =
        MutableStateFlow(Resource.loading(null))
    val movieByName = _movieByName.asStateFlow()


    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCasePopular.invoke("1")
                }
                result.collectLatest {
                    _popularMovieList.value = it
                }
            } catch (e: Exception) {
                _popularMovieList.value =
                    Resource.error(e.localizedMessage ?: "Unknown error", null)

            }
        }
    }


    fun getMovieByName(movieName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCaseMovieByName.invoke(movieName)
                }
                result.collectLatest {
                    Log.d("SEARCH", it.toString())
                    _movieByName.value = it
                }
            } catch (e: Exception) {
                _movieByName.value =
                    Resource.error(e.localizedMessage ?: "Unknown error", null)
            }
        }


    }

}