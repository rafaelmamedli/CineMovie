package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

class GetMovieTrailerUseCase @Inject constructor(private val repo: MovieRepository) {

    suspend operator fun invoke(movieId:Int) = repo.getMovieTrailer(movieId)


}