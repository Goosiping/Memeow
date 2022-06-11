package com.example.memeow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,

    //primaryVariant = Purple700,
    secondary = Teal200,

    //primaryVariant = Purple700,
    primaryVariant = Color(0xFFC8A415), // Golden
    surface = Color(0xFFFFFEFB),
    background = Color(0xFFE9E9E9), // Search bar background(background gray)

    onPrimary = Color(0xFFFFF59D), //Yellow 0
    onSecondary = Color.Black,
    secondaryVariant = Color(0xFFEBEAE8) // Light Gray
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MemeowTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
