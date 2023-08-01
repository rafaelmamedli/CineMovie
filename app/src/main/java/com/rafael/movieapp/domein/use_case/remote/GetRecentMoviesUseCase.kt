package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepositoryImp
import javax.inject.Inject

class GetRecentMoviesUseCase @Inject constructor(
    private val repo: MovieRepositoryImp
) {
    suspend fun getRecentMoviesUseCase(page:String) = repo.getRecentMovies(page)
}