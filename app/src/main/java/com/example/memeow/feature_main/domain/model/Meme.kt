package com.example.memeow.feature_main.domain.model

import android.net.Uri
import com.example.memeow.R

data class Meme(
    val title: String,
    val image: Uri,
    val tags: List<String>
)

