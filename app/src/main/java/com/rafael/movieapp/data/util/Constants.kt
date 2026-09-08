package com.rafael.movieapp.data.util

import com.rafael.movieapp.BuildConfig

const val BASE_URL = "https://api.themoviedb.org/3/"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342/"

/**
 * TMDB api key. Injected at build time from local.properties / the TMDB_API_KEY
 * environment variable, so that no secret is stored in the repository.
 */
val API_KEY: String = BuildConfig.TMDB_API_KEY

val hasApiKey: Boolean get() = API_KEY.isNotBlank()

const val TOP_RATED_MOVIE="top_rated_movie"
const val TOP_RATED="top_rated"
const val POPULAR_MOVIE="popular_movie"
const val POPULAR="popular"
const val RECENT_MOVIE="recent_movie"
const val RECENT="recent"
const val FAVOURITE_MOVIE="favourite_movie"
const val FAVOURITE="favourite"
const val SEARCHED_MOVIE="searched_movie"
const val SEARCHED="searched"
const val ALL_MOVIE = "all_movie"
const val ALL = "all"
const val MOVIE_ID = "movie_id"










