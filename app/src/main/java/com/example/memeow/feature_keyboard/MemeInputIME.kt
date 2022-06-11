package com.example.memeow.feature_keyboard


import android.app.AppOpsManager
import android.content.ClipDescription
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import androidx.core.view.inputmethod.InputConnectionCompat
import androidx.core.view.inputmethod.InputContentInfoCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.example.memeow.feature_keyboard.presentation.*

import com.example.memeow.feature_keyboard.presentation.util.KeyboardUtil
import com.example.memeow.ui.theme.MemeowTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.properties.Delegates


/*A custom keyboard is a Inpute method service
* https://developer.android.com/guide/topics/text/creating-input-method
* */

@AndroidEntryPoint
class MemeInputIME : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private val keyboardViewLifecycleOwner = KeyboardViewLifecycleOwner()


    lateinit var view: KeyboardCustomView

    override fun onCreate() {
        super.onCreate()
        keyboardViewLifecycleOwner.onCreate()
    }


    inner class KeyboardCustomView(context: Context) : AbstractComposeView(context) {

        lateinit var viewModel:KeyboardViewModel

        @Composable
        override fun Content() {
            MemeowTheme {
                //viewModel = hiltViewModel<KeyboardViewModel>()
                viewModel = KeyboardViewModel(context,application)
                viewModel.observeViewModelEvents().observe(keyboardViewLifecycleOwner, Observer {
                    val event = it.takeUnless { it == null || it.handled } ?: return@Observer
                    handleViewModelAction(event)
                })

                KeyboardScreen(viewModel = viewModel)
            }
        }

    }

    override fun onCreateInputView(): View {
        //Compose uses the decor view to locate the "owner" instances
        keyboardViewLifecycleOwner.attachToDecorView(
            window?.window?.decorView
        )



        view = KeyboardCustomView(this)
        /*
        view.viewModel.observeViewModelEvents().observe(keyboardViewLifecycleOwner, Observer {
            val event = it.takeUnless { it == null || it.handled } ?: return@Observer
            handleViewModelAction(event)
        })*/
        return view
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        keyboardViewLifecycleOwner.onResume()




    }

    override fun onFinishInputView(finishingInput: Boolean) {
        keyboardViewLifecycleOwner.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardViewLifecycleOwner.onDestroy()
    }

    protected open fun handleViewModelAction(event: KeyboardViewModelEvent) {


        when(event){
            is KeyboardSendMemeEvent ->{
                val sendingUri = event.meme.image
                Log.i(TAG,"handleViewModelAction, SendURI() ${event.meme} $sendingUri")
                doCommitContent("Send a meme", MIME_TYPE_PNG, sendingUri)

                Log.i(TAG,"$currentInputConnection ${currentInputEditorInfo.packageName}  ${currentInputEditorInfo.label}")
                }
            is KeyboardSendTextEvent ->{
                Log.i(TAG,"handleViewModelAction, SendText(${event.text})")
                //commitText(event.text)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //TODO
                    Log.i(TAG,"currentInputConnection.closeConnection()")
                    //currentInputConnection.closeConnection()

                }
            }



        }
        event.handle(this)

    }


    /** typing text*/
    private fun commitText(text : String){
        currentInputConnection.also { ic: InputConnection ->
            ic.commitText(text, text.length)
        }

    }

    /***sending image related*/


    private fun doCommitContent(
        description: String, mimeType: String,
        uri: Uri? = null
    ) {
        val editorInfo = currentInputEditorInfo

        // Validate packageName again just in case.
        if (!validatePackageName(editorInfo)) {
            return
        }

        /*if contentUri is null, we use file to get uri.
            contentUri = FileProvider.getUriForFile(this, AUTHORITY, file!!)
          */
        // As you as an IME author are most likely to have to implement your own content provider
        // to support CommitContent API, it is important to have a clear spec about what
        // applications are going to be allowed to access the content that your are going to share.
        var flag by Delegates.notNull<Int>()

        if (Build.VERSION.SDK_INT >= 25) {
            // On API 25 and later devices, as an analogy of Intent.FLAG_GRANT_READ_URI_PERMISSION,
            // you can specify InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION to give
            // a temporary read access to the recipient application without exporting your content
            // provider.
            flag = InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION
        } else {
            /**For demo only, we can make sure our device version.... :P */
            // On API 24 and prior devices, we cannot rely on
            // InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION. You as an IME author
            // need to decide what access control is needed (or not needed) for content URIs that
            // you are going to expose. This sample uses Context.grantUriPermission(), but you can
            // implement your own mechanism that satisfies your own requirements.
            /*
            flag = 0
            try {
                // TODO: Use revokeUriPermission to revoke as needed.
                grantUriPermission(
                    editorInfo.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: Exception) {
                Log.e(
                    TAG, "grantUriPermission failed packageName=" + editorInfo.packageName
                            + " contentUri=" + contentUri, e
                )
            }*/
        }
        val inputContentInfoCompat = InputContentInfoCompat(
            uri!!,
            ClipDescription(description, arrayOf(mimeType)),
            null /* linkUrl */
        )
        InputConnectionCompat.commitContent(
            currentInputConnection, currentInputEditorInfo, inputContentInfoCompat,
            flag, null
        )
    }


    private fun validatePackageName(editorInfo: EditorInfo?): Boolean {
        if (editorInfo == null) {
            return false
        }
        val packageName = editorInfo.packageName ?: return false

        // In Android L MR-1 and prior devices, EditorInfo.packageName is not a reliable identifier
        // of the target application because:
        //   1. the system does not verify it [1]
        //   2. InputMethodManager.startInputInner() had filled EditorInfo.packageName with
        //      view.getContext().getPackageName() [2]
        // [1]: https://android.googlesource.com/platform/frameworks/base/+/a0f3ad1b5aabe04d9eb1df8bad34124b826ab641
        // [2]: https://android.googlesource.com/platform/frameworks/base/+/02df328f0cd12f2af87ca96ecf5819c8a3470dc8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true
        }
        val inputBinding = currentInputBinding
        if (inputBinding == null) {
            // Due to b.android.com/225029, it is possible that getCurrentInputBinding() returns
            // null even after onStartInputView() is called.
            // TODO: Come up with a way to work around this bug....
            Log.e(
                TAG, "inputBinding should not be null here. "
                        + "You are likely to be hitting b.android.com/225029"
            )
            return false
        }
        val packageUid = inputBinding.uid
        val appOpsManager = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        try {
            appOpsManager.checkPackage(packageUid, packageName)
        } catch (e: Exception) {
            return false
        }
        return true

    }


    companion object{
        private const val TAG = "MemeInputIME"
        private const val MIME_TYPE_GIF = "image/gif"
        private const val MIME_TYPE_PNG = "image/png"
        private const val MIME_TYPE_WEBP = "image/webp"

    }

    override fun onPress(primaryCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onRelease(primaryCode: Int) {
        TODO("Not yet implemented")
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        TODO("Not yet implemented")
    }

    override fun onText(text: CharSequence?) {
        TODO("Not yet implemented")
    }

    override fun swipeLeft() {
        TODO("Not yet implemented")
    }

    override fun swipeRight() {
        TODO("Not yet implemented")
    }

    override fun swipeDown() {
        TODO("Not yet implemented")
    }

    override fun swipeUp() {
        TODO("Not yet implemented")
    }

}