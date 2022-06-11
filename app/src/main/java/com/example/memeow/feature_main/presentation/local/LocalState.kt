package com.example.memeow.feature_main.presentation.local

import com.example.memeow.feature_main.domain.model.Meme

data class LocalState(
    val memes: List<Meme> = emptyList(),
    val keyword: String = "",
    val searchbaractivate: Boolean = false
)