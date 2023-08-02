package com.rafael.movieapp.domein.use_case.local

import com.rafael.movieapp.data.repository.local.FavMoviesRepositoryImpl
import javax.inject.Inject

class GetAllFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepositoryImpl) {

    suspend fun getAllFavMovieUseCase() = repo.getAllFavMovies()


}