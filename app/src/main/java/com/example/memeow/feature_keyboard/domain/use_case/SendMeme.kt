package com.example.memeow.feature_keyboard.domain.use_case

import android.util.Log
import com.example.memeow.R
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow
import java.net.URI

class SendMeme(
    //private val repository: MemeRepository
) {
    private val TAG ="SendMeme"
    operator fun invoke(meme: Meme): Int {
        Log.i(TAG,"send a meme")
        return 1
        //return Meme("cat_1", R.drawable.cat_1, listOf("cat","1"))
    }
}