package com.example.memeow.feature_main.presentation.sigle_view

import android.net.Uri
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.lifecycle.ViewModel
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.use_case.MemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SingleViewState (
    val uri: Uri = Uri.parse(""),
    val tags: List<String> = emptyList(),
    val keytag: String = "",
    val tagaddactivate: Boolean = false
)

