package com.dariwan.kupin.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.ActivityLoginBinding
import com.dariwan.kupin.view.main.MainActivity
import com.dariwan.kupin.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}