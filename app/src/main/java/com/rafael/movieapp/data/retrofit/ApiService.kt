package com.rafael.movieapp.data.retrofit


import com.rafael.movieapp.data.models.remote.movie.Movie
import com.rafael.movieapp.data.models.remote.movie.Trailer
import com.rafael.movieapp.data.models.remote.detail.Details
import com.rafael.movieapp.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("page") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("movie/now_playing?")
    suspend fun getRecentMovies(
        @Query("page") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>


    @GET("movie/top_rated?")
    suspend fun getTopRatedMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("search/movie?")
    suspend fun getSearch(
        @Query("query") query: String ,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>


    @GET("movie/{movieId}/credits?")
    suspend fun getArtists(
        @Path("movieId") query: Int,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<Details>


    @GET("movie/{movieId}/videos")
    suspend fun getTrailer(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Trailer>



}