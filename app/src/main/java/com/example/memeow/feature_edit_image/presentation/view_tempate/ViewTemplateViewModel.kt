package com.example.memeow.feature_edit_image.presentation.view_tempate

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memeow.feature_edit_image.presentation.domain.use_case.ViewTemplateUseCase
import com.example.memeow.feature_main.domain.model.Meme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewTemplateViewModel @Inject constructor(
    private val viewTemplateUseCase: ViewTemplateUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ViewTemplateState())
    val state: State<ViewTemplateState> = _state
    private var getTemplates: Job? = null

    init {
        getTemplates(keyword = null)
    }

    fun onEvent(event: ViewTemplateEvent) {
        when (event) {
            is ViewTemplateEvent.PreviewTemplate -> {
                updateSelectingTemplate(event.template)
            }
        }

    }

    private fun getTemplates(keyword: String?) {
        getTemplates?.cancel()                 // cancel the subscription to the previous flow
        getTemplates = viewTemplateUseCase.getTemplates() // request the new flow
            .onEach { memes ->
                _state.value = state.value.copy( // flow overwrite the modified memes
                    templates = if (keyword == null || keyword == "") memes else memes.filter { keyword in it.tags }// TODO: should have filtered in repository
                )
            }
            .launchIn(viewModelScope)
    }


    private fun updateSelectingTemplate(template: Meme) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                selectedTemplate = template
            )
        }
    }


    /** For top bar searching**/
    fun updatetext(newkeyword: String) {
        _state.value = state.value.copy(
            keyword = newkeyword
        )
    }

    fun updatebar(newsearchbaractivate: Boolean) {
        _state.value = state.value.copy(
            searchbaractivate = newsearchbaractivate
        )
    }


}