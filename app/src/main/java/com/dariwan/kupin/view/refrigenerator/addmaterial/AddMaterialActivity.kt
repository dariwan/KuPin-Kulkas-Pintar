package com.dariwan.kupin.view.refrigenerator.addmaterial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.ActivityAddMaterialBinding
import com.dariwan.kupin.view.main.MainActivity

class AddMaterialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMaterialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMaterialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
    }

    private fun setupButton() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}