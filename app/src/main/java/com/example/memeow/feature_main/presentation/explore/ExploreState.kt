package com.example.memeow.feature_main.presentation.explore

import com.example.memeow.feature_main.domain.model.Meme

/*
 with ExploreState, we can easily manage our states,
 or the variables required to be subscribe for recomposition,
 in the Viewmodel.
 */
data class ExploreState (
    val memes: List<Meme> = emptyList(),
    val keyword: String = ""
)