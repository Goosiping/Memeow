package com.example.memeow

import android.Manifest
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.memeow.feature_main.presentation.navTab
import com.example.memeow.feature_main.presentation.navigateToSingleView
import com.example.memeow.feature_main.presentation.navigationBar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setContent {
            MemeowTheme {
                val navController = rememberNavController()
                val backStackEntry = navController.currentBackStackEntryAsState()
                val currentScreen = MemeowScreen.fromRoute(backStackEntry.value?.destination?.route)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExploreScreen(
                        viewModel = hiltViewModel(),
                        onImageClick = { image ->
                            navigateToSingleView(navController = navController, image = image)
                        }
                    )
                }
                navigationBar(
                    navController = navController,
                    currentScreen
                )
            }

        }
    }

    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100
            )
        }
        if (!haveInternetPermission()) {
            val permissions = arrayOf(Manifest.permission.INTERNET)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET), 100
            )
        }
        if (!haveNetStatePermission()) {
            val permissions = arrayOf(Manifest.permission.ACCESS_NETWORK_STATE)
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_NETWORK_STATE), 100
            )
        }
    }
    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun haveInternetPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED

    private fun haveNetStatePermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_NETWORK_STATE
        ) == PackageManager.PERMISSION_GRANTED

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