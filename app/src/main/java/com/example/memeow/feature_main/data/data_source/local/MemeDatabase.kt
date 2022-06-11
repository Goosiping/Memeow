package com.example.memeow.feature_main.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.memeow.feature_main.data.data_source.local.entity.Converters
import com.example.memeow.feature_main.data.data_source.local.entity.MemeEntity

@Database(
    entities = [MemeEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MemeDatabase : RoomDatabase() {
    abstract val dao: MemeDao

    companion object {
        const val DATABASE_NAME = "memes_db"
    }
}