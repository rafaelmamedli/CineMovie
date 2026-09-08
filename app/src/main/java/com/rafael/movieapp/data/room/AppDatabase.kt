package com.rafael.movieapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafael.movieapp.data.models.local.FavMovies


@Database(entities = [FavMovies::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao

}



