package com.rafael.movieapp.data.repository.remote

import com.rafael.movieapp.data.models.remote.Movie
import com.example.projectmoviecatch.data.retrofit.ApiService
import com.rafael.movieapp.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val apiService: ApiService) : MovieRepository {


    override suspend fun getPopularMovies(page: String): Flow<Resource<Movie>> = flow {
        emit(Resource.loading(null))
        val response = apiService.getPopularMovies(page)
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        } else {
            emit(Resource.error("Error popular movies",null))
        }
    }

    override suspend fun getRecentMovies(page: String): Flow<Resource<Movie>> = flow {
        emit(Resource.loading(null))
        val response = apiService.getRecentMovies(page)
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        } else {
            emit(Resource.error("Error recent movies",null))
        }


    }

    override suspend fun getTopRatedMovies(page: String): Flow<Resource<Movie>> = flow{
        emit(Resource.loading(null))
        val response = apiService.getTopRatedMovies(page)
        if (response.isSuccessful) {
            emit(Resource.success(response.body()))
        } else {
            emit(Resource.error("Error top rated movies",null))
        }
    }


}