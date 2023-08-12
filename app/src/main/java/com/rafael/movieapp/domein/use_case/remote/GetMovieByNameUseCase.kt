package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

class GetMovieByNameUseCase @Inject constructor(
    private val repo: MovieRepository
) {

    suspend operator fun invoke(movieName:String) = repo.getMoviesByName(movieName)

}