package com.rafael.movieapp.data.models.remote.movie

import com.rafael.movieapp.data.models.remote.movie.ResultX

data class Trailer(
    val id: Int ? = null,
    val results: List<ResultX>? = null
)
