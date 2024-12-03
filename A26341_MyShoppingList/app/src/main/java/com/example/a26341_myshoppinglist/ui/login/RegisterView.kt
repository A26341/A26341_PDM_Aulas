package com.example.a26341_myshoppinglist.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


@Composable

fun RegisterView(
    modifier: Modifier = Modifier,
    onRegisterSuccess: () -> Unit = {},
    navController : NavController = rememberNavController()
){
    val viewModel: RegisterViewModel = viewModel()
    val state = viewModel.state
    var passwordVisible by remember { mutableStateOf(false)}
    var errorMessage by remember { mutableStateOf<String?>(null) }


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
            Button(onClick = { viewModel.registar(
                onRegisterSuccess =
                {
                onRegisterSuccess()
                navController.popBackStack()
            }, onError = {message ->
                    errorMessage = message
                }) }) {
                Text(text = "Registar")
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red
                )
            }

        }
    }
}

