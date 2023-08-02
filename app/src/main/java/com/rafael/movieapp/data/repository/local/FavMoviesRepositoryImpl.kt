package com.rafael.movieapp.data.repository.local

import com.rafael.bodyfattracker.data.room.MovieDao
import com.rafael.movieapp.data.models.local.FavMovies
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavMoviesRepositoryImpl @Inject constructor(private val movieDao: MovieDao):
    FavMoviesRepository {


    override suspend fun getAllFavMovies(): Flow<MutableList<FavMovies>> {
        return movieDao.getAllFavMovies()
    }

    override suspend fun insert(favMovie: FavMovies) {
            movieDao.insert(favMovie)
    }

    override suspend fun updateFavMovie(favMovie: FavMovies) {
        movieDao.update(favMovie)
    }

    override suspend fun deleteFavMovie(favMovie: FavMovies) {
        movieDao.delete(favMovie)

    }


}


