package com.dariwan.kupin.view.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.data.local.database.MaterialRoomDatabase
import com.dariwan.kupin.core.utils.SessionManager
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.FragmentRefrigeneratorBinding
import com.dariwan.kupin.view.home.material.MaterialActivity
import com.dariwan.kupin.view.home.report.ReportActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class RefrigeneratorFragment : Fragment() {
    private lateinit var binding: FragmentRefrigeneratorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRefrigeneratorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayGreetings()
        setupButton()
    }

    private fun setupButton() {
        binding.cardRefrigenerator.setOnClickListener {
            val intent = Intent(requireContext(), MaterialActivity::class.java)
            startActivity(intent)
        }

        binding.cardReport.setOnClickListener {
            val intent = Intent(requireContext(), ReportActivity::class.java)
            startActivity(intent)
        }

        binding.cardRecipe.setOnClickListener {
            findNavController().navigate(R.id.fragmentRecipe, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.fragmentRefrigerator, true)
                    .build())
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun displayGreetings() {
        val time = SimpleDateFormat("HH.mm").format(Date()).toDouble()

        val greeting = when (time) {
            in 1.0..10.0 -> "Selamat pagi"
            in 10.0..14.0 -> "Selamat siang"
            in 14.0..18.5 -> "Selamat sore"
            else -> "Selamat malam"
        }

        binding.tvGreetings.text = "Hai, $greeting"
    }

}