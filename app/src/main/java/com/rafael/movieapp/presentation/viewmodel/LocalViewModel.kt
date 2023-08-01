package com.rafael.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.bodyfattracker.data.room.MovieDao
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Result
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.local.GetAllFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.InsertFavouritesUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocalViewModel @Inject constructor(
    private val useCaseAddFavMovie: InsertFavouritesUseCase,
    private val useCaseGEtFavouritesMovies: GetAllFavouritesUseCase


) :ViewModel(

) {


    private val _addFavMovie: MutableStateFlow<Resource<FavMovies>> =
        MutableStateFlow(Resource.loading(null))
    val addFavMovie: StateFlow<Resource<FavMovies>>
        get() = _addFavMovie


    fun addFavMovie(favMovie:FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseAddFavMovie.insertFavMovieUseCase(favMovie)
    }





}