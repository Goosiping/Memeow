package com.example.memeow.feature_main.presentation

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.memeow.MemeowScreen

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
){

}

@Composable
fun navTab(
    modifier: Modifier = Modifier,
    currentScreen: MemeowScreen,
    onClick:(MemeowScreen) -> Unit
){
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {



        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                ) },
            label = { Text("編輯") },
            selected = (currentScreen == MemeowScreen.Edit),
            onClick = { onClick(MemeowScreen.Edit) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = null
                ) },
            label = { Text("探索") },
            selected = (currentScreen == MemeowScreen.Explore),
            onClick = { onClick(MemeowScreen.Explore) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = null
                ) },
            label = { Text("你的梗圖") },
            selected = (currentScreen == MemeowScreen.Local),
            onClick = { onClick(MemeowScreen.Local) }
        )
    }
}

