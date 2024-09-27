package com.deepzub.movieapp.data.room.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepzub.movieapp.data.room.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase(){
    abstract val movieDao: MovieDao
}