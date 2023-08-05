package com.rafael.movieapp.domein.use_case.local

import com.rafael.movieapp.data.repository.local.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.repository.local.FavMoviesRepository
import javax.inject.Inject

class DeleteFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepository) {

    suspend fun deleteFavMovieUseCase(favMovie: FavMovies) = repo.deleteFavMovie(favMovie)


}