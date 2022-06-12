package com.example.memeow.feature_edit_image.presentation.view_tempate

import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.presentation.explore.ExploreEvents


sealed class ViewTemplateEvent{
    data class Search(val keyword: String): ViewTemplateEvent()
    data class UseTemplate(val template: Meme): ViewTemplateEvent()
    data class PreviewTemplate(val template: Meme): ViewTemplateEvent()
}

