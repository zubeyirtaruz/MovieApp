package com.deepzub.movieapp.data.repository

import com.deepzub.movieapp.data.room.entity.MovieEntity
import com.deepzub.movieapp.data.remote.MovieAPI
import com.deepzub.movieapp.data.remote.dto.MovieDetailDto
import com.deepzub.movieapp.data.remote.dto.MoviesDto
import com.deepzub.movieapp.data.room.datasource.MovieDao
import com.deepzub.movieapp.domain.repository.MovieApiRepository
import com.deepzub.movieapp.domain.repository.MovieDatabaseRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieApiRepositoryImpl @Inject constructor(private val api : MovieAPI) : MovieApiRepository {

    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(searchString = search)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId = imdbId)
    }

}

class MovieDatabaseRepositoryImpl @Inject constructor(private val dao : MovieDao) : MovieDatabaseRepository {

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        withContext(IO) {
            dao.insertMovie(movieEntity)
        }
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
        withContext(IO) {
            dao.deleteMovie(movieEntity)
        }
    }

    override suspend fun updateMovie(movieEntity: MovieEntity) {
        withContext(IO) {
            dao.updateMovie(movieEntity)
        }
    }

    override suspend fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return withContext(IO) {
            dao.getFavoriteMovies()
        }
    }

}