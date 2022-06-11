package com.example.memeow.feature_keyboard.presentation

import android.net.Uri
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.memeow.R
import com.example.memeow.feature_keyboard.presentation.util.KeyboardUtil
import com.example.memeow.feature_main.domain.model.Meme


@Composable
fun KeyboardScreen(viewModel: KeyboardViewModel = hiltViewModel()) {

    val TAG = "KeyboardScreen"

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    val focusManager = LocalFocusManager.current

    val keysMatrix = arrayOf(
        arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        arrayOf("caps", "z", "x", "c", "v", "b", "n", "m", "<"),
        arrayOf("global", "_", " ", "ENTER")
    )




    val tags = mutableListOf<String>(
        "柴犬", "哭哭貓", "海綿寶寶"
    )

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .height(430.dp)

            .padding(vertical = 16.dp)
    ) {


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .align(Alignment.End),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                SearchBar(
                    state.searchTextFieldValue,
                    Modifier
                        .weight(1f)
                        .heightIn(min = 50.dp),
                    onClickMethod = {
                        viewModel.onEvent(KeyboardEvent.TouchSearchBar)
                    }
                )
                RandomButton(
                    Modifier
                        .padding(start = 8.dp)
                        .size(50.dp)
                )
            }
        }


        /**English keyboard*/
        if (state.showTextKeyboard) {
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                keysMatrix.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        row.forEach { key ->
                            val resultKey =  if(state.isCapsLock && KeyboardUtil.checkIsALetter(key)) key.uppercase() else key
                            KeyboardKey(
                                keyboardKey = resultKey,
                                modifier = Modifier,
                                onClickMethodChar = { viewModel.onEvent(KeyboardEvent.TypeText(resultKey)) },
                                onClickMethodCaps = { viewModel.onEvent(KeyboardEvent.CapsLock) },
                                onClickMethodDel = { viewModel.onEvent(KeyboardEvent.DeleteText) },
                                onClickMethodReturn = {
                                    focusManager.clearFocus()
                                    viewModel.onEvent(KeyboardEvent.ReturnText)
                                }
                            )

                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }
        }else{
            /**Keyboard Memes Grid*/
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .height(300.dp)//TODO?
                    .padding(horizontal = 18.dp)
                    .padding(top = 12.dp),
            ) {
                items(state.memes) { item ->
                    KeyboardMemeCard(item.image,
                        onClickMethod = { viewModel.onEvent(KeyboardEvent.ClickMeme(item)) }
                    )
                }

            }

        }
        KeyboardBottomBar(tags = tags)

    }


}


@Composable
fun SearchBar(
    textState: TextFieldValue,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.body1.fontSize,
    onClickMethod: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()

    if (isPressed) {
        onClickMethod()
    }


    BasicTextField(
        modifier = modifier,
        value = textState,
        onValueChange = {
        },
        interactionSource = interactionSource,
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        decorationBox = { innerTextField ->
            Row(
                modifier.background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(10.dp)
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    Modifier.padding(5.dp)
                )
                Box {
                    if (textState.text.isEmpty())
                        Text(
                            stringResource(id = R.string.keyboard_placeholder_search),
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                fontSize = fontSize
                            )
                        )
                    innerTextField()
                }
            }

        }
    )
}

@Composable
fun RandomButton(
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { Log.i("TEST", "Button Click!") },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_icon_dice),
            contentDescription = "Send a random meme",
            tint = MaterialTheme.colors.primaryVariant,
            modifier = modifier.padding(3.dp)
        )
    }

}

@Composable
fun KeyboardMemeCard(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    onClickMethod: () -> Unit = {}
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f) //square shape
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(
                        onClick = onClickMethod
                        //Log.i(TAG, "You click an image from keyboard")
                    )

            )
        }
    }
}


@Composable
fun KeyboardBottomBar(
    modifier: Modifier = Modifier,
    tags: List<String>
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        color = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {
        val textChipRememberOneState = remember {
            mutableStateOf(false)
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)

        ) {
            Icon(
                Icons.Default.BookmarkBorder,
                "BookMark"
            )
            LazyRow(
                //horizontalArrangement = Arrangement.Start,
                //verticalAlignment = Alignment.CenterVertically
            ) {
                items(tags) { item ->
                    KeyboardChip(isSelected = textChipRememberOneState.value, text = item,
                        onChecked = { textChipRememberOneState.value = it })
                }
            }
        }


    }
}


@Composable
fun KeyboardChip(
    isSelected: Boolean,
    text: String,
    onChecked: (Boolean) -> Unit,
) {
    val shape = RoundedCornerShape(6.dp)
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
                    color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.secondaryVariant,
                    shape = shape
                )
                .clip(shape = shape)
                .clickable {
                    onChecked(!isSelected)
                }
                .padding(8.dp)
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onSecondary,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}


@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier,
    onClickMethodChar: (String) -> Unit,
    onClickMethodDel: () -> Unit,
    onClickMethodCaps: () -> Unit,
    onClickMethodReturn: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    Surface(
        elevation = 3.dp,
        modifier = modifier
            .widthIn(40.dp)
            .padding(horizontal = 3.dp),
        shape = RoundedCornerShape(25f),
        color = if (pressed.value) Color.Gray
                else if (keyboardKey == "ENTER") MaterialTheme.colors.primaryVariant
                else if (KeyboardUtil.checkIsALetter(keyboardKey)) Color.White
                else Color.LightGray,
    ) {
        when (keyboardKey) {
            "<" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .width(55.dp),
                    onClickMethod = { onClickMethodDel() },
                    imageVector = Icons.Default.Backspace,
                    desc = "DEL"
                )
            }
            "caps" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .width(55.dp),
                    onClickMethod = { onClickMethodCaps() },
                    imageVector = Icons.Default.KeyboardCapslock,
                    desc = "CAPS"
                )
            }
            " " -> {
                KeyboardFunButton(
                    modifier = modifier
                        .widthIn(200.dp, 250.dp),
                    onClickMethod = { onClickMethodChar(keyboardKey) },
                    imageVector = Icons.Default.SpaceBar,
                    desc = "SPACE"
                )
            }
            "ENTER" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .widthIn(70.dp, 80.dp),
                    onClickMethod = { onClickMethodReturn() },
                    imageVector = Icons.Default.KeyboardReturn,
                    desc = "ENTER"
                )
            }
            ":)" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .widthIn(50.dp, 60.dp),
                    onClickMethod = { onClickMethodChar(keyboardKey) },
                    imageVector = Icons.Default.SentimentSatisfied,
                    desc = "ENTER"
                )
            }
            "global" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .widthIn(50.dp, 60.dp),
                    onClickMethod = { /**TODO?*/ },
                    imageVector = Icons.Default.Public,
                    desc = "ENTER"
                )
            }

            "_" -> {
                KeyboardFunButton(
                    modifier = modifier
                        .widthIn(50.dp, 60.dp),
                    onClickMethod = {  onClickMethodChar(keyboardKey) },
                    imageVector = Icons.Default.Minimize,
                    desc = "ENTER"
                )
            }

            else -> {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Text(keyboardKey, fontWeight = FontWeight.SemiBold, fontSize = 20.sp,
                        modifier = Modifier
                            .clickable(interactionSource = interactionSource, indication = null) {
                                if (KeyboardUtil.checkIsALetter(keyboardKey)) {
                                    onClickMethodChar(
                                        keyboardKey
                                    )
                                }
                            }
                            .padding(
                                start = 4.dp,
                                end = 4.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                    )

                }
            }
        }

    }

}

@Composable
fun KeyboardFunButton(
    modifier: Modifier,
    onClickMethod: () -> Unit,
    imageVector: ImageVector? = null,
    desc: String,
    painter: Painter? = null,
) {
    IconButton(
        onClick = onClickMethod,
        modifier = modifier
    ) {
        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = desc,
                modifier = modifier.padding(3.dp)
            )
        } else if (painter != null) {
            Icon(
                painter = painter,
                contentDescription = desc,
                modifier = modifier.padding(3.dp)
            )
        }

    }
}