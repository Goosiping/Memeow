package com.example.memeow.feature_main.presentation.explore

import android.provider.ContactsContract
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.use_case.MemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val memeUseCases: MemeUseCases
): ViewModel() {

    private val _state = mutableStateOf(ExploreState())
    val state: State<ExploreState> = _state

    private var recentlyDeletedMeme: Meme? = null

    private var getMemesJob: Job? = null

    init {
        getMemes(null)
    }

    fun onEvent(event: ExploreEvents) {
        when(event) {
            is ExploreEvents.Search -> {
                getMemes(event.keyword)
            }
            is ExploreEvents.DeleteMeme -> {
                viewModelScope.launch{
                    memeUseCases.deleteMeme(event.meme) //ExploreEvents.DeleteMeme val meme
                    recentlyDeletedMeme = event.meme
                }
            }
            is ExploreEvents.RestoreMeme -> {
                viewModelScope.launch{
                    memeUseCases.addMeme(recentlyDeletedMeme?:return@launch) // recentlyDeletedMeme is nullable
                    recentlyDeletedMeme = null
                }
            }
        }
    }

    private fun getMemes(keyword: String?){
        getMemesJob?.cancel()                 // cancel the subscription to the previous flow
        getMemesJob = memeUseCases.getMemes() // request the new flow
            .onEach { memes ->
                _state.value = state.value.copy( // flow overwrite the modified memes
                    memes = if (keyword == null || keyword == "") memes  else  memes.filter{ keyword in it.tags }// TODO: should have filtered in repository
                )
            }
            .launchIn(viewModelScope)
    }
    /*
     data class copy() can make a clone
     https://carterchen247.medium.com/kotlin%E4%BD%BF%E7%94%A8%E5%BF%83%E5%BE%97-%E5%85%AD-data-class-1689b1782abb
     Why we don't call collect() on flow? see:
     https://handstandsam.com/2021/02/19/the-best-way-to-collect-a-flow-in-kotlin-launchin/
    */

    fun updatetext(newkeyword: String){
        _state.value = state.value.copy(
            keyword = newkeyword
        )
    }

    fun updatebar(newsearchbaractivate: Boolean){
        _state.value = state.value.copy(
            searchbaractivate = newsearchbaractivate
        )
    }
}