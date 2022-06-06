package com.example.memeow.feature_main.presentation.explore

import com.example.memeow.feature_main.domain.model.Meme

/*
 seal class just like the enum class, which can help us to distinguish different events,
 and deal with them respectively.
 data class is just the object with parameter passed in.
 for more info, please see:
 https://louis383.medium.com/kotlin-sealed-classes-%E7%9A%84%E5%9F%BA%E7%A4%8E%E4%BD%BF%E7%94%A8-de660dbb63d2
*/
sealed class ExploreEvents{
    data class Search(val keyword: String): ExploreEvents()
    data class DeleteMeme(val meme: Meme): ExploreEvents()
    object RestoreMeme: ExploreEvents()
}

