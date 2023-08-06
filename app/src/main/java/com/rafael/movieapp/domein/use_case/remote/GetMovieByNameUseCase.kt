package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(
    private val repo: MovieRepository
) {

    suspend fun getMovie(movieName:String) = repo.getMoviesByName(movieName)

}