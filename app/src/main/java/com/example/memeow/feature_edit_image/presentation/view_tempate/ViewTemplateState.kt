package com.example.memeow.feature_edit_image.presentation.view_tempate

import com.example.memeow.feature_main.domain.model.Meme

data class ViewTemplateState(
    val templates: List<Meme> = emptyList(),
    val keyword: String = "",
    val selectedTemplate: Meme? = null,
    val searchbaractivate: Boolean = false
)