package com.rafael.movieapp.domein.use_case.local

import com.rafael.movieapp.data.repository.local.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.repository.local.FavMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavouritesUseCase @Inject constructor(private val repo: FavMoviesRepository) {

    suspend fun insertFavMovieUseCase(favMovie: FavMovies): Flow<List<FavMovies>> {
        // Insert the FavMovies
        repo.insert(favMovie)

        // Return all the FavMovies after insertion
        return repo.getAllFavMovies()
    }
}
