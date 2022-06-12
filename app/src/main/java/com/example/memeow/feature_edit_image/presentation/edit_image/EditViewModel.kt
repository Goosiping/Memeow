package com.example.memeow.feature_edit_image.presentation.edit_image

import android.R.attr.bitmap
import android.content.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import com.example.memeow.R
import com.example.memeow.SettingValues
import com.example.memeow.feature_edit_image.presentation.edit_image.file_util.FileSaveHelper.Companion.isSdkHigherThan28
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ja.burhanrashid52.photoeditor.*
import java.io.*
import javax.inject.Inject


/** We need font
 *
 *
 * */
@HiltViewModel
class EditViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {
    val TAG = "EditViewModel"

    lateinit var photoEditor: PhotoEditor

    val contentResolver: ContentResolver = context.contentResolver

    var mSaveImageUri: Uri? = null

    val context = context //:(

    private val _state = mutableStateOf(EditImageState())
    val state: State<EditImageState> = _state

    val styleBuilder = TextStyleBuilder()

    init {
        styleBuilder.withTextSize(25f)
    }
    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.StartTyping -> {
                changeTypingStatus(true)
            }
            is EditEvent.SaveImg -> {
                //saveImage()
                saveBitmap()
            }
            is EditEvent.StopTyping ->{
                styleBuilder.withTextColor(state.value.selectColor)
                changeTypingStatus(false)
                photoEditor.addText(state.value.curText, styleBuilder)
            }
        }

    }

    fun changeTypingStatus(status: Boolean){
        _state.value = state.value.copy(
            isTyping = status
        )
    }
    fun showLoading(text: String) {}
    fun hideLoading() {}
    fun showSnackbar(text: String) {
        Log.i(TAG, text)

    }


    /**Just can reference this sample code*/
    private fun shareImage() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        val saveImageUri = mSaveImageUri
        if (saveImageUri == null) {
            return
        }
        //can lookup to that photoeditor github
        //intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(saveImageUri))
        //startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)))
    }


    fun setPhotoEditor(photoEditorView: PhotoEditorView) {
        Log.i(TAG, "set photoEditor")
        val mTextRobotoTf = ResourcesCompat.getFont(context, R.font.roboto_medium)
        photoEditor = PhotoEditor.Builder(context, photoEditorView)
            .setPinchTextScalable(true)
            .setDefaultTextTypeface(mTextRobotoTf)
            .setClipSourceImage(true)
            .build()
    }


    fun saveBitmap() {
        if (isSdkHigherThan28()) {
            val fileName = System.currentTimeMillis().toString()
            if(isSdkHigherThan28()){

            photoEditor.saveAsBitmap(SaveSettings.Builder().setClearViewsEnabled(false).build(),
                object : OnSaveBitmap {
                    override fun onBitmapReady(saveBitmap: Bitmap?) {
                        if (saveBitmap != null) {
                            saveImageToStorage(saveBitmap, fileName)
                        }
                    }


                    override fun onFailure(e: Exception?) {
                        Log.i(TAG,"FIALUE")
                    }
                })

        } else {

            /****Save with old method****/
        }
    }
    }


    @Throws(IOException::class)
    private fun saveImageToStorage(bitmapObject: Bitmap, fileName:String) {
        val imageOutStream: OutputStream
        val storagePath = getCurrentImageFolderPath()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            //values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            values.put(MediaStore.Images.Media.RELATIVE_PATH, storagePath)
            //values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, SettingValues.ALBUM_NAME)
            val uri: Uri? =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageOutStream = uri?.let { contentResolver.openOutputStream(it) }!!
        } else {
            val imagePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val image = File(imagePath, "$fileName.jpg")
            imageOutStream = FileOutputStream(image)
        }
        try {
            bitmapObject.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
        } finally {
            imageOutStream.close()
        }
    }


    fun getCurrentImageFolderPath():String? {

        val projection = arrayOf(MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.RELATIVE_PATH
        )
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(SettingValues.ALBUM_NAME)
        val sortOrder ="${MediaStore.Images.Media.DATE_ADDED} DESC"
        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                return cursor.getString(4)
            }
        }
        return null
    }

    fun updateCurText(text: String){
        _state.value = state.value.copy(
            curText = text
        )
    }


}