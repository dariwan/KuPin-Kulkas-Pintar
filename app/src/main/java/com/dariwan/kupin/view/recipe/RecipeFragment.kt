package com.dariwan.kupin.view.recipe

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.AllRecipeAdapter
import com.dariwan.kupin.core.adapter.RecipeRecommendationAdapter
import com.dariwan.kupin.core.data.remote.response.AllRecipeItem
import com.dariwan.kupin.core.data.remote.response.RecipeItem
import com.dariwan.kupin.core.utils.Result
import com.dariwan.kupin.core.utils.ViewModelFactory
import com.dariwan.kupin.databinding.FragmentRecipeBinding
import com.dariwan.kupin.view.home.report.ReportViewModel

@RequiresApi(Build.VERSION_CODES.O)
class RecipeFragment : Fragment() {
   private lateinit var binding: FragmentRecipeBinding
   private lateinit var getAllDataViewModel: GetAllDataViewModel
   private var nameMaterial: String = ""
    private val recipeViewModel: RecipeViewModel by viewModels {
        RecipeViewModel.RecipeFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllDataViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        searchRecommendation()
        getMaterialName()
        showAllRecipe()
        setupButton()
    }

    private fun setupButton() {
        binding.ivShowAll.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentRecipe_to_allRecipeFragment)
        }
    }

    private fun showAllRecipe() {
        recipeViewModel.getAllRecipe().observe(requireActivity()){
            when(it){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val error = it.error
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    showAllRecipeRv(it.data)
                }
            }
        }
    }

    private fun searchRecommendation() {
        recipeViewModel.sendRecommendation(nameMaterial).observe(requireActivity()){
            Log.e("nama", "nama: $nameMaterial")
            when(it){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val error = it.error
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    showRecipeRecom(it.data)
                }
            }
        }
    }

    private fun showRecipeRecom(recipeRecomm: List<RecipeItem>){
        val adapter = RecipeRecommendationAdapter()
        binding.rvRecipeRecom.adapter = adapter
        binding.rvRecipeRecom.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(recipeRecomm)
    }

    private fun showAllRecipeRv(allRecipe: List<AllRecipeItem>){
        val adapter = AllRecipeAdapter()
        binding.rvAllRecipe.adapter = adapter
        binding.rvAllRecipe.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitAllList(allRecipe)
    }

    private fun getMaterialName(){
        getAllDataViewModel.getMaterialNames().observe(viewLifecycleOwner, Observer { names ->
            nameMaterial = names
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): GetAllDataViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[GetAllDataViewModel::class.java]
    }

}