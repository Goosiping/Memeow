package com.example.memeow.feature_main.presentation.explore.components

import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Label
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// reference: https://www.youtube.com/watch?v=3oXBnM6fZj0
@Composable
fun MainBar(
    displaysearchbar: Boolean,
    keyword: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTrigger: () -> Unit
){
    when(displaysearchbar){
        false -> {
            DefaultAppBar(onSearchClicked = onSearchTrigger)
        }
        true-> {
            SearchBar(
                text = keyword,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        /*elevation = */
        color = MaterialTheme.colors.surface
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {onTextChange(it)},
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search for tag:"
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isEmpty()){
                            onCloseClicked()
                        }else{
                            onTextChange("")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            )
        )
    }

    /**
    TextField(
    modifier = Modifier.fillMaxWidth(),
    value = text,
    onValueChange = {onTextChange(it)},
    placeholder = {
    Text(
    modifier = Modifier.alpha(ContentAlpha.medium),
    text = "Search for tag:"
    )
    },
    textStyle = TextStyle(
    fontSize = MaterialTheme.typography.subtitle1.fontSize
    ),
    singleLine = true,
    leadingIcon = {
    IconButton(
    modifier = Modifier.alpha(ContentAlpha.medium),
    onClick = {}
    ) {
    Icon(
    imageVector = Icons.Default.Search,
    contentDescription = "Search icon"
    )
    }
    },
    trailingIcon = {
    IconButton(
    onClick = {
    if (text.isEmpty()){
    onCloseClicked()
    }else{
    onTextChange("")
    }
    }
    ) {
    Icon(
    imageVector = Icons.Default.Close,
    contentDescription = "close icon"
    )
    }
    },
    keyboardOptions = KeyboardOptions(
    imeAction = ImeAction.Search
    ),
    keyboardActions = KeyboardActions(
    onSearch = {
    onSearchClicked(text)
    }
    ),
    colors = TextFieldDefaults.textFieldColors(
    backgroundColor = Color.Transparent,
    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
    )
    )*/


}

@Composable
fun DefaultAppBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Memeow",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            IconButton(
                onClick = { /*TODO?*/ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Label,
                    contentDescription = "Toggle Tag",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            IconButton(
                onClick = { /*TODO?*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(
                onClick = { onSearchClicked() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        modifier = Modifier.height(60.dp)
    )
}

@Composable
@Preview
fun SearchBarPreview() {
    SearchBar(
        text = "TEXT!",
        onTextChange = { },
        onCloseClicked = { },
        onSearchClicked = { }
    )
}

@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(
        onSearchClicked = { }
    )
}