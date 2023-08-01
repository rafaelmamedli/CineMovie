package com.rafael.movieapp.domein.use_case.local

import com.rafael.bodyfattracker.data.repo.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.models.local.FavMovies
import javax.inject.Inject

class InsertFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepositoryImpl) {

    suspend fun insertFavMovieUseCase(favMovie: FavMovies): List<FavMovies> {
        // Insert the FavMovies
        repo.insert(favMovie)

        // Return all the FavMovies after insertion
        return repo.getAllFavMovies()
    }
}
