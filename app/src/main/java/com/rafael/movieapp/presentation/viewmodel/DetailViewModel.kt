package com.rafael.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.detail.Details
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.local.DeleteFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.GetAllFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.InsertFavouritesUseCase
import com.rafael.movieapp.domein.use_case.remote.GetMovieArtistsUseCase
import com.rafael.movieapp.domein.use_case.remote.GetMovieTrailerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val useCaseAddFavMovie: InsertFavouritesUseCase,
    private val useCaseGetFavMovies: GetAllFavouritesUseCase,
    private val useCaseDeleteMovie: DeleteFavouritesUseCase,
    private val useCaseTrailerMovie: GetMovieTrailerUseCase,
    private val useCaseArtists: GetMovieArtistsUseCase
) : ViewModel() {
    init {
        getAllFavMovies()
    }

    private val _getFavMovies: MutableStateFlow<Resource<MutableList<FavMovies>>> =
        MutableStateFlow(Resource.loading(null))
    val getFavMovies: StateFlow<Resource<MutableList<FavMovies>>>
        get() = _getFavMovies

    private val _getCrew: MutableStateFlow<Resource<Details>> =
        MutableStateFlow(Resource.loading(null))
    val getCrew = _getCrew.asStateFlow()






    fun addFavMovie(favMovie: FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseAddFavMovie.insert(favMovie)
    }

    fun deleteFavMovie(favMovie: FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseDeleteMovie.delete(favMovie)
    }

    fun crewMovie(movieId: Int) =
        viewModelScope.launch(Dispatchers.Main) {

            try {
                val result = withContext(Dispatchers.IO) {
                    useCaseArtists.getArtists(movieId)
                }
                result.collectLatest {
                    _getCrew.value = it
                }
            } catch (e: Exception) {
                _getCrew.value =
                    Resource.error(e.localizedMessage ?: "Unknown error", null)

            }


        }

    private fun getAllFavMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseGetFavMovies.getAll()
                .catch { e ->
                    _getFavMovies.value = Resource.error("Error getting fav movies", null)
                }
                .collect { favMovies ->
                    _getFavMovies.value = Resource.success(favMovies)
                }
        }
    }


}