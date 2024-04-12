package com.dariwan.kupin.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.ActivityRegisterBinding
import com.dariwan.kupin.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        setupButton()
    }

    private fun setupButton() {
        binding.btnRegister.setOnClickListener {
            register()
        }
        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun register() {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        binding.progressBar.visibility = View.VISIBLE

        registerViewModel.registerUser(username, email, password, {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, "Akun Berhasil Dibuat", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, {errorMessage ->
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }
}