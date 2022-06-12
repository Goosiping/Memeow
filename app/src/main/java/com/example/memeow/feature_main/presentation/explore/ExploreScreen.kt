package com.example.memeow.feature_main.presentation.explore

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.memeow.MemeowScreen
import com.example.memeow.feature_main.presentation.explore.components.MainBar
import com.example.memeow.feature_main.presentation.explore.components.MemeItem
import com.example.memeow.feature_main.presentation.explore.components.SearchBar
import com.example.memeow.feature_main.presentation.navigationBar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ExploreScreen"

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onImageClick: (Uri) ->Unit
) {
    val state = viewModel.state.value
    Log.i(TAG, "ExploreScreen")
    Scaffold(
        topBar = {
            MainBar(
                displaysearchbar = state.searchbaractivate,
                keyword = state.keyword,
                onTextChange = { viewModel.updatetext(newkeyword = it)}, // it from TextField onValueChange()
                onCloseClicked = {
                    viewModel.updatetext("")
                    viewModel.updatebar(false) },
                onSearchClicked = { viewModel.onEvent(ExploreEvents.Search(keyword = state.keyword))},
                onSearchTrigger = { viewModel.updatebar(true) }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            /*
            LazyVerticalGrid see:
            https://developer.android.com/codelabs/jetpack-compose-layouts?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fcompose%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fjetpack-compose-layouts#7
            */
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ){
                Log.i(TAG, "LazyVerticalGrid")
                items(state.memes) { meme ->
                    //Log.d("","imageUri = ${meme.image}")
                    MemeItem(
                        imageUri = meme.image,
                        onImageClick = { onImageClick(meme.image) },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun ExploreScreenPreview() {
    ExploreScreen(
        viewModel = hiltViewModel(),
        onImageClick = {}
    )
}