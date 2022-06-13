package com.example.memeow.feature_main.presentation.sigle_view

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memeow.feature_main.domain.use_case.MemeUseCases
import com.example.memeow.feature_main.presentation.explore.ExploreEvents
import com.example.memeow.feature_main.presentation.explore.ExploreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleViewViewModel @Inject constructor(
    private val memeUseCases: MemeUseCases
): ViewModel() {
    private val _state = mutableStateOf(SingleViewState())
    val state: State<SingleViewState> = _state

    private var getTagsJob: Job? = null

    fun onEvent(event: SingleViewEvents) {
        when(event){
            is SingleViewEvents.DeleteTag -> {
                viewModelScope.launch {
                    memeUseCases.deleteTagsByUri(listOf(event.tag), _state.value.uri)
                    getTags(_state.value.uri)
                }
            }
            is SingleViewEvents.InsertTag -> {
                viewModelScope.launch {
                    memeUseCases.addTagsByUri(listOf(event.tag), _state.value.uri)
                    getTags(_state.value.uri)
                }
            }
            is SingleViewEvents.PressPlus -> {
                _state.value = state.value.copy(
                    tagaddactivate = true
                )
            }
            is SingleViewEvents.Updatekeytag -> {
                _state.value = state.value.copy(
                    keytag = event.keytag
                )
            }
            is SingleViewEvents.PressSend -> {
                viewModelScope.launch {
                    memeUseCases.addTagsByUri(listOf(event.keytag), _state.value.uri)
                    _state.value = state.value.copy(
                        keytag = event.keytag,
                        tagaddactivate = false
                    )
                    getTags(_state.value.uri)
                }
            }
        }
    }

    fun getTags(uri: Uri){
        getTagsJob?.cancel()                 // cancel the subscription to the previous flow
        getTagsJob = memeUseCases.getTagsByUri(uri) // request the new flow
            .onEach { tags ->
                _state.value = state.value.copy( // flow overwrite the modified memes
                    uri = uri,
                    tags = tags
                )
            }
            .launchIn(viewModelScope)

    }
}