package com.example.memeow.feature_main.domain.use_case

import android.net.Uri
import com.example.memeow.feature_main.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow

class GetTagsByUri (
    private val repository: MemeRepository
) {
    operator fun invoke(uri: Uri): Flow<List<String>> {
        return repository.getTagsByUri(uri)
    }
}