package com.rafael.movieapp.data.retrofit


import com.rafael.movieapp.data.models.remote.Movie
import com.rafael.movieapp.data.models.remote.Trailer
import com.rafael.movieapp.data.models.remote.detail.Details
import com.rafael.movieapp.data.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("3/search/movie?")
    suspend fun getSearch(
        @Query("query") query: String ,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>


    @GET("/3/movie/{movieId}/credits?")
    suspend fun getArtists(
        @Path("movieId") query: Int,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<Details>


    @GET("3/movie/{movieId}/videos")
    suspend fun getTrailer(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Trailer>



}