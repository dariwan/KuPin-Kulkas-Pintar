package com.dariwan.kupin.core.data.remote.apihelper

import com.dariwan.kupin.core.data.remote.request.MaterialRequest
import com.dariwan.kupin.core.data.remote.response.RecommendationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("recommend")
    fun getRecommendation(
        @Body material: MaterialRequest,
    ): Call<RecommendationResponse>

}