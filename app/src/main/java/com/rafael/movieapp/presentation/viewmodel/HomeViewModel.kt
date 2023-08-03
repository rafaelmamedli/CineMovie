package com.rafael.movieapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.local.GetAllFavouritesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetMovieByNameUseCase
import com.rafael.movieapp.domein.use_case.remote.GetPopularMoviesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetRecentMoviesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetTopRatedMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
    private val useCaseGetFavMovies: GetAllFavouritesUseCase
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

    private val _getFavMovies: MutableStateFlow<Resource<MutableList<FavMovies>>> =
        MutableStateFlow(Resource.loading(null))
    val getFavMovies: StateFlow<Resource<MutableList<FavMovies>>>
        get() = _getFavMovies




    init {
        getPopularMovies("1")
        getRecentMovies("1")
        getTopRatedMovies("1")
        Log.d("TAG", "ViewModel")
    }

    private fun getPopularMovies(page: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCasePopular.getPopularMoviesUseCase(page)
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
                        useCaseRecent.getRecentMoviesUseCase(page)
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

        viewModelScope.launch(Dispatchers.Main
        ) {
            try {
                val result = withContext(Dispatchers.IO) {
                    useCaseTopRated.getTopRatedMoviesUseCase(page)
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


    private fun getAllFavMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseGetFavMovies.getAllFavMovieUseCase()
                .catch { e ->
                    _getFavMovies.value = Resource.error("Error getting fav movies", null)
                }
                .collect { favMovies ->
                    _getFavMovies.value = Resource.success(favMovies)
                }
        }
    }





}
