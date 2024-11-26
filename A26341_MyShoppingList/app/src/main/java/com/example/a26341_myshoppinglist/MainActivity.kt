package com.example.a26341_myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a26341_myshoppinglist.ui.home.ListType.AddListTypeView
import com.example.a26341_myshoppinglist.ui.home.ListType.ListTypesView
import com.example.a26341_myshoppinglist.ui.login.LoginView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var navController = rememberNavController()

            A26341_MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView {
                                navController.navigate(Screen.ListTypes.route)
                            }
                        }
                       composable(Screen.ListTypes.route) {
                            ListTypesView(){
                               navController.navigate(Screen.AddListTypes.route)
                            }
                        }
                        composable(Screen.AddListTypes.route) {
                            AddListTypeView(
                                navController = navController
                            )
                        }
                        }
                    }
                }

                LaunchedEffect(Unit) {

                    val auth = Firebase.auth

                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        navController.navigate(Screen.ListTypes.route)
                    }
                }
            }
        }
    }

    sealed class Screen(val route: String) {
        object Login : Screen("login")
        object ListTypes : Screen("listTypes")
        object AddListTypes : Screen("addListTypes")
        object ListItems : Screen("listItems")
        object AddListItems : Screen("addListItems")
    }