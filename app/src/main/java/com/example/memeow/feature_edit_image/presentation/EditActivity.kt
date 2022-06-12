package com.example.memeow.feature_edit_image.presentation


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.memeow.R
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoEditorView
import ja.burhanrashid52.photoeditor.ViewType

class EditActivity : AppCompatActivity() {

    var mPhotoEditor: PhotoEditor? = null
    private var mPhotoEditorView: PhotoEditorView? = null


    private var addTextButton: AppCompatImageButton? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        mPhotoEditorView = findViewById(R.id.photoEditorView)
        mPhotoEditor = mPhotoEditorView?.run {
            PhotoEditor.Builder(this@EditActivity, this)
                .setPinchTextScalable(true) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build() // build photo editor sdk
        }


        addTextButton = findViewById(R.id.edit_add_text)
        addTextButton?.setOnClickListener{
            mPhotoEditor!!.addText("Hello", Color.parseColor("#FFFFFF"))
        }
    }



    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    override fun onStart() {
        super.onStart()

    }

}