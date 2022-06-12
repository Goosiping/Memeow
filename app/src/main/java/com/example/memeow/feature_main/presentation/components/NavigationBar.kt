package com.example.memeow.feature_main.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memeow.feature_main.presentation.explore.ExploreScreen
import androidx.navigation.navArgument
import com.example.memeow.MemeowScreen
import com.example.memeow.feature_edit_image.presentation.edit_image.EditScreen
import com.example.memeow.feature_edit_image.presentation.view_tempate.EditViewTemplateScreen
import com.example.memeow.feature_main.presentation.local.LocalBody
import com.example.memeow.feature_main.presentation.sigle_view.singleViewBody


@Composable
fun navigationBar(
    navController: NavHostController,
    currentScreen: MemeowScreen,
    currentRoute: String? =""
){

    val screenName = MemeowScreen.Explore.name



    Scaffold(

        bottomBar = {
            if(!checkHideNavBar(currentRoute)){
                navTab(
                    modifier = Modifier,
                    currentScreen = currentScreen,
                    onClick = { screen ->
                        navController.navigate(screen.name)
                    }
                )
            }

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
                    singleViewBody(
                        imageUri =  Uri.parse(imageId.replace('\\', '/')),
                        onClickBack = { navController.popBackStack() }
                    )
                }
            }
            composable(MemeowScreen.Edit.name){
                EditViewTemplateScreen(
                    onImageClick = { image ->
                        navigateToSingleView(navController = navController, image = image)
                    },
                    onEditButtonClick =  { image ->
                        navigateToEditView(navController = navController, image = image)
                    }
                )
            }
            composable(
                route = "${MemeowScreen.Edit.name}/{image}",
                arguments = listOf(
                    navArgument("image"){
                        type = NavType.StringType
                    }
                )
            ){ entry ->
                val imageId = entry.arguments?.getString("image")

                if (imageId != null) {
                    EditScreen(imageUri =  Uri.parse(imageId.replace('\\', '/')),
                        backMethod = {navController.popBackStack()}
                    )
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

fun navigateToEditView(
    navController: NavHostController,
    image: Uri
){
    /**For some unknown reason, image editor cannot normally use in jetpack compose, it may related jackpack compose rendering lifecycle*/

    val modifiedUri = image.toString().replace('/', '\\')
    navController.navigate("${MemeowScreen.Edit.name}/$modifiedUri")

}

fun checkHideNavBar(screenName: String?): Boolean {
    if (screenName == null)
        return false
    /*Check current route(screenName) to decide hide nav bar or not*/
    if (screenName.startsWith(MemeowScreen.Edit.name))
        if (screenName.length > MemeowScreen.Edit.name.length)
            return true
    if (screenName.startsWith(MemeowScreen.Explore.name))
        if (screenName.length > MemeowScreen.Explore.name.length)
            return true
    return false
}
