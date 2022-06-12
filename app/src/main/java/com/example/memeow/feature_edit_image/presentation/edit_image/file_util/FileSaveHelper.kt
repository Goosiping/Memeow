package com.example.memeow.feature_edit_image.presentation.edit_image.file_util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * From:https://github.com/burhanrashid52/PhotoEditor
 *
 * General contract of this class is to
 * create a file on a device.
 *
 * How to Use it-
 * Call [FileSaveHelper.createFile]
 * if file is created you would receive it's file path and Uri
 * and after you are done with File call [FileSaveHelper.notifyThatFileIsNowPubliclyAvailable]
 *
 * Remember! in order to shutdown executor call [FileSaveHelper.addObserver] or
 * create object with the [FileSaveHelper]
 */
class FileSaveHelper(private val mContentResolver: ContentResolver) : LifecycleObserver {

    companion object {
        fun isSdkHigherThan28(): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        }
    }

}