package com.example.a26341_mynewsapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.a26341_mynewsapp.ui.components.MyBottomBar
import com.example.a26341_mynewsapp.ui.components.MyTopAppBar
import com.example.a26341_mynewsapp.ui.theme.A26341_MyNewsAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeView(navController: NavHostController) {
    Scaffold(
        topBar = { MyTopAppBar(title = "Menu principal") },
        bottomBar = { MyBottomBar(navController) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { navController.navigate("cats") }) {
                    Text(text = "Let's go!")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    A26341_MyNewsAppTheme {

    }

}