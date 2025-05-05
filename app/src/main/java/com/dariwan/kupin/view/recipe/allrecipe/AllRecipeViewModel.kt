package com.dariwan.kupin.view.recipe.allrecipe

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.core.data.remote.di.Injection
import com.dariwan.kupin.core.data.remote.repository.RecommendationRepository
import com.dariwan.kupin.view.recipe.RecipeViewModel
import java.lang.IllegalArgumentException

@RequiresApi(Build.VERSION_CODES.O)
class AllRecipeViewModel(private val recommendationRepository: RecommendationRepository): ViewModel() {
    fun searchRecipe(name: String) = recommendationRepository.searchRecipe(name)

    @Suppress("UNCHECKED_CAST")
    class SearchRecipeFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AllRecipeViewModel::class.java)){
                return AllRecipeViewModel(Injection.provideRecommendationRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}