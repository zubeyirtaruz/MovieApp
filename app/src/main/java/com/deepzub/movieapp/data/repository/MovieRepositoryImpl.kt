package com.deepzub.movieapp.data.repository

import com.deepzub.movieapp.data.remote.MovieAPI
import com.deepzub.movieapp.data.remote.dto.MovieDetailDto
import com.deepzub.movieapp.data.remote.dto.MoviesDto
import com.deepzub.movieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val api : MovieAPI) : MovieRepository{
    override suspend fun getMovies(search: String): MoviesDto {
        return api.getMovies(searchString = search)
    }

    override suspend fun getMovieDetail(imdbId: String): MovieDetailDto {
        return api.getMovieDetail(imdbId = imdbId)
    }

}