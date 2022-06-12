package com.example.memeow.feature_main.presentation.sigle_view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.memeow.MemeowScreen
import com.example.memeow.feature_main.presentation.components.topBar
import com.google.accompanist.flowlayout.FlowRow
import com.example.memeow.feature_main.presentation.components.tagChip


@Composable
fun singleViewBody(
    imageUri: Uri,
    onClickBack: () -> Unit
){
    Log.d("","imageUri = $imageUri")

    val tags: List<String>
    tags = listOf("柴犬", "哭哭貓", "海綿寶寶")

    Scaffold(
        topBar = {
            topBar(
                title = "你的梗圖",
                onClick = { onClickBack() }
            )
        },
        /*
        bottomBar = {
            BottomNavigation(modifier = Modifier){
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = null
                        ) },
                    label = { Text("你的梗圖") },
                    onClick = {},
                    selected = false
                )
            }
        }*/
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
                            FloatingActionButton(
                                modifier = Modifier.size(50.dp),
                                backgroundColor = MaterialTheme.colors.onPrimary,
                                onClick = { /**ADD TAG**/ }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }

                        FlowRow(
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                        ){
                            tags.forEach{
                                tagChip(
                                    text = it,
                                    onClick = { /** EDIT TAG **/ },
                                    onClose = { /** DELETE TAG**/ }
                                )
                            }
                        }
                    }


                }
            }
        }

    }



}