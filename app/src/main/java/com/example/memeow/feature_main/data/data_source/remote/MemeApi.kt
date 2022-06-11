package com.example.memeow.feature_main.data.data_source.remote

import com.example.memeow.feature_main.data.data_source.remote.dto.MemeDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET

interface MemeApi {
    @GET(".")
    suspend fun gettrendingMeme(): List<MemeDto>
    companion object {
        const val BASE_URL = "https://memes.tw/wtf/api/"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}