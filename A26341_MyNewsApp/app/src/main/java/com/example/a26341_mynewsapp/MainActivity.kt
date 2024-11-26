package com.example.a26341_mynewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){HomeView(navController)}
        composable("cats"){ CatsView (navController)}
    }
}

