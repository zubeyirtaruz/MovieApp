package com.deepzub.movieapp.domain.repository

import com.deepzub.movieapp.data.remote.dto.MovieDetailDto
import com.deepzub.movieapp.data.remote.dto.MoviesDto

interface MovieRepository {
    suspend fun getMovies(search : String) : MoviesDto
    suspend fun getMovieDetail(imdbId : String) : MovieDetailDto
}