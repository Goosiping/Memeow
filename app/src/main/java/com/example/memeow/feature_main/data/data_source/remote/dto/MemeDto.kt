package com.example.memeow.feature_main.data.data_source.remote.dto

import android.net.Uri
import com.example.memeow.feature_main.domain.model.Meme

data class MemeDto (

    val id             : Int?       ,
    val url            : String     ,
    val src            : String?    ,
    val author         : AuthorDto? ,
    val title          : String     ,
    val pageview       : Int?       ,
    val totalLikeCount : Int?       ,
    val createdAt      : CreatedAtDto?,
    val hashtag        : String     ,
    val contest        : ContestDto?

){
    fun toMeme(): Meme {
        return Meme(
            image = Uri.parse(src),
            tags = hashtag.split(" "),
            title = title
        )
    }
}