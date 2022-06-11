package com.example.memeow.feature_main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun tagChip(
    text: String,
    onClick: () -> Unit,
    onClose: () -> Unit
) {
    val shape = RoundedCornerShape(12.dp)
    Box(contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    vertical = 1.dp,
                    horizontal = 4.dp
                )
                .background(
                    MaterialTheme.colors.secondaryVariant,
                    shape = shape
                )
                .clip(shape = shape)
                .clickable {
                    onClick()
                }
                .padding(8.dp)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onSecondary,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.clickable { onClose() }
            )
        }
    }
}