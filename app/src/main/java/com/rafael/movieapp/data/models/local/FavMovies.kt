package com.rafael.movieapp.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters



@Entity(tableName = "fav_movies")
@TypeConverters(ListIntConverter::class)
class FavMovies (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val vote_average: Double? = null,
    var genrestring: String? = null
)
