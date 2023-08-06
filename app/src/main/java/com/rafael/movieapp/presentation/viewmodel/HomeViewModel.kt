package com.rafael.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.remote.GetPopularMoviesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetRecentMoviesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetTopRatedMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCasePopular: GetPopularMoviesUseCase,
    private val useCaseRecent: GetRecentMoviesUseCase,
    private val useCaseTopRated: GetTopRatedMovies,
) :
    ViewModel() {


    private val _popularMovieList: MutableStateFlow<Resource<Movie>> =
        MutableStateFlow(Resource.loading(null))
    val popularMovieList: StateFlow<Resource<Movie>>
        get() = _popularMovieList

    private val _recentMovieList: MutableStateFlow<Resource<Movie>> =
        MutableStateFlow(Resource.loading(null))
    val recentMovieList: StateFlow<Resource<Movie>>
        get() = _recentMovieList

    private val _topRatedMovieList: MutableStateFlow<Resource<Movie>> =
        MutableStateFlow(Resource.loading(null))
    val topRatedMovieList: StateFlow<Resource<Movie>>
        get() = _topRatedMovieList


    init {
        refreshData()
    }


    fun refreshData() {
        getPopularMovies("1")
        getRecentMovies("1")
        getTopRatedMovies("1")
    }

    private fun getPopularMovies(page: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCasePopular.getPopular(page)
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

    private fun getRecentMovies(page: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result =
                    withContext(Dispatchers.IO) {
                        useCaseRecent.getRecent(page)
                    }

                result.collectLatest {
                    _recentMovieList.value = it
                }
            } catch (e: Exception) {
                _recentMovieList.value =
                    Resource.error(e.localizedMessage ?: "Unknown error", null)

            }
        }
    }


    private fun getTopRatedMovies(page: String) {
        viewModelScope.launch(
            Dispatchers.Main
        ) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCaseTopRated.getTop(page)
                }

                result.collectLatest {
                    _topRatedMovieList.value = it
                }
            } catch (e: Exception) {

                _topRatedMovieList.value =
                    Resource.error(e.localizedMessage ?: "Unknown error", null)
            }

        }
    }

}
