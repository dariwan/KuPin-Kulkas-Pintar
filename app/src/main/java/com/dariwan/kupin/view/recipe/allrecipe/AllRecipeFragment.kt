package com.dariwan.kupin.view.recipe.allrecipe

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariwan.kupin.R
import com.dariwan.kupin.core.adapter.AllRecipeAdapter
import com.dariwan.kupin.core.data.remote.response.AllRecipeItem
import com.dariwan.kupin.core.utils.Result
import com.dariwan.kupin.databinding.FragmentAllRecipeBinding
import com.dariwan.kupin.view.recipe.RecipeViewModel

@RequiresApi(Build.VERSION_CODES.O)
class AllRecipeFragment : Fragment() {
    private lateinit var binding: FragmentAllRecipeBinding
    private val recipeViewModel: RecipeViewModel by viewModels {
        RecipeViewModel.RecipeFactory(requireContext())
    }
    private val allRecipeViewModel: AllRecipeViewModel by viewModels {
        AllRecipeViewModel.SearchRecipeFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAllRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAllRecipe()
        setupSearchListener()
    }

    private fun setupSearchListener() {
        binding.etName.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    event?.action == KeyEvent.ACTION_DOWN &&
                    event.keyCode == KeyEvent.KEYCODE_ENTER){
                    searchRecipe()
                    return true
                }
                return false
            }

        })
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

    private fun searchRecipe(){
        val name = binding.etName.text.toString()
        allRecipeViewModel.searchRecipe(name).observe(requireActivity()){
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

    private fun showAllRecipeRv(allRecipe: List<AllRecipeItem>){
        val adapter = AllRecipeAdapter()
        binding.rvSearchRecipe.adapter = adapter
        binding.rvSearchRecipe.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitAllList(allRecipe)
    }
}