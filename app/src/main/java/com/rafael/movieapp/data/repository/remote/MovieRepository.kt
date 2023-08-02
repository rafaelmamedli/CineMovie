package com.rafael.movieapp.data.repository.remote

import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
   suspend fun getPopularMovies(page: String): Flow<Resource<Movie>>
   suspend fun getRecentMovies(page: String): Flow<Resource<Movie>>
   suspend fun getTopRatedMovies(page: String): Flow<Resource<Movie>>
   suspend fun getMoviesByName(movieName:String): Flow<Resource<Movie>>
}