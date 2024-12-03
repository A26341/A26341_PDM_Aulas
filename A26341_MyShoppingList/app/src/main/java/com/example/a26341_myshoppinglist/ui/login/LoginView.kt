package com.example.a26341_myshoppinglist.ui.login

import android.app.Activity
import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a26341_myshoppinglist.ui.theme.A26341_MyShoppingListTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.LaunchedEffect
import com.example.a26341_myshoppinglist.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.a26341_myshoppinglist.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.delay
import androidx.activity.result.ActivityResultLauncher as ActivityResultLauncher1

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    launcher: ActivityResultLauncher1<Intent>,
    googleAuthHelper: GoogleAuthHelper,
    onNavigateToRegister: () -> Unit = {},
    navController: NavHostController
) {
    val viewModel: LoginViewModel = viewModel()
    val state = viewModel.state
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        // Check if the user is already signed in
        if (FirebaseAuth.getInstance().currentUser != null) {
            viewModel.signOut(googleAuthHelper)

            // Delay navigation to LoginView
            delay(100) // Adjust delay as needed
            navController.navigate(Screen.Login.route)
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = state.value.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = { Text(text = "Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.value.password,
                onValueChange = viewModel::onPasswordChange,
                placeholder = { Text(text = "Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Esconder senha" else "Mostrar senha"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.login(onLoginSuccess = onLoginSuccess) }) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botão para Login com o Google
            Button(
                onClick = {
                    viewModel.loginWithGoogle(launcher, googleAuthHelper, onLoginSuccess)
                },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id= R.drawable.icons8_google_logo),
                    contentDescription = "Login com Google",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Entrar com Google")
        }
            // Exibição da mensagem de erro
            Text(
                text = when (val error = state.value.error) {
                    is ValidationError -> error.message ?: "Erro de validação desconhecido"
                    is FirebaseAuthInvalidCredentialsException -> "Credenciais inválidas."
                    is FirebaseAuthInvalidUserException -> "Utilizador não encontrado."
                    else -> ""
                }
            )
            if (state.value.isLoading) {
                CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.BottomCenter){
                Button(onClick = {onNavigateToRegister()}){
                    Text(text = "Registar")
                }

            }

    }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {} // Callback vazio, pois não precisamos lidar com o resultado na Preview
    )
    A26341_MyShoppingListTheme {
        LoginView(launcher = launcher, googleAuthHelper = GoogleAuthHelper(LocalContext.current),
            navController = NavHostController(LocalContext.current)  )
    }
}

