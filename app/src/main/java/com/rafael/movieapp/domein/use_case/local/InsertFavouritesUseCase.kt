package com.rafael.movieapp.domein.use_case.local

import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.repository.local.FavMoviesRepository
import javax.inject.Inject

class InsertFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepository) {

    suspend operator fun invoke(favMovie: FavMovies) = repo.insert(favMovie)



}
