package com.example.memeow.feature_main.presentation.sigle_view

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.ImageLoader
import coil.request.SuccessResult
import com.example.memeow.feature_main.presentation.components.tagChip
import com.example.memeow.feature_main.presentation.components.topBar
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import java.io.OutputStream


@Composable
fun singleViewBody(
    viewModel: SingleViewViewModel = hiltViewModel(),
    imageUri: Uri,
    onClickBack: () -> Unit,
    onClickEdit: () -> Unit
){
    Log.d("","imageUri = $imageUri")
    viewModel.getTags(imageUri)


    val state = viewModel.state.value

    val tags: List<String> = state.tags

    val context = LocalContext.current


    Scaffold(
        topBar = {
            topBar(
                title = "梗圖",
                onClick = { onClickBack() }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                IconButton(
                    onClick = { onClickEdit() },
                    modifier = Modifier.padding(vertical = 4.dp, horizontal =  48.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "編輯"
                        )
                    }
                }
                IconButton(
                    onClick = { shareImage(context, imageUri) },
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "分享"
                        )
                    }
                }
                IconButton(
                    onClick = { /**DELETE MEME**/ },
                    modifier = Modifier.padding(vertical = 4.dp, horizontal =  48.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "刪除"
                        )
                    }
                }
            }
        }
    ){ innterPadding ->
        Box(
            modifier = Modifier.padding(innterPadding)
        ){
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Box(modifier = Modifier)

                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(0.dp, 512.dp)
                )
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column() {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tags",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.padding(16.dp)
                            )
                            when(state.tagaddactivate){
                                false -> {
                                    FloatingActionButton(
                                        modifier = Modifier.size(50.dp),
                                        backgroundColor = MaterialTheme.colors.onPrimary,
                                        onClick = { viewModel.onEvent(SingleViewEvents.PressPlus) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null
                                        )
                                    }
                                }
                                true-> {
                                    TextField(
                                        modifier = Modifier.fillMaxWidth(),
                                        value = state.keytag,
                                        onValueChange = { viewModel.onEvent(SingleViewEvents.Updatekeytag(it)) },
                                        placeholder = {
                                            Text(
                                                modifier = Modifier.alpha(ContentAlpha.medium),
                                                text = "tag"
                                            )
                                        },
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                viewModel.onEvent(SingleViewEvents.PressSend(state.keytag) )
                                            }
                                        )
                                    )
                                }
                            }
                        }

                        FlowRow(
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                        ){
                            tags.forEach{
                                tagChip(
                                    text = it,
                                    onClick = { /** EDIT TAG **/ },
                                    onClose = { viewModel.onEvent(SingleViewEvents.DeleteTag(it))}
                                )
                            }
                        }
                    }


                }
            }
        }

    }
}

private fun shareImage(context: Context, uri: Uri) {

    GlobalScope.launch {
        val bitmap = UritoBitmap(uri, context)

        val inneruri = saveImageInQ(bitmap, context)

        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        intent.putExtra(Intent.EXTRA_STREAM, inneruri)
        startActivity(context, Intent.createChooser(intent, "Share"), null)
    }

}

private suspend fun UritoBitmap(uri: Uri, context: Context): Bitmap {

    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(uri.toString())
        .allowHardware(false) // Disable hardware bitmaps.
        .build()

    val result = (loader.execute(request) as SuccessResult).drawable

    return (result as BitmapDrawable).bitmap
}

private fun saveImageInQ(bitmap: Bitmap, context: Context):Uri? {
    val filename = "IMG_${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    var imageUri: Uri? = null
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Video.Media.IS_PENDING, 1)
    }

    //use application context to get contentResolver
    val contentResolver = context.contentResolver

    contentResolver.also { resolver ->
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) }
    }

    fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it) }

    contentValues.clear()
    contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
    imageUri?.let { contentResolver.update(it, contentValues, null, null) }

    return imageUri
}
