package com.dariwan.kupin.core.data.remote.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dariwan.kupin.core.data.remote.apihelper.ApiService
import com.dariwan.kupin.core.data.remote.request.MaterialRequest
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

    companion object{
        @Volatile
        private var INSTANCE: RecommendationRepository? = null

        fun getInstance(apiService: ApiService, context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: RecommendationRepository(apiService)
            }.also { INSTANCE = it }
    }
}