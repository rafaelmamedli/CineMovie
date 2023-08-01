package com.rafael.movieapp.domein.use_case.local

import com.rafael.bodyfattracker.data.repo.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.models.local.FavMovies
import javax.inject.Inject

class GetAllFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepositoryImpl) {

    suspend fun getAllFavMovieUseCase(favMovie: FavMovies) = repo.getAllFavMovies()


}