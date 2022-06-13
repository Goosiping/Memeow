package com.example.memeow.feature_main.domain.use_case

import android.net.Uri
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository

class DeleteTagsByUri (
    private val repository: MemeRepository
) {
    suspend operator fun invoke(tags: List<String>, uri: Uri){
        repository.removeTagsByUri(tags,uri)
    }
}