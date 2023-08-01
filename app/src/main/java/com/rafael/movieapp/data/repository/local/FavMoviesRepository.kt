package com.rafael.bodyfattracker.data.repo


import com.rafael.movieapp.data.models.local.FavMovies

interface FavMoviesRepository  {
    suspend fun getAllFavMovies(): List<FavMovies>
    suspend fun insert(bodyFat: FavMovies)

    suspend fun updateFavMovie(bodyFat: FavMovies)

    suspend fun deleteFavMovie(bodyFat: FavMovies)
}
