package com.example.memeow.feature_main.domain.use_case

import android.net.Uri
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow

class GetAllTags(
    private val repository: MemeRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.getAllTags()
    }
}