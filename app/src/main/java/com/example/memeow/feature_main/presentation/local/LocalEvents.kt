package com.example.memeow.feature_main.presentation.local

import com.example.memeow.feature_main.domain.model.Meme

sealed class LocalEvents{
    data class Search(val keyword: String): LocalEvents()
    data class DeleteMeme(val meme: Meme): LocalEvents()
    object RestoreMeme: LocalEvents()
}
