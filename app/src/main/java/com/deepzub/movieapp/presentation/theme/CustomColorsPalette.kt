package com.deepzub.movieapp.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette(
    val extraColor1: Color = Color.Unspecified,
    val extraColor2: Color = Color.Unspecified,
    val extraColor3: Color = Color.Unspecified
)

val LightCustomColorsPalette = CustomColorsPalette(
    extraColor1 = Color(color = 0xFF29B6F6),
    extraColor2 = Color(color = 0xFF26A69A),
    extraColor3 = Color(color = 0xFFEF5350)
)

val DarkCustomColorsPalette = CustomColorsPalette(
    extraColor1 = Color(color = 0xFF0277BD),
    extraColor2 = Color(color = 0xFF00695C),
    extraColor3 = Color(color = 0xFFC62828)
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val MaterialTheme.customColorsPalette : CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current