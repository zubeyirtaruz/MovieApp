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
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM FavoriteMovies")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

}