package com.example.memeow.feature_main.presentation

import android.net.Uri
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
                LocalBody(
                    onImageClick = { image ->
                        navigateToSingleView(navController = navController, image = image)
                    }
                )
            }
            composable(
                route = "$screenName/{image}",
                arguments = listOf(
                    navArgument("image"){
                        type = NavType.StringType
                    }
                )
            ){ entry ->
                val imageId = entry.arguments?.getString("image")

                if (imageId != null) {
                    singleViewBody(imageUri =  Uri.parse(imageId.replace('\\', '/')))
                }
            }
        }
    }
}

fun navigateToSingleView(
    navController: NavHostController,
    image: Uri
){
    val modifiedUri = image.toString().replace('/', '\\')
    navController.navigate("${MemeowScreen.Explore.name}/$modifiedUri")
}