package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val repo: MovieRepository
) {

    suspend fun getTop(page: String) = repo.getTopRatedMovies(page)
}