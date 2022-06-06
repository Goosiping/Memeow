package com.example.memeow.feature_main.domain.use_case

import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository

class AddMeme (
    private val repository: MemeRepository
) {
    suspend operator fun invoke(meme: Meme){
        repository.insertMeme(meme)
    }
}