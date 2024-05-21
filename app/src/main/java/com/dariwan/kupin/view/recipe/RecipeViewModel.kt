package com.dariwan.kupin.view.recipe

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dariwan.kupin.core.data.remote.di.Injection
import com.dariwan.kupin.core.data.remote.repository.RecommendationRepository
import java.lang.IllegalArgumentException

class RecipeViewModel(private val recommendationRepository: RecommendationRepository): ViewModel() {
    fun sendRecommendation(user_input: String) = recommendationRepository.getRecommendation(user_input)

    class RecipeFactory(private val context: Context): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)){
                return RecipeViewModel(Injection.provideRecommendationRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}