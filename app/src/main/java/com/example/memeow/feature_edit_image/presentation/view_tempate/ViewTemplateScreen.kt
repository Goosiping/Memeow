package com.example.memeow.feature_edit_image.presentation.view_tempate

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.memeow.R
import com.example.memeow.feature_edit_image.presentation.EditActivity
import com.example.memeow.feature_edit_image.presentation.component.MemeTemplateItem
import com.example.memeow.feature_main.presentation.explore.components.MainBar

private const val TAG = "EditViewTemplateScreen"

@Composable
fun EditViewTemplateScreen(
    viewModel: ViewTemplateViewModel = hiltViewModel(),
    onImageClick: (Uri) -> Unit,
    onEditButtonClick: (Uri) -> Unit = {}
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            MainBar(
                displaysearchbar = state.searchbaractivate,
                keyword = state.keyword,
                onTextChange = { viewModel.updatetext(newkeyword = it) }, // it from TextField onValueChange()
                onCloseClicked = {
                    viewModel.updatetext("")
                    viewModel.updatebar(false)
                },
                onSearchClicked = { viewModel.onEvent(ViewTemplateEvent.Search(keyword = state.keyword)) },
                onSearchTrigger = { viewModel.updatebar(true) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            LazyVerticalGrid(
                modifier = Modifier.weight(3f), columns = GridCells.Fixed(3)
            ) {
                items(state.templates) { meme ->
                    //Log.d("","imageUri = ${meme.image}")
                    MemeTemplateItem(
                        imageUri = meme.image,
                        onImageClick = { viewModel.onEvent(ViewTemplateEvent.PreviewTemplate(meme)) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            PreviewTemplateWindow(
                modifier = Modifier.weight(2f, false),
                imageUri = state.selectedTemplate?.image,
                selectOnClickMethod = {
                    onEditButtonClick(state.selectedTemplate?.image!!)
                    //LaunchEditPage(context,state.selectedTemplate?.image!!)
                    //
                    },
                buttonEnable = state.selectedTemplate?.image != null
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}


fun LaunchEditPage(context:Context,uri: Uri){
    val intent = Intent(context, EditActivity::class.java).apply {
        putExtra(EXTRA_MESSAGE, uri)
    }
    startActivity(context,intent,null)
}

@Composable
fun PreviewTemplateWindow(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    selectOnClickMethod:  () -> Unit,
    buttonEnable: Boolean
) {

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        color = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "預覽模板",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h6.fontSize,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit, //CROP?
                    modifier = Modifier
                        .clickable { /***GOTO EDIT**/ }
                        .fillMaxWidth()
                        .requiredHeight(200.dp)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(30.dp, 70.dp),
                onClick = {

                    selectOnClickMethod()
                },
                enabled = buttonEnable
            ) {
                Text(text = stringResource(id = R.string.edit_select_template_button))
            }

        }

    }
}

