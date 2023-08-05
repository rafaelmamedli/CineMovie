package com.rafael.movieapp.data.repository.local


import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Movie
import kotlinx.coroutines.flow.Flow

interface FavMoviesRepository {
    suspend fun getAllFavMovies(): Flow<MutableList<FavMovies>>
    suspend fun insert(favMovie: FavMovies)
    suspend fun updateFavMovie(favMovie: FavMovies)
    suspend fun deleteFavMovie(favMovie: FavMovies)
    suspend fun getFavMovieByTitle(title: String): Flow<FavMovies?>
}

