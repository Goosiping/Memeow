package com.example.memeow.feature_keyboard.presentation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.example.memeow.feature_keyboard.domain.use_case.KeyboardUseCases
import com.example.memeow.feature_keyboard.domain.use_case.SendMeme

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class KeyboardViewModel @Inject constructor(
): ViewModel() {

    /*I realllly don't know why if i put keyboardUseCases in constructor parameter to let hilt inject, it will be broken.
    * so i move it to here
    * */

    private lateinit var keyboardUseCases: KeyboardUseCases


    private val _state = mutableStateOf(KeyboardState())
    val state: State<KeyboardState> = _state

    private val TAG ="KeyboardViewModel"
    private val observableEvents = MutableLiveData<KeyboardViewModelEvent>()
    fun observeViewModelEvents(): LiveData<KeyboardViewModelEvent> = observableEvents

    private fun postViewModelEvent(event: KeyboardViewModelEvent) {
        observableEvents.postValue(event)
    }



    fun onEvent(event: KeyboardEvent){
        when(event){
            is KeyboardEvent.ClickMeme ->{
                viewModelScope.launch {
                    keyboardUseCases.sendMeme(event.meme)
                    Log.i(TAG,"SEND MEME()")
                    postViewModelEvent(KeyboardSendMemeEvent(event.meme))

                }
            }
            is KeyboardEvent.TypeText ->{
                viewModelScope.launch {
                    /* This is sending to outside editor
                    Log.i(TAG,"SEND TEXT(${event.text})")
                    postViewModelEvent(KeyboardSendTextEvent(event.text))
                    _state.value = state.value.copy(
                        keyword = state.value.keyword + event.text
                    )*/

                    /*This is typing text in keyboard's text editor*/
                    val addedText = state.value.searchTextFieldValue.text.plus(event.text)
                    val selection = TextRange(addedText.length, addedText.length)
                    _state.value =  state.value.copy(
                        searchTextFieldValue = state.value.searchTextFieldValue.copy(
                            text =  addedText,
                            selection = selection
                        )
                    )
                    state.value.searchTextFieldValue.annotatedString
                }
            }
            is KeyboardEvent.TouchSearchBar ->{
                updateShowKeyboard(true)
            }
            is KeyboardEvent.DeleteText ->{
                Log.i(TAG,"DELETE TEXT")
                val lenOfCurrentText = state.value.searchTextFieldValue.text.length
                if (lenOfCurrentText == 0) return

                val deletedText =  state.value.searchTextFieldValue.text.slice(0 until lenOfCurrentText -1)
                val selection = TextRange(lenOfCurrentText-1, lenOfCurrentText -1)
                updateSearchTextFieldState(deletedText, selection)
            }
            is KeyboardEvent.ReturnText ->{
                /*TODO*/
                /** 1. Hide keyboard
                 *  2. Searching by keyword, and display result.*/
                updateShowKeyboard(false)
            }
            is KeyboardEvent.CapsLock->{
                toggleCapsLock()
            }


        }
    }

    private fun updateSearchTextFieldState(newText: String, newSelection: TextRange){
        _state.value =  state.value.copy(
            searchTextFieldValue = state.value.searchTextFieldValue.copy(
                text =  newText,
                selection = newSelection,

            )
        )
    }

    private fun updateShowKeyboard(status: Boolean){
        viewModelScope.launch {
            _state.value = state.value.copy(
                showTextKeyboard = status
            )
        }

    }
    private fun toggleCapsLock(){
        viewModelScope.launch {
            Log.i(TAG,"caps lock")
            _state.value = state.value.copy(
                isCapsLock = !(state.value.isCapsLock)
            )
        }

    }

    @Composable
    fun sendMeme(){
        val context = LocalContext.current
    }




    init {
        Log.i(TAG,"TRY TO INIT")
        keyboardUseCases = KeyboardUseCases(
            sendMeme = SendMeme()
        )
    }
}