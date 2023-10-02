package com.lcsmilhan.flickpicks.di

import android.content.Context
import androidx.room.Room
import com.lcsmilhan.flickpicks.common.Constants.BASE_URL
import com.lcsmilhan.flickpicks.common.Constants.DB_NAME
import com.lcsmilhan.flickpicks.data.local.MovieDatabase
import com.lcsmilhan.flickpicks.data.local.repository.MyFavoritesRepositoryImpl
import com.lcsmilhan.flickpicks.data.local.repository.MyWatchListRepositoryImpl
import com.lcsmilhan.flickpicks.data.remote.ApiService
import com.lcsmilhan.flickpicks.data.remote.repository.MovieRepositoryImpl
import com.lcsmilhan.flickpicks.domain.local.repository.MyFavoritesRepository
import com.lcsmilhan.flickpicks.domain.local.repository.MyWatchListRepository
import com.lcsmilhan.flickpicks.domain.remote.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            DB_NAME
        ).build()
    }

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

    @Provides
    @Singleton
    fun provideMovieRepository(apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesMyMoviesRepository(db: MovieDatabase): MyFavoritesRepository {
        return MyFavoritesRepositoryImpl(db.favoritesDao)
    }

    @Provides
    @Singleton
    fun providesMyWatchListRepository(db: MovieDatabase): MyWatchListRepository {
        return MyWatchListRepositoryImpl(db.watchListDao)
    }

}