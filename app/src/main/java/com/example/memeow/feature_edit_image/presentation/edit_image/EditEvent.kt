package com.example.memeow.feature_edit_image.presentation.edit_image

sealed class EditEvent {
    object StopTyping :EditEvent()
    object SaveImg: EditEvent()
    object StartTyping: EditEvent()
    object StartAddImage: EditEvent()

}