package com.dariwan.kupin.view.refrigenerator.editmaterial

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.FragmentEditMaterialBinding

class EditMaterialFragment : Fragment() {
    private lateinit var binding: FragmentEditMaterialBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditMaterialBinding.inflate(layoutInflater)
        return binding.root
    }
}