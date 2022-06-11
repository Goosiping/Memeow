package com.example.memeow.feature_keyboard.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.*
import com.example.memeow.di.AppModule
import com.example.memeow.feature_keyboard.domain.use_case.KeyboardUseCases
import com.example.memeow.feature_keyboard.domain.use_case.SendMeme
import com.example.memeow.feature_main.data.data_source.remote.MemeApi
import com.example.memeow.feature_main.data.repository.FakeMemeRepository
import com.example.memeow.feature_main.domain.model.Meme
import com.example.memeow.feature_main.domain.repository.MemeRepository
import com.example.memeow.feature_main.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.AccessController.getContext
import javax.inject.Inject



class KeyboardViewModel(
    context: Context,
    application: Application
): ViewModel() {

    /*I realllly don't know why if i put keyboardUseCases in constructor parameter to let hilt injec
    t, it will be broken. SO i change into without @HiltVieModel and @Inject constuctor
    * so i move it to here
    * */
    private lateinit var keyboardUseCases: KeyboardUseCases
    private lateinit var memeUseCases: MemeUseCases
    private lateinit var repository: MemeRepository

    private val _state = mutableStateOf(KeyboardState())
    val state: State<KeyboardState> = _state

    private val TAG ="KeyboardViewModel"
    private val observableEvents = MutableLiveData<KeyboardViewModelEvent>()
    fun observeViewModelEvents(): LiveData<KeyboardViewModelEvent> = observableEvents

    private var getMemesJob: Job? = null

    init {
        //getMemes(null)
    }

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


    init {
        Log.i(TAG,"TRY TO INIT")
        keyboardUseCases = KeyboardUseCases(
            sendMeme = SendMeme()
        )
        val api = AppModule.provideDictionaryApi()
        val db = AppModule.provideMemeDatabase(application)
        val repository = AppModule.provideNoteRepository(context, db,api)
        memeUseCases = AppModule.provideNoteUseCases(repository)

        getMemes(null)
        //AppModule.provideNoteUseCases(AppModule.provideDictionaryApi())

    }
}