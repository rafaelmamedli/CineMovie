package com.rafael.movieapp.domein.use_case.local

import com.rafael.movieapp.data.repository.local.FavMoviesRepository
import javax.inject.Inject

class GetAllFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepository) {

    suspend fun getAll() = repo.getAllFavMovies()


}