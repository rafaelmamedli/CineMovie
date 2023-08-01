package com.example.projectmoviecatch.data.retrofit

import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.util.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("3/movie/popular?")
    suspend fun getPopularMovies(
        @Query("page") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("3/movie/now_playing?")
    suspend fun getRecentMovies(
        @Query("page") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>


    @GET("3/movie/top_rated?")
    suspend fun getTopRatedMovies(
        @Query("page") page: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("search/movie?api_key=802b2c4b88ea1183858e6b285827696e&language=en-US")
    fun getSearch(@Query("query") query: String): Call<Movie>


}