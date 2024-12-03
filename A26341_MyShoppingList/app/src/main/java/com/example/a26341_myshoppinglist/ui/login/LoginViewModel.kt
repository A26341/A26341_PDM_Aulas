package com.example.a26341_myshoppinglist.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

data class LoginState(
    var email: String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: Throwable? = null 
)

class ValidationError(message: String) : Throwable(message)



class LoginViewModel : ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private var authStateListener: FirebaseAuth.AuthStateListener? = null


    private val email
        get() = state.value.email
    private val password
        get() = state.value.password

    override fun onCleared() {
        super.onCleared()
        authStateListener?.let { Firebase.auth.removeAuthStateListener(it) }
    }

    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    fun login(onLoginSuccess: () -> Unit) {

        if (email.isEmpty()) {
            state.value = state.value.copy(error = ValidationError("Email is required"))
            return
        }
        if (password.isEmpty()) {
            state.value = state.value.copy(error = ValidationError("Password is required"))
            return
        }


        state.value = state.value.copy(isLoading = true)
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    onLoginSuccess()
                } else {
                    // Armazene a exceção no estado
                    state.value = state.value.copy(error = task.exception)
                }
            }

    }

    fun loginWithGoogle(launcher: ActivityResultLauncher<Intent>, googleAuthHelper: GoogleAuthHelper, onLoginSuccess: () -> Unit) {
        val signInIntent = googleAuthHelper.getSignInIntent()
        launcher.launch(signInIntent)

        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser != null) {
                onLoginSuccess()
                auth.removeAuthStateListener(authStateListener!!)
            }
        }

        Firebase.auth.addAuthStateListener(authStateListener!!)
    }

    fun signOut(googleAuthHelper: GoogleAuthHelper) {
        FirebaseAuth.getInstance().signOut()
        googleAuthHelper.signOut()
    }
}

