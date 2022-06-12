package com.example.memeow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel


enum class MemeowScreen (val icon: ImageVector){
    Edit(
        icon = Icons.Filled.Edit
    ),
    Explore(
        icon = Icons.Filled.Explore
    ),
    Local(
        icon = Icons.Filled.FolderOpen
    );

    companion object {
        fun fromRoute(route: String?): MemeowScreen =
            when (route?.substringBefore("/")) {
                Edit.name -> Edit
                Explore.name -> Explore
                Local.name -> Local
                null -> Explore
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }

    }
}