package com.deepzub.movieapp.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepzub.movieapp.domain.model.ThemeState
import com.deepzub.movieapp.domain.use_case.get_movies.GetMovieUseCase
import com.deepzub.movieapp.util.DataStoreUtils
import com.deepzub.movieapp.util.DataStoreUtils.Companion.IS_DARK_MODE_KEY
import com.deepzub.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    dataStoreUtil: DataStoreUtils
): ViewModel() {

    private val _state = mutableStateOf<MoviesState>(MoviesState())
    val state: State<MoviesState> = _state

    private val _themeState = MutableStateFlow(ThemeState(false))
    val themeState: StateFlow<ThemeState> = _themeState

    private val dataStore = dataStoreUtil.dataStore

    private var job: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.data.map { preferences ->
                ThemeState(preferences[IS_DARK_MODE_KEY] ?: false)
            }.collect {
                _themeState.value = it
            }
        }

        getMovies(_state.value.search)

    }


    private fun getMovies(search: String) {
        job?.cancel()

        getMovieUseCase.executeGetMovies(search = search).onEach {
            when (it) {

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
        when (event) {
            is MoviesEvent.Search -> {
                getMovies(event.searchString)
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[IS_DARK_MODE_KEY] = !(preferences[IS_DARK_MODE_KEY] ?: false)
            }
        }

    }
}
