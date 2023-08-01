package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepositoryImp
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val repo: MovieRepositoryImp
){
  suspend fun getPopularMoviesUseCase(page:String) = repo.getPopularMovies(page)

}