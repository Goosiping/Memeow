package com.example.memeow.feature_keyboard.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.example.memeow.feature_main.domain.model.Meme

data class KeyboardState (
    val memes: List<Meme> = emptyList(),
    val keyword:String = "", // for in-keyboard search bar's keyword
    val showTextKeyboard: Boolean = false,
    val searchTextFieldValue: TextFieldValue = TextFieldValue(),
    val isCapsLock:Boolean = false //Display upper case or lower case keyboard

)