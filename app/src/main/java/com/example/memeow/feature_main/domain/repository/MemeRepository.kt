package com.example.memeow.feature_main.domain.repository

import android.net.Uri
import com.example.memeow.feature_main.domain.model.Meme
import kotlinx.coroutines.flow.Flow

interface MemeRepository {
    /*need to be connected to database later*/
    fun getMemes(): Flow<List<Meme>>

    fun exploreMemes(): Flow<List<Meme>>

    suspend fun insertMeme(meme: Meme)

    suspend fun removeMeme(meme: Meme)

    fun getTagsByUri(uri: Uri): Flow<List<String>>

    suspend fun insertTagsByUri(tags: List<String>, uri: Uri)

    suspend fun removeTagsByUri(tags: List<String>, uri: Uri)

    fun getAllTags(): Flow<Set<String>>
}
