package com.deepzub.movieapp.data.remote

import com.deepzub.movieapp.data.remote.dto.MovieDetailDto
import com.deepzub.movieapp.data.remote.dto.MoviesDto
import com.deepzub.movieapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

//    http://www.omdbapi.com/?apikey=caa9bd56&s=batman
//    http://www.omdbapi.com/?apikey=caa9bd56&i=tt1877830

    @GET(".")
    suspend fun getMovies(
        @Query("s") searchString : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : MoviesDto

    @GET(".")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey : String = API_KEY
    ) : MovieDetailDto
}