package com.rafael.movieapp.data.models.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val page:Int? = null,
                 val results: List<Result>? = null,
                 val total_pages:Int? = null,
                 val total_results: Int? = null):Parcelable
