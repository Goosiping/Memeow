package com.example.memeow.feature_keyboard.presentation

import com.example.memeow.feature_keyboard.MemeInputIME
import com.example.memeow.feature_main.domain.model.Meme

abstract class  KeyboardViewModelEvent {
    var handled: Boolean = false
        private set

    open fun handle(ime : MemeInputIME) {
        handled = true
    }
}

class KeyboardSendMemeEvent(val meme: Meme): KeyboardViewModelEvent(){

}

class KeyboardSendTextEvent(val text:String): KeyboardViewModelEvent(){

}