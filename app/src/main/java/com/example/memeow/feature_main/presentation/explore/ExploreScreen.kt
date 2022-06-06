package com.example.memeow.feature_main.presentation.explore

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.memeow.feature_main.presentation.explore.components.MemeItem
import com.example.memeow.feature_main.presentation.explore.components.SearchBar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ExploreScreen"

@Composable
fun ExploreScreen(
    //navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Log.i(TAG, "ExploreScreen")
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                text = "Search for tag...",
                onTextChange = { },
                onCloseClicked = { },
                onSearchClicked = { }
            )
            /*
            LazyVerticalGrid see:
            https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#7
            */
            LazyVerticalGrid(columns = GridCells.Fixed(2)){
                Log.i(TAG, "LazyVerticalGrid")
                items(state.memes) { meme ->
                    MemeItem(drawable = meme.image)
                }
            }
        }
    }
}


@Composable
@Preview
fun ExploreScreenPreview() {
    ExploreScreen( viewModel = hiltViewModel())
}