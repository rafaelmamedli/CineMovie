package com.rafael.movieapp.domein.use_case.local

import com.rafael.bodyfattracker.data.repo.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.models.local.FavMovies
import javax.inject.Inject

class DeleteFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepositoryImpl) {

    suspend fun deleteFavMovieUseCase(favMovie: FavMovies) = repo.deleteFavMovie(favMovie)


}