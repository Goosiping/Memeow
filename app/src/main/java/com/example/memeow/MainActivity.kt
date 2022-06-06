package com.example.memeow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.memeow.feature_main.presentation.explore.ExploreScreen
import com.example.memeow.ui.theme.MemeowTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memeow.feature_main.presentation.navTab

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemeowTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExploreScreen( viewModel = hiltViewModel())
                }
                Scaffold(
                    bottomBar = { navTab() }
                ){innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "ExploreScreen",
                        modifier = Modifier
                    ){
                        composable("ExploreScreen"){
                            ExploreScreen()
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemeowTheme {
        Greeting("Android")
    }
}