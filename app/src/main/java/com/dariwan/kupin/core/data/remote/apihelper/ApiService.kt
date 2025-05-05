package com.dariwan.kupin.core.data.remote.apihelper

import com.dariwan.kupin.core.data.remote.request.MaterialRequest
import com.dariwan.kupin.core.data.remote.response.AllRecipesResponse
import com.dariwan.kupin.core.data.remote.response.RecommendationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("recommend")
    fun getRecommendation(
        @Body material: MaterialRequest,
    ): Call<RecommendationResponse>

    @GET("recipes")
    fun getAllRecipe() : Call<AllRecipesResponse>

    @GET("search_recipe")
    fun searchRecipe(
        @Query("name") name: String
    ): Call<AllRecipesResponse>

}