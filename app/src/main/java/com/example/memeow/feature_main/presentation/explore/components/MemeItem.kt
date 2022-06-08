package com.example.memeow.feature_main.presentation.explore.components

import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.tooling.preview.Preview
import com.example.memeow.R

@Composable
fun MemeItem(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier,
    onImageClick: (drawable: Int) -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(168.dp)//the image size display in lazy grid
                .clickable {
                    onImageClick(drawable)
                }
        )
    }
}

@Composable
@Preview
fun MemeItemPreview() {
    MemeItem(
        drawable = R.drawable.cat_1,
        onImageClick = {}
    )
}