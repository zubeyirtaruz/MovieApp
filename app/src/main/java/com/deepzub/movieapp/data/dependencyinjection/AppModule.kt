package com.deepzub.movieapp.data.dependencyinjection

import android.content.Context
import com.deepzub.movieapp.data.remote.MovieAPI
import com.deepzub.movieapp.data.repository.MovieRepositoryImpl
import com.deepzub.movieapp.domain.repository.MovieRepository
import com.deepzub.movieapp.util.Constants.BASE_URL
import com.deepzub.movieapp.util.DataStoreUtils
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
    fun provideMoveApi() : MovieAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI) : MovieRepository {
        return MovieRepositoryImpl(api = api)
    }

    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context):DataStoreUtils = DataStoreUtils(context)

}