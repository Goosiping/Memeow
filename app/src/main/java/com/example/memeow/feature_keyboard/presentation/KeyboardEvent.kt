package com.example.memeow.feature_keyboard.presentation

import com.example.memeow.feature_main.domain.model.Meme

sealed class KeyboardEvent {
    data class ClickMeme(val meme: Meme): KeyboardEvent()
    data class TypeText(val text: String): KeyboardEvent()
    object DeleteText: KeyboardEvent()
    object TouchSearchBar: KeyboardEvent()
    object ReturnText : KeyboardEvent()
    object CapsLock : KeyboardEvent()
}