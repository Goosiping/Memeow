package com.example.memeow.feature_edit_image.presentation.view_tempate

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.memeow.R
import com.example.memeow.feature_edit_image.presentation.component.MemeTemplateItem
import com.example.memeow.feature_main.presentation.explore.components.MainBar

private const val TAG = "EditViewTemplateScreen"

@Composable
fun EditViewTemplateScreen(
    viewModel: ViewTemplateViewModel = hiltViewModel(),
    onImageClick: (Uri) -> Unit,
    onEditButtonClick: (Uri?) -> Unit = {}
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
                        onImageClick = { viewModel.onEvent(ViewTemplateEvent.PreviewTemplate(meme)) },
                        modifier = Modifier.padding(2.dp),
                        isSelected = (meme.image == state.selectedTemplate?.image)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            PreviewTemplateWindow(
                modifier = Modifier.weight(2f, false),
                imageUri = state.selectedTemplate?.image,
                selectOnClickMethod = {
                    if(state.selectedTemplate == null){
                        /**default blank canvas*/
                        val uri: Uri? = Uri.parse("android.resource://com.example.memeow/drawable/blank");
                        onEditButtonClick(uri)
                    }
                    else{
                        onEditButtonClick(state.selectedTemplate.image)
                    }
                    //LaunchEditPage(context,state.selectedTemplate?.image!!)
                    //
                    },
                hasSelectImg = state.selectedTemplate?.image != null
            )

            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PreviewTemplateWindow(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    selectOnClickMethod:  () -> Unit,
    hasSelectImg: Boolean
) {

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        color = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {

            if (imageUri != null) {
                Crossfade(targetState = imageUri, animationSpec = tween(500)){ imageUri ->
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
            }else{
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.Black.copy(alpha = 0.02f)
                        ),
                    verticalArrangement = Arrangement.Center
                ){
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.edit_select_template_empty),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()

                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

            }


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                ,
                onClick = {
                    selectOnClickMethod()
                },
                //enabled = buttonEnable,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colors.onPrimary,
                    contentColor = Color.White)
            ) {

                Text(text = if(hasSelectImg) stringResource(id = R.string.edit_select_template_button)
                    else stringResource(id = R.string.edit_select_template_blank),
                    fontSize = MaterialTheme.typography.button.fontSize,
                    color = Color.DarkGray

                )
            }

        }

    }
}

