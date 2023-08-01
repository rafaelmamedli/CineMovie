package com.rafael.movieapp.data.models.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Result(
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val vote_average: Double? = null,
    var genrestring: String? = null,
) : Parcelable

