package com.dariwan.kupin.view.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.R
import com.dariwan.kupin.core.utils.Constant.KEY_IS_LOGIN
import com.dariwan.kupin.core.utils.SessionManager
import com.dariwan.kupin.databinding.ActivityLoginBinding
import com.dariwan.kupin.view.main.MainActivity
import com.dariwan.kupin.view.register.RegisterActivity

@RequiresApi(Build.VERSION_CODES.O)

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPref: SessionManager

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        sharedPref = SessionManager(this)

        setupButton()
        checkPermissions()
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 33){
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
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
        sharedPref.apply {
            sharedPref.setBooleanPref(KEY_IS_LOGIN, true)
        }
    }


    override fun onStart() {
        super.onStart()
        val isLogin = sharedPref.isLogin
        if (isLogin){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}