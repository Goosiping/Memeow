package com.example.memeow.feature_main.data.repository

import android.provider.ContactsContract
import com.example.memeow.R
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMemeRepository : MemeRepository {
    private val memes = mutableListOf<Meme>()
    /*need to be connected to database later*/

    override fun getMemes(): Flow<List<Meme>> {
        return flow { emit(memes) }
    }

    override suspend fun insertMeme(meme: Meme){
        memes.add(meme)
    }

    override suspend fun removeMeme(meme: Meme){
        memes.remove(meme)
    }

}


