package com.rafael.movieapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafael.bodyfattracker.data.room.MovieDao
import com.rafael.movieapp.data.models.local.FavMovies


@Database(entities = [FavMovies::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao

}



