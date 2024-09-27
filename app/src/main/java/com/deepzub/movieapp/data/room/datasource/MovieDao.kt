package com.deepzub.movieapp.data.room.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.deepzub.movieapp.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Update
    suspend fun update(movieEntity: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovies(): Flow<List<MovieEntity>>

}