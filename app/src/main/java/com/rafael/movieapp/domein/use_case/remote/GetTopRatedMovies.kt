package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepositoryImp
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val repo: MovieRepositoryImp
) {

    suspend fun getTopRatedMoviesUseCase(page: String) = repo.getTopRatedMovies(page)
}