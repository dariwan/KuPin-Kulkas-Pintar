package com.dariwan.kupin.view.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.ActivityLoginBinding
import com.dariwan.kupin.view.main.MainActivity
import com.dariwan.kupin.view.register.RegisterActivity

@RequiresApi(Build.VERSION_CODES.O)

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setupButton()
    }

    private fun setupButton() {
        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()


        if (email.isEmpty()){
            binding.etEmailLayout.error = "Email harus diisi"
        } else if (password.isEmpty()){
            binding.etPasswordLayout.error = "Password harus diisi"
        } else {
            binding.progressBar.visibility = View.VISIBLE
            loginViewModel.loginUser(email, password, { user ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                saveLoginInfo()
            }, {errorMessage ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            })
        }

    }

    private fun saveLoginInfo() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean(IS_LOGIN, true)
        }.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean(IS_LOGIN, false)
        if (isLogin){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val PREFS_NAME = "kupin_app"
        const val IS_LOGIN = "login"
    }
}