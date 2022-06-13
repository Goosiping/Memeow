package com.example.memeow.feature_main.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memeow.MemeowScreen
import com.example.memeow.ui.theme.MemeowThemeMD3

@Composable
fun MemeowTabRow(
    allScreens: List<MemeowScreen>,
    onTabSelected: (MemeowScreen) -> Unit,
    currentScreen: MemeowScreen
) {
    Surface(
        Modifier
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                MemeowTab(
                    text = screen.name,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun MemeowTab(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean
) {

}


@Composable
fun navTab(
    modifier: Modifier = Modifier,
    currentScreen: MemeowScreen,
    onClick: (MemeowScreen) -> Unit
) {
    MemeowThemeMD3 {

        NavigationBar(
            containerColor = MaterialTheme.colors.surface,
            modifier = modifier.height(70.dp),
            tonalElevation = 0.dp,

            ) {

            LaunchedEffect(Unit) {

            }
            NavigationBarItem(
                selected = (currentScreen == MemeowScreen.Edit),
                onClick = { onClick(MemeowScreen.Edit) },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = null
                    )
                },
                label = {
                    Column {
                        Spacer(modifier = Modifier.height(20.dp).weight(1f))
                        Text(text = "編輯", fontSize = 12.sp)
                    }
                }
            )



            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Explore,
                        contentDescription = null
                    )
                },
                label = { Text(text = "探索", fontSize = 12.sp) },
                selected = (currentScreen == MemeowScreen.Explore),
                onClick = { onClick(MemeowScreen.Explore) }
            )

            NavigationBarItem(

                icon = {
                    Icon(
                        imageVector = Icons.Outlined.FolderOpen,
                        contentDescription = null
                    )
                },
                label = { Text(text = "你的梗圖", fontSize = 12.sp) },
                selected = (currentScreen == MemeowScreen.Local),
                onClick = { onClick(MemeowScreen.Local) }
            )

        }

    }

}

@Composable
fun navTabOld(
    modifier: Modifier = Modifier,
    currentScreen: MemeowScreen,
    onClick: (MemeowScreen) -> Unit
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {


        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            },
            label = { Text("編輯") },
            selected = (currentScreen == MemeowScreen.Edit),
            onClick = { onClick(MemeowScreen.Edit) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = null
                )
            },
            label = { Text("探索") },
            selected = (currentScreen == MemeowScreen.Explore),
            onClick = { onClick(MemeowScreen.Explore) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = null
                )
            },
            label = { Text("你的梗圖") },
            selected = (currentScreen == MemeowScreen.Local),
            onClick = { onClick(MemeowScreen.Local) }
        )
    }
}

