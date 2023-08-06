package com.rafael.movieapp.data.room

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafael.movieapp.data.models.local.FavMovies
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert
    suspend fun insert(favMovie: FavMovies)

    @Delete
    suspend fun delete(favMovie: FavMovies)


    @Update
    suspend fun update(favMovie: FavMovies)

    @Query("SELECT * FROM fav_movies")
      fun getAllFavMovies(): Flow<MutableList<FavMovies>>

    @Query("SELECT * FROM fav_movies WHERE title = :title")
     fun getFavMovieByTitle(title: String): Flow<FavMovies?>


}

