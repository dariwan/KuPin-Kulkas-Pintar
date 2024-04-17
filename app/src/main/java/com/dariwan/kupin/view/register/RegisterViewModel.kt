package com.dariwan.kupin.view.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun registerUser(username: String, email: String, password: String,
                     onSuccess: () -> Unit, onError: (String) -> Unit){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userMap = hashMapOf(
                        "username" to username,
                        "email" to email,
                        "password" to password,
                    )

                    val userId = auth.currentUser!!.uid

                    db.collection("user").document(userId).set(userMap)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onError("Gagal membuat akun: ${e.message}")
                        }
                } else{
                    onError("Gagal membuat akun: ${task.exception?.message ?: "Unknown error occurred"}")
                }
            }
    }
}