package com.rafael.movieapp.data.repository.local


import com.rafael.movieapp.data.models.local.FavMovies
import kotlinx.coroutines.flow.Flow

interface FavMoviesRepository  {
    suspend fun getAllFavMovies(): Flow<List<FavMovies>>
    suspend fun insert(bodyFat: FavMovies)

    suspend fun updateFavMovie(bodyFat: FavMovies)

    suspend fun deleteFavMovie(bodyFat: FavMovies)
}
