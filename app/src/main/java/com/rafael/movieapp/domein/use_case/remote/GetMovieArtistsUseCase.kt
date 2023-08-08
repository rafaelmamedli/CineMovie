package com.rafael.movieapp.domein.use_case.remote

import com.rafael.movieapp.data.repository.remote.MovieRepository
import javax.inject.Inject

    class GetMovieArtistsUseCase @Inject constructor(private val repo: MovieRepository) {

        suspend fun getArtists(movieId: Int) = repo.getArtists(movieId)
    }



