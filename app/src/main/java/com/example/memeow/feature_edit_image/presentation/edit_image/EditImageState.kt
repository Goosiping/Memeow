package com.example.memeow.feature_edit_image.presentation.edit_image

import android.graphics.Color

data class EditImageState (
    val isTyping:Boolean = false,
    val selectColor: Int  = Color.parseColor("#000000"),
    val curText: String  = ""
)