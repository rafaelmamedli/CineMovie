package com.rafael.bodyfattracker.data.repo


import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavMoviesRepository  {
    suspend fun getAllFavMovies(): Flow<List<FavMovies>>
    suspend fun insert(bodyFat: FavMovies)

    suspend fun updateFavMovie(bodyFat: FavMovies)

    suspend fun deleteFavMovie(bodyFat: FavMovies)
}
