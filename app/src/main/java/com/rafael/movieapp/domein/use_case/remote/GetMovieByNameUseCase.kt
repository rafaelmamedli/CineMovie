package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepositoryImp
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(
    private val repo: MovieRepositoryImp
) {

    suspend fun getMovieByNameUseCase(movieName:String) = repo.getMoviesByName(movieName)

}