package com.example.memeow.feature_main.domain.repository

import com.example.memeow.feature_main.domain.model.Meme
import kotlinx.coroutines.flow.Flow

interface MemeRepository {
    /*need to be connected to database later*/
    fun getMemes(): Flow<List<Meme>>

    fun exploreMemes(): Flow<List<Meme>>

    suspend fun insertMeme(meme: Meme)

    suspend fun removeMeme(meme: Meme)
}
