package com.deepzub.movieapp.domain.repository

import com.deepzub.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDatabaseRepository {
    suspend fun insertMovie(movieEntity: MovieEntity)
    suspend fun deleteMovie(movieEntity: MovieEntity)
    suspend fun updateMovie(movieEntity: MovieEntity)
    suspend fun getFavoriteMovies(): Flow<List<MovieEntity>>
}