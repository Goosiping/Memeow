package com.example.memeow.feature_main.presentation

import androidx.compose.material.*
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.Icon

@Composable
fun navTab(
    modifier: Modifier = Modifier,
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
            selected = false,
            onClick = { /*TODO*/ }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = null
                ) },
            label = { Text("探索") },
            selected = true,
            onClick = { /*TODO*/ }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.FolderOpen,
                    contentDescription = null
                ) },
            label = { Text("你的梗圖") },
            selected = false,
            onClick = { /*TODO*/ }
        )
    }
}