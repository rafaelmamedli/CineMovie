package com.rafael.movieapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.projectmoviecatch.data.retrofit.ApiService
import com.rafael.movieapp.data.repository.local.FavMoviesRepository
import com.rafael.movieapp.data.repository.local.FavMoviesRepositoryImpl
import com.rafael.movieapp.data.room.AppDatabase
import com.rafael.bodyfattracker.data.room.MovieDao
import com.rafael.movieapp.data.repository.remote.MovieRepository
import com.rafael.movieapp.data.repository.remote.MovieRepositoryImp
import com.rafael.movieapp.data.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit) :ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }




    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "fav_movie_database"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.MovieDao()
    }

    @Provides
    @Singleton
    fun provideFavMoviesRepository(movieDao: MovieDao): FavMoviesRepository {
        return FavMoviesRepositoryImpl(movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService):MovieRepository {
        return MovieRepositoryImp(apiService)
    }






}