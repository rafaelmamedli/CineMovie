package com.rafael.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.local.DeleteFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.GetAllFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.InsertFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocalViewModel @Inject constructor(
    private val useCaseAddFavMovie: InsertFavouritesUseCase,
    private val useCaseGetFavMovies: GetAllFavouritesUseCase,
    private val useCaseDeleteMovie: DeleteFavouritesUseCase
) :ViewModel(

) {

    private val _addFavMovie: MutableStateFlow<Resource<FavMovies>> =
        MutableStateFlow(Resource.loading(null))
    val addFavMovie: StateFlow<Resource<FavMovies>>
        get() = _addFavMovie



    private val _getFavMovies: MutableStateFlow<Resource<MutableList<FavMovies>>> =
        MutableStateFlow(Resource.loading(null))
    val getFavMovies: StateFlow<Resource<MutableList<FavMovies>>>
        get() = _getFavMovies


    init {
        getAllFavMovies()
    }


    fun addFavMovie(favMovie:FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseAddFavMovie.insertFavMovieUseCase(favMovie)
    }

    fun deleteFavMovie(favMovie:FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseDeleteMovie.deleteFavMovieUseCase(favMovie)
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