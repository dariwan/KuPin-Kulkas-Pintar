package com.dariwan.kupin.core.data.remote.di

import android.content.Context
import com.dariwan.kupin.core.data.remote.apihelper.ApiConfig
import com.dariwan.kupin.core.data.remote.repository.RecommendationRepository

object Injection {
    fun provideRecommendationRepository(context: Context): RecommendationRepository{
        val apiService = ApiConfig.getApiService()
        return RecommendationRepository.getInstance(apiService, context)
    }
}