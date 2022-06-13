package com.example.memeow.feature_edit_image.presentation.component

import android.net.Uri
import android.text.BoringLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

//TODO
//Selecting  Effect
@Composable
fun MemeTemplateItem(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    onImageClick: (imageUri: Uri) -> Unit,
    isSelected:Boolean = false
) {

    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.border(if(!isSelected) 0.dp else 4.dp, MaterialTheme.colors.onPrimary,
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable {
                    onImageClick(imageUri)
                }
                .aspectRatio(1f)
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