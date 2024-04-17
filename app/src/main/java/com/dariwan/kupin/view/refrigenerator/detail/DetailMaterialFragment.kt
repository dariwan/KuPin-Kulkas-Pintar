package com.dariwan.kupin.view.refrigenerator.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dariwan.kupin.R
import com.dariwan.kupin.databinding.FragmentDetailMaterialBinding

class DetailMaterialFragment : Fragment() {
    private lateinit var binding: FragmentDetailMaterialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailMaterialBinding.inflate(layoutInflater)
        return binding.root
    }
}