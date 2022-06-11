package com.example.memeow.di

import com.example.memeow.feature_keyboard.domain.use_case.KeyboardUseCases
import com.example.memeow.feature_keyboard.domain.use_case.SendMeme
import com.example.memeow.feature_main.data.repository.FakeMemeRepository
import com.example.memeow.feature_main.domain.repository.MemeRepository
import com.example.memeow.feature_main.domain.use_case.AddMeme
import com.example.memeow.feature_main.domain.use_case.DeleteMeme
import com.example.memeow.feature_main.domain.use_case.GetMemes
import com.example.memeow.feature_main.domain.use_case.MemeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*Database*/

    /*Repository*/
    @Provides
    @Singleton
    fun provideNoteRepository(/*db: MemeDatabase*/): MemeRepository {
        return FakeMemeRepository()
    }

    /*UseCases*/
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: MemeRepository): MemeUseCases {
        return MemeUseCases(
            getMemes = GetMemes(repository),
            deleteMeme = DeleteMeme(repository),
            addMeme = AddMeme(repository)
        )
    }


    /*----Keyboard-------*/
    /*UseCases*/
    @Provides
    @Singleton
    fun provideKeyboardUseCases(): KeyboardUseCases {
        return KeyboardUseCases(
            sendMeme = SendMeme()
        )
    }

}