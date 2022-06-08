package com.example.memeow.feature_main.presentation.local

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocalBody(){
    Text(
        text = "***TEST THIS IS SUPPOSE TO SHOW LOCAL MEMES***",
        modifier = Modifier.padding(vertical = 8.dp)
    )
}