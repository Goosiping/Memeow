package com.example.memeow.feature_main.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memeow.feature_main.presentation.explore.ExploreScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.example.memeow.MemeowScreen
import com.example.memeow.feature_main.presentation.local.LocalBody
import com.example.memeow.feature_main.presentation.sigle_view.singleViewBody


@Composable
fun navigationBar(
    navController: NavHostController,
    currentScreen: MemeowScreen
){

    val screenName = MemeowScreen.Explore.name

    Scaffold(
        bottomBar = {
            navTab(
                modifier = Modifier,
                currentScreen = currentScreen,
                onClick = { screen ->
                    navController.navigate(screen.name)
                }
            )
        }
    ){innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MemeowScreen.Explore.name,
            modifier = Modifier
        ){
            composable(MemeowScreen.Explore.name){
                ExploreScreen(
                    onImageClick = { image ->
                        navigateToSingleView(navController = navController, image = image)
                    }
                )
            }
            composable(MemeowScreen.Local.name){
                LocalBody()
            }
            composable(
                route = "$screenName/{image}",
                arguments = listOf(
                    navArgument("image"){
                        type = NavType.IntType
                    }
                )
            ){ entry ->
                val imageId = entry.arguments?.getInt("image")

                if (imageId != null) {
                    singleViewBody(drawable = imageId)
                }
            }
        }
    }
}

fun navigateToSingleView(
    navController: NavHostController,
    image: Int
){
    navController.navigate("${MemeowScreen.Explore.name}/$image")
}