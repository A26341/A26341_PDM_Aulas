package com.example.a26341_myshoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a26341_myshoppinglist.models.ListType
import com.example.a26341_myshoppinglist.ui.home.ListItems.AddListItemsView
import com.example.a26341_myshoppinglist.ui.home.ListItems.ListItemsView
import com.example.a26341_myshoppinglist.ui.home.ListType.AddListTypeView
import com.example.a26341_myshoppinglist.ui.home.ListType.ListTypesView
import com.example.a26341_myshoppinglist.ui.login.GoogleAuthHelper
import com.example.a26341_myshoppinglist.ui.login.LoginView
import com.example.a26341_myshoppinglist.ui.login.RegisterView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme

class MainActivity : ComponentActivity() {

    private lateinit var googleAuthHelper: GoogleAuthHelper
    private lateinit var navController: NavHostController

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            googleAuthHelper.firebaseAuthWithGoogle(data, this) {
                navController.navigate(Screen.ListTypes.route) // Navegar para ListTypesView
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        googleAuthHelper = GoogleAuthHelper(this)
        setContent {
            navController = rememberNavController()

            A26341_MyShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView(launcher = launcher,
                                googleAuthHelper = googleAuthHelper,
                                onLoginSuccess = {navController.navigate(Screen.ListTypes.route)},
                                onNavigateToRegister = {navController.navigate(Screen.Register.route)},
                                navController = navController)
                        }
                        composable(Screen.Register.route) {
                            RegisterView(
                                navController = navController
                            )
                        }
                        composable(Screen.ListTypes.route) {
                            ListTypesView(
                                navController = navController,
                                onNavigateToAddList = { navController.navigate(Screen.AddListTypes.route) }
                            )
                        }
                        composable(Screen.AddListTypes.route) {
                            AddListTypeView(
                                navController = navController
                            )
                        }
                        composable(
                            route = Screen.ListItems.route + "/{listTypeName}/{listTypeDocId}",
                            arguments = listOf(
                                navArgument("listTypeName") { type = NavType.StringType },
                                navArgument("listTypeDocId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val listTypeName = backStackEntry.arguments?.getString("listTypeName")!!
                            val listTypeDocId = backStackEntry.arguments?.getString("listTypeDocId")!!
                            ListItemsView(
                                listTypeName = listTypeName,
                                listTypeDocId = listTypeDocId,
                                onNavigateToAddList = { typeName, docId ->
                                    navController.navigate(Screen.AddListItems.route + "/$listTypeName/$listTypeDocId")
                                }
                            )
                        }
                        composable(route = Screen.AddListItems.route + "/{listTypeName}/{listTypeDocId}",
                            arguments = listOf(
                                navArgument("listTypeName") { type = NavType.StringType },
                                navArgument("listTypeDocId") { type = NavType.StringType }

                            ) )
                        {backStackEntry ->
                                val listTypeName = backStackEntry.arguments?.getString("listTypeName")!!
                                val listTypeDocId = backStackEntry.arguments?.getString("listTypeDocId")!!
                                AddListItemsView(
                                    listTypeName = listTypeName,
                                    listTypeDocId = listTypeDocId,
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
            object ListItems : Screen("listItems/{listTypeName}/{listTypeDocId}")
            object AddListItems : Screen("addListItems/{listTypeName}/{listTypeDocId}")
            object Register : Screen("register")
        }
