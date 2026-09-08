package com.rafael.movieapp.data.repository.remote


import com.rafael.movieapp.data.models.remote.movie.Movie
import com.rafael.movieapp.data.models.remote.movie.Trailer
import com.rafael.movieapp.data.models.remote.detail.Details
import com.rafael.movieapp.data.retrofit.ApiService
import com.rafael.movieapp.data.util.Resource
import com.rafael.movieapp.data.util.hasApiKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val apiService: ApiService) : MovieRepository {

    override suspend fun getPopularMovies(page: String): Flow<Resource<Movie>> =
        apiCall { apiService.getPopularMovies(page) }

    override suspend fun getRecentMovies(page: String): Flow<Resource<Movie>> =
        apiCall { apiService.getRecentMovies(page) }

    override suspend fun getTopRatedMovies(page: String): Flow<Resource<Movie>> =
        apiCall { apiService.getTopRatedMovies(page) }

    override suspend fun getMoviesByName(movieName: String): Flow<Resource<Movie>> =
        apiCall { apiService.getSearch(movieName) }

    override suspend fun getMovieTrailer(movieId: Int): Flow<Resource<Trailer>> =
        apiCall { apiService.getTrailer(movieId) }

    override suspend fun getArtists(movieId: Int): Flow<Resource<Details>> =
        apiCall { apiService.getArtists(movieId) }

    /**
     * Wraps a single TMDB call: emits loading, then success or a user friendly error.
     * No exception is allowed to escape, so a dropped connection can never crash the app.
     */
    private fun <T> apiCall(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
        emit(Resource.loading(null))

        if (!hasApiKey) {
            emit(Resource.error(ERROR_NO_API_KEY, null))
            return@flow
        }

        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.success(body))
            } else {
                emit(Resource.error(httpErrorMessage(response.code()), null))
            }
        } catch (e: IOException) {
            emit(Resource.error(ERROR_NETWORK, null))
        } catch (e: Exception) {
            emit(Resource.error(e.localizedMessage ?: ERROR_UNKNOWN, null))
        }
    }

    private fun httpErrorMessage(code: Int): String = when (code) {
        401 -> ERROR_NO_API_KEY
        404 -> "Content not found"
        in 500..599 -> "TMDB service is temporarily unavailable"
        else -> "Request failed (HTTP $code)"
    }

    private companion object {
        const val ERROR_NETWORK = "Check network connection"
        const val ERROR_NO_API_KEY = "TMDB API key is missing or invalid"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}
