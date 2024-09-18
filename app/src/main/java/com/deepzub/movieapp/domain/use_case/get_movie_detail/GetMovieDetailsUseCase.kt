package com.deepzub.movieapp.domain.use_case.get_movie_detail

import coil.network.HttpException
import com.deepzub.movieapp.data.remote.dto.toMovieDetail
import com.deepzub.movieapp.data.remote.dto.toMovieList
import com.deepzub.movieapp.domain.model.Movie
import com.deepzub.movieapp.domain.model.MovieDetail
import com.deepzub.movieapp.domain.repository.MovieRepository
import com.deepzub.movieapp.util.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

    fun executeGetMovieDetails(imdbId : String) : kotlinx.coroutines.flow.Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetail(imdbId = imdbId)
            emit(Resource.Success(movieDetail.toMovieDetail()))
        } catch (e: IOError) {
            emit(Resource.Error(message = "No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }
    }

}