package com.deepzub.movieapp.data.dependencyinjection

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.deepzub.movieapp.data.remote.MovieAPI
import com.deepzub.movieapp.data.repository.MovieDatabaseRepositoryImpl
import com.deepzub.movieapp.data.repository.MovieApiRepositoryImpl
import com.deepzub.movieapp.data.room.datasource.MovieDatabase
import com.deepzub.movieapp.domain.repository.MovieApiRepository
import com.deepzub.movieapp.domain.repository.MovieDatabaseRepository
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
    fun provideMovieApiRepository(api: MovieAPI) : MovieApiRepository {
        return MovieApiRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideDataBase(app: Application) : MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "DataBase"
        )
//            .addMigrations() later add migrations if u change the table
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDatabaseRepository(movieDB: MovieDatabase) : MovieDatabaseRepository {
        return MovieDatabaseRepositoryImpl(movieDB.movieDao)
    }

    @Provides
    fun provideDataStoreUtil(@ApplicationContext context: Context):DataStoreUtils = DataStoreUtils(context)

}
