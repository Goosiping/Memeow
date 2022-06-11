package com.example.memeow.feature_main.data.data_source.local.entity

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromjointags(jointags: String): List<String> {
        return jointags.split(" ")
    }

    @TypeConverter
    fun tojointags(tags: List<String>): String {
        return tags.joinToString(" ")
    }
}