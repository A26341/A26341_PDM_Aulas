package com.example.a26341_myshoppinglist.ui.login

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

data class RegisterState(
    var email:String = "",
    var password: String = "",
    val isLoading: Boolean = false,
    val error: Throwable? = null
)



class RegisterViewModel : ViewModel(){

    var state = mutableStateOf(RegisterState())

    private val email
        get() = state.value.email
    private val password
        get() = state.value.password



    fun onEmailChange(newValue: String) {
        state.value = state.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        state.value = state.value.copy(password = newValue)
    }

    @SuppressLint("SuspiciousIndentation")
    fun registar(onRegisterSuccess: () -> Unit, onError: (String) -> Unit) {
        if (email.isEmpty()) {
            onError("Email is required") // Chamar onError diretamente
            return
        }
        if (password.isEmpty()) {
            onError("Password is required") // Chamar onError diretamente
            return
        }

        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        state.value = state.value.copy(isLoading = true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid

                    if (userId != null) {
                        val userMap = mapOf(
                            "uid" to userId,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis()
                        )

                        firestore.collection("users")
                            .document(userId) // Usar o UID como ID do documento
                            .set(userMap)
                            .addOnSuccessListener {
                                state.value = state.value.copy(isLoading = false)
                                onRegisterSuccess()
                            }
                            .addOnFailureListener { exception ->
                                state.value = state.value.copy(isLoading = false)
                                onError("Erro ao guardar dados do utilizador: ${exception.localizedMessage}")
                            }
                    } else {
                        state.value = state.value.copy(isLoading = false)
                        onError("UID do utilizador não encontrado.")
                    }
                } else {
                    // Tratamento de erros
                    state.value = state.value.copy(isLoading = false)
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthWeakPasswordException -> "A senha é muito fraca."
                        is FirebaseAuthInvalidCredentialsException -> "O email fornecido é inválido."
                        is FirebaseAuthUserCollisionException -> "O email já está em uso."
                        else -> exception?.localizedMessage ?: "Erro desconhecido."
                    }
                    onError(errorMessage)
                }
            }
    }





}