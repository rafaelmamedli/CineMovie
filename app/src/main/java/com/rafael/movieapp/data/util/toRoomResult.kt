package com.rafael.movieapp.data.util

import com.rafael.movieapp.data.models.local.FavMovies
import com.rafael.movieapp.data.models.remote.Result

fun Result.toRoomResult(): FavMovies {
    return FavMovies(
        backdrop_path = backdrop_path,
        genre_ids = genre_ids,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        genrestring = genrestring
    )
}
