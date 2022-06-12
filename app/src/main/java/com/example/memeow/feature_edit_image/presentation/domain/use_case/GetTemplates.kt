package com.example.memeow.feature_edit_image.presentation.domain.use_case

import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow

class GetTemplates(
    private val repository: MemeRepository
) {
    operator fun invoke(): Flow<List<Meme>> {
        /* Flow.map can sort the content*/
        return repository.getMemes()
    }
}