package com.example.memeow.feature_edit_image.presentation.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

//TODO
//Selecting  Effect
@Composable
fun MemeTemplateItem(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    onImageClick: (imageUri: Uri) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(168.dp)//the image size display in lazy grid
                .clickable {
                    onImageClick(imageUri)
                }
        )
    }
}


/*
@Composable
@Preview
fun MemeItemPreview() {
    MemeItem(
        drawable = R.drawable.cat_1,
        onImageClick = {}
    )
}
*/