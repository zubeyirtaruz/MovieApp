package com.deepzub.movieapp.presentation.movies.views

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepzub.movieapp.presentation.Screen
import com.deepzub.movieapp.presentation.movies.MoviesEvent
import com.deepzub.movieapp.presentation.movies.MoviesViewModel
import com.deepzub.movieapp.util.ConnectionState
import com.deepzub.movieapp.presentation.ConnectivityStatus
import com.deepzub.movieapp.presentation.DarkModeSwitch
import com.deepzub.movieapp.util.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun MovieScreen(
    navController : NavController,
    viewModel : MoviesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        Column {
            MovieSearchBar(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 50.dp, end = 20.dp, bottom = 20.dp),
                hint = "Batman",
                onSearch = {
                    if(isConnected){
                        viewModel.onEvent(MoviesEvent.Search(it))
                    }

                })

            ConnectivityStatus()

            DarkModeSwitch(
                checked = viewModel.themeState.value.isDarkMode,
                Modifier.padding(8.dp),
                onCheckedChanged = {
                    viewModel.toggleTheme()
                }
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.movies) { movie ->
                    MovieListRow(movie = movie, onItemClick = {
                        if(isConnected){
                            navController.navigate(Screen.MovieDetailScreen.route+"/${movie.imdbID}")
                        }

                    })
                }

            }

        }

    }

}

@Composable
fun MovieSearchBar(
    modifier : Modifier,
    hint : String,
    onSearch : (String) -> Unit = {}
) {

    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        TextField(value = text,
            onValueChange = {
            text = it
        },
            keyboardActions = KeyboardActions(onDone = {
                onSearch(text)
            }),
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }
        )

        if (isHintDisplayed) {
            Text(text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }
    }
}