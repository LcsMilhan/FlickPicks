package com.lcsmilhan.flickpicks.di

import android.app.Application
import androidx.room.Room
import com.lcsmilhan.flickpicks.common.Constants.BASE_URL
import com.lcsmilhan.flickpicks.common.Constants.DATABASE_NAME
import com.lcsmilhan.flickpicks.data.local.MovieDatabase
import com.lcsmilhan.flickpicks.data.remote.ApiService
import com.lcsmilhan.flickpicks.data.repository.local.MovieDbRepositoryImpl
import com.lcsmilhan.flickpicks.data.repository.remote.MovieRepositoryImpl
import com.lcsmilhan.flickpicks.domain.repository.local.MovieDbRepository
import com.lcsmilhan.flickpicks.domain.repository.remote.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesMovieDbRepository(db: MovieDatabase): MovieDbRepository {
        return MovieDbRepositoryImpl(db.moviesDao)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository {
        return movieRepositoryImpl
    }

}