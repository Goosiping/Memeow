package com.example.memeow.feature_edit_image.presentation.edit_image

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.memeow.R
import ja.burhanrashid52.photoeditor.PhotoEditorView
import ja.burhanrashid52.photoeditor.TextStyleBuilder


@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel<EditViewModel>(),
    imageUri: Uri,
    backMethod : () -> Unit
) {
    val photoEditorView: PhotoEditorView = rememberPhotoEditorView(imageUri, viewModel)
    val focusRequester = FocusRequester()



    /**Selecting a image*/
    var addUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        addUri = uri
        addUri.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it).copy(Bitmap.Config.ARGB_8888, false);
            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it!!)
                bitmap.value = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, false);
            }
        }
        viewModel.photoEditor.addImage(bitmap.value)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 12.dp)

    ) {
        IconButton(onClick = { backMethod() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
        }
        Text(
            stringResource(id = R.string.edit_page_title),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        IconButton(onClick = { viewModel.onEvent(EditEvent.SaveImg) }) {
            Icon(
                imageVector = Icons.Default.SaveAlt,
                contentDescription = "Back",
            )
        }
    }



    if (viewModel.state.value.isTyping) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black.copy(alpha = 0.5f)
                )
                .zIndex(2f),
            contentAlignment = Alignment.Center

        ) {
            Button(
                onClick = { viewModel.onEvent(event = EditEvent.StopTyping) }, modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(end = 16.dp)
            ) {
                Text("Done")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 150.dp)
            ) {
                var textState by remember { mutableStateOf(TextFieldValue("")) }

                BasicTextField(
                    value = textState,
                    onValueChange = {
                        textState = it
                        viewModel.updateCurText(it.text)
                    },
                    modifier = Modifier.focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.h4,

                    )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        }

    }

    Log.d("", "imageUri = $imageUri")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 80.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        PhotoEditorViewCompose(photoEditorView)
        //Spacer(modifier = Modifier.weight(1f, false))
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.1f)
                        )
                    )
                ),
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {

            Spacer(modifier = Modifier.width(6.dp))
            EditButton(
                iconImg = R.drawable.ic_baseline_undo_24,
                text = stringResource(R.string.edit_button_undo)
            ) {
                viewModel.photoEditor.undo()
            }
            EditButton(
                iconImg = R.drawable.ic_baseline_redo_24,
                text = stringResource(R.string.edit_button_redo)
            ) {
                viewModel.photoEditor.redo()
            }


            EditButton(
                iconImg = R.drawable.ic_baseline_text_fields_24,
                text = stringResource(R.string.edit_button_text)
            ) {
                viewModel.onEvent(EditEvent.StartTyping)

            }
            EditButton(
                iconImg = R.drawable.ic_baseline_image_24,
                text = stringResource(R.string.edit_button_image)
            ) {
                launcher.launch("image/*")
                //viewModel.onEvent(EditEvent.StartAddImage)

            }
            Spacer(modifier = Modifier.width(6.dp))

        }

    }
}


@Composable
fun PhotoEditorViewCompose(photeoEditorView: PhotoEditorView) {


    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(400.dp, 600.dp), // Occupy the max size in the Compose UI tree
        factory = { context ->
            // Creates custom view
            photeoEditorView
        },
        update = { view ->
            // View's been inflated or state read in this block has been updated
            // Add logic here if necessary

            // As selectedItem is read here, AndroidView will recompose
            // whenever the state changes
            // Example of Compose -> View communication

        }
    )

}


@Composable
fun rememberPhotoEditorView(imageUri: Uri, viewModel: EditViewModel): PhotoEditorView {

    val context = LocalContext.current
    return remember {
        val photoEditorView = PhotoEditorView(context).apply {
            // Sets up listeners for View -> Compose communication
            source.setImageURI(imageUri)
        }
        viewModel.setPhotoEditor(photoEditorView)
        photoEditorView
    }
}

@Composable
fun EditButton(@DrawableRes iconImg: Int, text: String, onClickMethod: () -> Unit) {

    IconButton(
        onClick = { onClickMethod() },
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = iconImg),
                contentDescription = text,
                modifier = Modifier.padding(3.dp)
            )
            Text(text)
        }
    }

}
