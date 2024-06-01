package com.dariwan.kupin.core.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dariwan.kupin.core.data.remote.apihelper.ApiService
import com.dariwan.kupin.core.data.remote.request.MaterialRequest
import com.dariwan.kupin.core.data.remote.response.AllRecipeItem
import com.dariwan.kupin.core.data.remote.response.AllRecipesResponse
import com.dariwan.kupin.core.data.remote.response.RecipeItem
import com.dariwan.kupin.core.data.remote.response.RecommendationResponse
import com.dariwan.kupin.core.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationRepository private constructor(
    private val apiService: ApiService,
){
    fun getRecommendation(user_input: String): LiveData<Result<List<RecipeItem>>>{
        val recipeLieData = MutableLiveData<Result<List<RecipeItem>>>()
        recipeLieData.value = Result.Loading

        val request = MaterialRequest(user_input)
        apiService.getRecommendation(request).enqueue(object : Callback<RecommendationResponse>{
            override fun onResponse(
                call: Call<RecommendationResponse>,
                response: Response<RecommendationResponse>,
            ) {
                if (response.isSuccessful){
                    val recipe = response.body()?.recommendations ?: emptyList()
                    recipeLieData.value = Result.Success(recipe)
                } else{
                    recipeLieData.value = Result.Error("Gagal mendapatkan rekomendasi")
                    Log.e("recom_repo", "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RecommendationResponse>, t: Throwable) {
                Log.e("recom_repo", "Failed: Response Unsuccessful - ${t.message}")
                recipeLieData.value = Result.Error("Terjadi kesalahan")
            }

        })
        return recipeLieData
    }

    fun getAllRecipe(): LiveData<Result<List<AllRecipeItem>>>{
        val allRecipeData = MutableLiveData<Result<List<AllRecipeItem>>>()
        allRecipeData.value = Result.Loading

        apiService.getAllRecipe().enqueue(object : Callback<AllRecipesResponse> {
            override fun onResponse(
                call: Call<AllRecipesResponse>,
                response: Response<AllRecipesResponse>,
            ) {
                if (response.isSuccessful){
                    val allRecipe = response.body()?.recipes ?: emptyList()
                    allRecipeData.value = Result.Success(allRecipe)
                } else{
                    allRecipeData.value = Result.Error("Gagal mendapatkan seluruh resep")
                    Log.e("all_recipe", "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllRecipesResponse>, t: Throwable) {
                Log.e("all_recipe", "Failed: Response Unsuccessful - ${t.message}")
                allRecipeData.value = Result.Error("Terjadi kesalahan")
            }

        })
        return allRecipeData
    }

    fun searchRecipe(name: String): LiveData<Result<List<AllRecipeItem>>>{
        val searchRecipeData = MutableLiveData<Result<List<AllRecipeItem>>>()
        searchRecipeData.value = Result.Loading

        apiService.searchRecipe(name).enqueue(object : Callback<AllRecipesResponse>{
            override fun onResponse(
                call: Call<AllRecipesResponse>,
                response: Response<AllRecipesResponse>,
            ) {
                if (response.isSuccessful){
                    val allRecipe = response.body()?.recipes ?: emptyList()
                    searchRecipeData.value = Result.Success(allRecipe)
                } else{
                    searchRecipeData.value = Result.Error("Gagal mendapatkan resep")
                    Log.e("all_recipe", "Failed: Response Unsuccessful - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllRecipesResponse>, t: Throwable) {
                Log.e("all_recipe", "Failed: Response Unsuccessful - ${t.message}")
                searchRecipeData.value = Result.Error("Terjadi kesalahan")
            }

        })
        return searchRecipeData
    }



    companion object{
        @Volatile
        private var INSTANCE: RecommendationRepository? = null

        fun getInstance(apiService: ApiService, context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: RecommendationRepository(apiService)
            }.also { INSTANCE = it }
    }
}