package com.example.memeow.feature_keyboard.presentation.util

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes


object KeyboardUtil {
    private const val TAG = "CommitContent"

    @Throws(Resources.NotFoundException::class)
    fun getUriToResource(context: Context, @AnyRes resId: Int): Uri? {
        val res: Resources = context.getResources()
        return Uri.parse(
            ContentResolver.SCHEME_CONTENT+"://"+
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }

    /*For keyboard check if a text is "A single" letter */
    fun checkIsALetter(text: String): Boolean{
        if (text.length < 0)
            return false
        return text[0].isLetter() && (text.length == 1)
    }

}