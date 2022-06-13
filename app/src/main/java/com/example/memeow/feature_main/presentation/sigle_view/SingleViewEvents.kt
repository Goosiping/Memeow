package com.example.memeow.feature_main.presentation.sigle_view

import android.net.Uri
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.presentation.explore.ExploreEvents

sealed class SingleViewEvents{
    data class GetTags(val uri: Uri): SingleViewEvents()
    data class InsertTag(val tag: String): SingleViewEvents()
    data class DeleteTag(val tag: String): SingleViewEvents()
    data class Updatekeytag(val keytag: String): SingleViewEvents()
    data class PressSend(val keytag: String): SingleViewEvents()
    object PressPlus: SingleViewEvents()
}

