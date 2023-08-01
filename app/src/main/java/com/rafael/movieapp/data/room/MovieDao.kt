package com.rafael.bodyfattracker.data.room

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafael.movieapp.data.models.local.FavMovies


@Dao
interface MovieDao {

    @Insert
    suspend fun insert(favMovie: FavMovies)

    @Delete
    suspend fun delete(favMovie: FavMovies)


    @Update
    suspend fun update(favMovie: FavMovies)

    @Query("SELECT * FROM fav_movies")
     fun getAllFavMovies(): List<FavMovies>
}

