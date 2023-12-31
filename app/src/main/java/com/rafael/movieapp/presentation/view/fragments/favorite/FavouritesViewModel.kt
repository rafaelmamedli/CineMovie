package com.rafael.movieapp.presentation.view.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.domein.use_case.local.DeleteFavouritesUseCase
import com.rafael.movieapp.domein.use_case.local.GetAllFavouritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val useCaseGetFavMovies: GetAllFavouritesUseCase,
    private val useCaseDeleteMovie: DeleteFavouritesUseCase
) : ViewModel(

) {

    private val _getFavMovies: MutableStateFlow<Resource<MutableList<FavMovies>>> =
        MutableStateFlow(Resource.loading(null))
    val getFavMovies = _getFavMovies.asStateFlow()


    init {
        getAllFavMovies()
    }

    fun deleteFavMovie(favMovie: FavMovies) = viewModelScope.launch(Dispatchers.IO) {
        useCaseDeleteMovie(favMovie)
    }

    private fun getAllFavMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            useCaseGetFavMovies()
                .catch { e ->
                    _getFavMovies.value = Resource.error("Error getting fav movies", null)
                }
                .collect { favMovies ->
                    _getFavMovies.value = Resource.success(favMovies)
                }
        }
    }




}