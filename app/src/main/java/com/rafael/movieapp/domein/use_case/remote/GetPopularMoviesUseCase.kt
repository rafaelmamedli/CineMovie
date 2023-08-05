package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val repo: MovieRepository
){
  suspend fun getPopularMoviesUseCase(page:String) = repo.getPopularMovies(page)

}