package com.example.memeow.feature_main.domain.use_case

data class MemeUseCases(
    val getMemes: GetMemes,
    val addMeme: AddMeme,
    val deleteMeme: DeleteMeme
)
