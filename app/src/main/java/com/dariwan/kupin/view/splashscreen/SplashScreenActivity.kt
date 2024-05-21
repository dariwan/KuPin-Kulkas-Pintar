package com.dariwan.kupin.view.splashscreen

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.dariwan.kupin.databinding.ActivitySplashScreenBinding
import com.dariwan.kupin.view.login.LoginActivity
import com.dariwan.kupin.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val intent =  Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        progressBarSetup()
    }

    private fun progressBarSetup() {
        binding.progressBar.max = 1000
        ObjectAnimator.ofInt(binding.progressBar, "progress", 1000)
            .setDuration(3000)
            .start()
    }
}