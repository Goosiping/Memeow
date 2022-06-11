package com.example.memeow.feature_main.data.data_source.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.memeow.feature_main.domain.model.Meme


@Entity(tableName = "meme") //"MemeEntity" is the default table name.
data class MemeEntity(
    @PrimaryKey val image: String,
    val tags: List<String>,
    val title: String,
    val id: Int? = null
) {
    fun toMeme(): Meme {
        return Meme(
            image = Uri.parse(image),
            tags = tags,
            title = title
        )
    }
}
