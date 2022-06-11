package com.example.memeow.feature_main.data.data_source.local

import android.net.Uri
import androidx.room.*
import com.example.memeow.feature_main.data.data_source.local.entity.MemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {
    @Query("Select * FROM meme")
    suspend fun getMeme(): List<MemeEntity>

    @Query("DELETE FROM meme WHERE image IN(:uris)")
    suspend fun deleteMemeByUris(uris: List<String>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemes(memes: List<MemeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeme(meme: MemeEntity)

    @Delete
    suspend fun deleteMeme(meme: MemeEntity)
}