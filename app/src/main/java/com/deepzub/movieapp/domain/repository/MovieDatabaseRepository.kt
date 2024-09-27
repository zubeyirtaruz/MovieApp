package com.deepzub.movieapp.domain.repository

import com.deepzub.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDatabaseRepository {
    suspend fun insert(studentEntity: MovieEntity)
    suspend fun delete(studentEntity: MovieEntity)
    suspend fun update(studentEntity: MovieEntity)
    suspend fun getAllMovies(): Flow<List<MovieEntity>>
}