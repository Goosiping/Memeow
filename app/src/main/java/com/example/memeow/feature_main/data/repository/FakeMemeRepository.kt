package com.example.memeow.feature_main.data.repository

import android.provider.ContactsContract
import com.example.memeow.R
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMemeRepository : MemeRepository {
    private val memes = mutableListOf<Meme>(
        Meme("cat_1", R.drawable.cat_1, listOf("cat","1")),
        Meme("cat_2", R.drawable.cat_2, listOf("cat","2")),
        Meme("cat_3", R.drawable.cat_3, listOf("cat","3"))
    )
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


