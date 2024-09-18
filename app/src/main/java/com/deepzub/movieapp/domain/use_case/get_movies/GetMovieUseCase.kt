package com.deepzub.movieapp.domain.use_case.get_movies

import coil.network.HttpException
import com.deepzub.movieapp.data.remote.dto.toMovieList
import com.deepzub.movieapp.domain.model.Movie
import com.deepzub.movieapp.domain.repository.MovieRepository
import com.deepzub.movieapp.util.Resource
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(private val repository: MovieRepository){

    fun executeGetMovies(search : String) : kotlinx.coroutines.flow.Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movieList = repository.getMovies(search = search)
            if(movieList.Response == "True"){
                emit(Resource.Success(movieList.toMovieList()))
            } else {
                emit(Resource.Error(message = "No movie found!"))
            }
        } catch (e: IOError) {
            emit(Resource.Error(message = "No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Error"))
        }
    }

}