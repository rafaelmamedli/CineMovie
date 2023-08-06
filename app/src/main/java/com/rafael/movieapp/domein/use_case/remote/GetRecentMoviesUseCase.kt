package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

class GetRecentMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
) {
    suspend fun getRecent(page:String) = repo.getRecentMovies(page)
}