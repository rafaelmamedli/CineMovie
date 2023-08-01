package com.rafael.bodyfattracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.local.ListIntConverter


@Database(entities = [FavMovies::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bodyFatDao(): MovieDao

}



