package com.example.a26341_mynewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a26341_mynewsapp.ui.cats.CatsView
import com.example.a26341_mynewsapp.ui.home.HomeView
import com.example.a26341_mynewsapp.models.Cat
import com.example.a26341_mynewsapp.ui.theme.A26341_MyNewsAppTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A26341_MyNewsAppTheme {
                    MyCatApp()
                }
        }
    }
}

@Composable
fun MyCatApp(){
    val navController =  rememberNavController()
    NavHost(navController = navController,
        startDestination = Screen.Home.route){
        composable(route = Screen.Home.route){ HomeView(navController) }
        composable(route = Screen.Cats.route){ CatsView (navController) }
    }
}



sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cats : Screen("cats")
    object BookMarks : Screen("bookMarks")
}

