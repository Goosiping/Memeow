package com.example.memeow.di

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.room.Room
import com.example.memeow.R
import com.example.memeow.feature_edit_image.presentation.domain.use_case.GetTemplates
import com.example.memeow.feature_edit_image.presentation.domain.use_case.ViewTemplateUseCase

import com.example.memeow.feature_main.data.data_source.local.MemeDatabase
import com.example.memeow.feature_main.data.data_source.local.entity.Converters
import com.example.memeow.feature_main.data.data_source.remote.MemeApi
import com.example.memeow.feature_main.data.data_source.remote.MemeApi.Companion.moshi
import com.example.memeow.feature_main.data.repository.FakeMemeRepository
import com.example.memeow.feature_main.domain.repository.MemeRepository
import com.example.memeow.feature_main.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*Database*/
    @Provides
    @Singleton
    fun provideMemeDatabase(app: Application): MemeDatabase{
        return Room.databaseBuilder(
            app,
            MemeDatabase::class.java,
            MemeDatabase.DATABASE_NAME
        ).addTypeConverter(Converters()).build()
    }

    /*API*/
    @Provides
    @Singleton
    fun provideDictionaryApi(): MemeApi {
        return Retrofit.Builder()
            .baseUrl(MemeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MemeApi::class.java)
    }

    /*Repository*/
    @Provides
    @Singleton
    fun provideNoteRepository(
        @ApplicationContext context: Context,
        db: MemeDatabase,
        api: MemeApi
    ): MemeRepository {
        Log.d("","inside provideNoteRepository")
        return FakeMemeRepository(context.contentResolver, api, db.dao)
    }

    /*UseCases*/
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: MemeRepository): MemeUseCases {
        Log.d("","inside provideNoteUseCases")
        return MemeUseCases(
            getMemes = GetMemes(repository),
            exploreMemes = ExploreMemes(repository),
            deleteMeme = DeleteMeme(repository),
            addMeme = AddMeme(repository),
            getTagsByUri = GetTagsByUri(repository),
            addTagsByUri = AddTagsByUri(repository),
            deleteTagsByUri = DeleteTagsByUri(repository),
            getAllTags = GetAllTags(repository)
        )
    }


    /*Edit ViewTemplate
    * TODO what is this template repo?
    * */
    @Provides
    @Singleton
    fun provideViewTemplateUseCase(repository: MemeRepository): ViewTemplateUseCase{
        return ViewTemplateUseCase(
            getTemplates = GetTemplates(repository)
        )
    }



}
