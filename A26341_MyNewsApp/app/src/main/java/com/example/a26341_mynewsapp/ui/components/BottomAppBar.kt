package com.example.a26341_mynewsapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.a26341_mynewsapp.Screen

@Composable
fun MyBottomBar(navController: NavHostController) {

    BottomAppBar{
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
        }

        }
    }
}