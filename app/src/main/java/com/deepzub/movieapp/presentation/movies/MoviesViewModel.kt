package com.deepzub.movieapp.presentation.movies

import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.movieapp.domain.use_case.get_movies.GetMovieUseCase
import com.deepzub.movieapp.presentation.no_internet.ConnectivityObserver
import com.deepzub.movieapp.presentation.no_internet.NetworkConnectivityObserver
import com.deepzub.movieapp.presentation.theme.MovieAppTheme
import com.deepzub.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
): ViewModel() {

    private val _state = mutableStateOf<MoviesState>(MoviesState())
    val state : State<MoviesState> = _state

    private var job : Job? = null

    init {
        getMovies(_state.value.search)
    }


    private fun getMovies(search : String){
        job?.cancel()

        getMovieUseCase.executeGetMovies(search = search).onEach {
            when(it) {

                is Resource.Success -> {
                    _state.value = MoviesState(movies = it.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value = MoviesState(error = it.message ?: "Error!")
                }

                is Resource.Loading -> {
                    _state.value = MoviesState(isLoading = true)
                }

            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MoviesEvent) {
        when(event) {
            is MoviesEvent.Search -> {
                getMovies(event.searchString)
            }
        }
    }

}
