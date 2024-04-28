package com.dariwan.kupin.view.recomendation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.MaterialAdapter
import com.dariwan.kupin.core.adapter.MaterialRecommendationAdapter
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.FragmentRecomendationBinding
import com.dariwan.kupin.view.refrigenerator.RefrigeneratorViewModel

@RequiresApi(Build.VERSION_CODES.O)
class RecomendationFragment : Fragment() {
   private lateinit var binding: FragmentRecomendationBinding
   private lateinit var recomendationViewModel: RecomendationViewModel
   private lateinit var adapter: MaterialRecommendationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecomendationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recomendationViewModel = obtainViewModel(requireActivity()  as AppCompatActivity)

        setupRv()
    }

    private fun setupRv() {
        recomendationViewModel.getRecommendationMaterial().observe(requireActivity()){materialList ->
            if (materialList != null){
                adapter.setListMaterial(materialList)
            }
        }

        adapter = MaterialRecommendationAdapter()
        binding.rvRecom.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecom.setHasFixedSize(true)
        binding.rvRecom.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): RecomendationViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(RecomendationViewModel::class.java)
    }

}