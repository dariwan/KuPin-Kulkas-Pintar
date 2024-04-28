package com.dariwan.kupin.view.recomendation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariwan.kupin.core.data.local.database.Material
import com.dariwan.kupin.core.data.local.repository.MaterialRepository

@RequiresApi(Build.VERSION_CODES.O)

class RecomendationViewModel(application: Application):ViewModel() {
    private val mMaterialRepository: MaterialRepository = MaterialRepository(application)

    fun getRecommendationMaterial(): LiveData<List<Material>> = mMaterialRepository.getRecommendationMaterial()
}