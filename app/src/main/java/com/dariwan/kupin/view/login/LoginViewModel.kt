package com.dariwan.kupin.view.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun loginUser(email: String, password: String,
                  onComplete: (FirebaseUser?) -> Unit,
                  onError: (String) -> Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    onComplete(auth.currentUser)
                } else{
                    onError(task.exception?.message ?: "User tidak ditemukan")
                }
            }
            .addOnFailureListener {
                onError(it.message ?: "User tidak ditemukan")
            }
    }
}