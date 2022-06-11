package com.example.memeow.feature_main.domain.use_case

import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow

class ExploreMemes (
    private val repository: MemeRepository
) {
    operator fun invoke(): Flow<List<Meme>> {
        /* Flow.map can sort the content*/
        return repository.exploreMemes()
    }
}
