package com.example.a26341_myshoppinglist.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.a26341_myshoppinglist.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class GoogleAuthHelper(private val context: Context) {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()


    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    fun signIn(activity: Activity) {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun firebaseAuthWithGoogle(data: Intent?, activity: Activity, onLoginSuccess: () -> Unit) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            // Aqui, extraímos o idToken do GoogleSignInAccount
            val idToken = account.idToken!!
            firebaseAuthWithGoogle(idToken, activity, onLoginSuccess)
        } catch (e: ApiException) {
            Log.w("GoogleAuthHelper", "Google sign in failed", e)
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String, activity: Activity ,onLoginSuccess: () -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        val userEmail = user.email

                        // Dados a serem salvos no Firestore
                        val userMap = mapOf(
                            "uid" to userId,
                            "email" to userEmail,
                        )

                        val firestore = FirebaseFirestore.getInstance()

                        // Verificar se o usuário já existe na coleção "users"
                        firestore.collection("users")
                            .document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    // Criar novo documento para o usuário
                                    firestore.collection("users")
                                        .document(userId)
                                        .set(userMap)
                                        .addOnSuccessListener {
                                            onLoginSuccess()
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.e("GoogleAuthHelper", "Erro ao salvar dados no Firestore: ${exception.localizedMessage}")
                                        }
                                } else{
                                }                                }

                            .addOnFailureListener { exception ->
                                Log.e("GoogleAuthHelper", "Erro ao verificar utilizador no Firestore: ${exception.localizedMessage}")
                            }
                    }
                } else {
                    Log.w("GoogleAuthHelper", "signInWithCredential:failure", task.exception)
                }
            }
    }


    fun signOut() {
        Firebase.auth.signOut()
        googleSignInClient.signOut()
    }

    fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }


    companion object {
        const val RC_SIGN_IN = 9001
    }
}